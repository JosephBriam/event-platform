package com.manosunidas.event_manager.service;

import com.manosunidas.event_manager.model.Event;
import com.manosunidas.event_manager.model.Registration;
import com.manosunidas.event_manager.model.User;
import com.manosunidas.event_manager.repository.RegistrationRepository;
import com.manosunidas.event_manager.repository.EventRepository;
import com.manosunidas.event_manager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    // Registrar un participante en un evento
    public Registration registerUserToEvent(Long eventId, String username) {
        // Obtener el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener el evento por ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Verificar si el usuario ya está registrado en este evento
        List<Registration> existingRegistrations = registrationRepository.findByUserAndEvent(user, event);
        if (!existingRegistrations.isEmpty()) {
            throw new RuntimeException("Ya estás registrado en este evento");
        }

        // Verificar si hay cupos disponibles
        if (event.getAvailableSeats() <= 0) {
            throw new RuntimeException("No hay cupos disponibles para este evento");
        }

        // Crear un nuevo registro para el evento
        Registration registration = new Registration();
        registration.setUser(user); // Asignar el usuario
        registration.setEvent(event); // Asignar el evento

        // Asignar los detalles del usuario al registro
        registration.setName(user.getName()); // Asignar el nombre del usuario
        registration.setEmail(user.getEmail()); // Asignar el email
        registration.setPhone(user.getPhone()); // Asignar el teléfono
        registration.setAge(user.getAge()); // Asignar la edad

        // Descontar un cupo del evento
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);  // Guardamos el evento con los cupos actualizados

        return registrationRepository.save(registration); // Guardar el registro de inscripción
    }

    // Obtener todos los registros de un evento
    public List<Registration> getRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    // Obtener todos los registros (solo accesible para ADMIN)
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    // Actualizar un registro
    public Registration updateRegistration(Long registrationId, Registration updatedRegistration, Authentication authentication) {
        Registration existingRegistration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizamos los campos del registro con los datos de usuario actuales
        existingRegistration.setName(user.getName());
        existingRegistration.setEmail(user.getEmail());
        existingRegistration.setPhone(user.getPhone());
        existingRegistration.setAge(user.getAge());

        return registrationRepository.save(existingRegistration);
    }

    // Eliminar un registro
    public void deleteRegistration(Long registrationId, Authentication authentication) {
        
    }

    public boolean isUserRegistered(Long eventId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        List<Registration> existingRegistrations = registrationRepository.findByUserAndEvent(user, event);
        return !existingRegistrations.isEmpty();
    }
}
