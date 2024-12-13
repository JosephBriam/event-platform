package com.manosunidas.event_manager.controller;

import com.manosunidas.event_manager.model.Event;
import com.manosunidas.event_manager.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        return eventService.updateEvent(id, eventDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/search")
    public List<Event> searchEvents(@RequestParam(required = false) String category,
                                    @RequestParam(required = false) String location,
                                    @RequestParam(required = false) String date) {
        return eventService.getEventsByFilters(category, location, date);
    }

    @GetMapping("/search/keyword")
    public List<Event> searchEventsByKeyword(@RequestParam(required = false) String keyword) {
        return eventService.searchEvents(keyword);
    }

    @GetMapping("/featured")
    public List<Event> getFeaturedEvents() {
        return eventService.getAllEvents()
                .stream()
                .sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
                .limit(4)
                .collect(Collectors.toList());
    }

    // Endpoint para estadísticas
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return eventService.getEventStatistics();
    }

}
