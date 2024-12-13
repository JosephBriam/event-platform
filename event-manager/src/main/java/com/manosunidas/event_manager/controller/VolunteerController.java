package com.manosunidas.event_manager.controller;

import com.manosunidas.event_manager.model.Volunteer;
import com.manosunidas.event_manager.service.VolunteerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public Volunteer addVolunteer(@RequestParam Long eventId, @RequestParam String name, @RequestParam String email) {
        return volunteerService.addVolunteer(eventId, name, email);
    }

    @GetMapping("/{eventId}")
    public List<Volunteer> getVolunteersForEvent(@PathVariable Long eventId) {
        return volunteerService.getVolunteersForEvent(eventId);
    }

    @DeleteMapping("/{volunteerId}")
    public void removeVolunteer(@PathVariable Long volunteerId) {
        volunteerService.removeVolunteer(volunteerId);
    }
}