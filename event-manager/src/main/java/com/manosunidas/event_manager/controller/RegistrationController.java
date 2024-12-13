package com.manosunidas.event_manager.controller;

import com.manosunidas.event_manager.model.Event;
import com.manosunidas.event_manager.model.Registration;
import com.manosunidas.event_manager.model.User;
import com.manosunidas.event_manager.repository.UserRepository;
import com.manosunidas.event_manager.service.RegistrationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    public RegistrationController(RegistrationService registrationService, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    // Registrar un participante en un evento
    @PostMapping("/{eventId}")
    public Registration registerParticipant(@PathVariable Long eventId, Authentication authentication) {
        String username = authentication.getName();
        return registrationService.registerUserToEvent(eventId, username);
    }

    // Obtener todos los registros de un evento
    @GetMapping("/event/{eventId}")
    public List<Registration> getRegistrationsByEvent(@PathVariable Long eventId) {
        return registrationService.getRegistrationsByEvent(eventId);
    }

    // Obtener todos los eventos a los que el usuario está inscrito (usando el usuario autenticado)
    @GetMapping("/my-events")
    public List<Event> getUserEvents(Authentication authentication) {
        String username = authentication.getName(); // Obtener el username del usuario logueado desde el contexto de seguridad
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener la lista de eventos a los que el usuario está inscrito a través de las inscripciones
        return user.getRegistrations().stream()
                .map(Registration::getEvent)
                .collect(Collectors.toList());
    }


    // Obtener todos los registros de usuarios (solo ADMIN)
    @GetMapping("/")
    public List<Registration> getAllRegistrations(Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return registrationService.getAllRegistrations();
        }
        throw new RuntimeException("Acceso denegado");
    }

    // Actualizar un registro (solo ADMIN o el usuario correspondiente)
    @PutMapping("/{registrationId}")
    public Registration updateRegistration(@PathVariable Long registrationId,
                                           @RequestBody Registration updatedRegistration,
                                           Authentication authentication) {
        return registrationService.updateRegistration(registrationId, updatedRegistration, authentication);
    }

    // Eliminar un registro (solo ADMIN o el usuario correspondiente)
    @DeleteMapping("/{registrationId}")
    public void deleteRegistration(@PathVariable Long registrationId, Authentication authentication) {
        registrationService.deleteRegistration(registrationId, authentication);
    }

    @GetMapping("/isRegistered")
    public boolean isUserRegistered(@RequestParam Long eventId, Authentication authentication) {
        String username = authentication.getName();
        return registrationService.isUserRegistered(eventId, username);
    }
}
