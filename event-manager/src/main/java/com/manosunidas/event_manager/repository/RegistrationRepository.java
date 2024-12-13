package com.manosunidas.event_manager.repository;

import com.manosunidas.event_manager.model.Event;
import com.manosunidas.event_manager.model.Registration;
import com.manosunidas.event_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByUser(User user);  // Obtener registros por usuario

    void deleteById(Long id);  // Eliminar registro por ID
    List<Registration> findByUserAndEvent(User user, Event event);

}