package com.manosunidas.event_manager.service;

import com.manosunidas.event_manager.model.Event;
import com.manosunidas.event_manager.model.Volunteer;
import com.manosunidas.event_manager.repository.EventRepository;
import com.manosunidas.event_manager.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, EventRepository eventRepository) {
        this.volunteerRepository = volunteerRepository;
        this.eventRepository = eventRepository;
    }

    public Volunteer addVolunteer(Long eventId, String name, String email) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Volunteer volunteer = new Volunteer();
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setEvent(event);

        return volunteerRepository.save(volunteer);
    }

    public List<Volunteer> getVolunteersForEvent(Long eventId) {
        return volunteerRepository.findByEventId(eventId);
    }

    public void removeVolunteer(Long volunteerId) {
        volunteerRepository.deleteById(volunteerId);
    }
}