package com.manosunidas.event_manager.service;



import com.manosunidas.event_manager.model.Event;
import com.manosunidas.event_manager.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event createEvent(Event event) {
        // Validaciones adicionales
        if (event.getBannerImageUrl() == null || event.getThumbnailImageUrl() == null) {
            throw new RuntimeException("Se requieren ambas imágenes para el evento.");
        }
        if (event.getCategory() == null || event.getCategory().isEmpty()) {
            throw new RuntimeException("Debe asignar una categoría al evento.");
        }

        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = getEventById(id);
        event.setName(eventDetails.getName());
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setLocation(eventDetails.getLocation());
        event.setDate(eventDetails.getDate());
        event.setTime(eventDetails.getTime());
        event.setAvailableSeats(eventDetails.getAvailableSeats());
        event.setCategory(eventDetails.getCategory());
        event.setBannerImageUrl(eventDetails.getBannerImageUrl());
        event.setThumbnailImageUrl(eventDetails.getThumbnailImageUrl());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getEventsByFilters(String category, String location, String date) {
        return eventRepository.findByFilters(category, location, date);
    }

    public List<Event> searchEvents(String keyword) {
        return eventRepository.searchEvents(keyword);
    }

    public Map<String, Object> getEventStatistics() {
        long totalEvents = eventRepository.count();
        List<Object[]> popularCategories = eventRepository.getPopularCategories();

        String mostPopularCategory = popularCategories.isEmpty()
                ? "Sin categoría"
                : (String) popularCategories.get(0)[0];

        long totalRegistrations = eventRepository.getEventRegistrationCounts()
                .stream()
                .mapToLong(arr -> (long) arr[1]) // Asegúrate de que el conteo esté en la segunda posición del arreglo
                .sum();

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalEvents", totalEvents);
        statistics.put("totalRegistrations", totalRegistrations);
        statistics.put("mostPopularCategory", mostPopularCategory);
        return statistics;
    }


    public List<Event> getEventsByDateRange(String startDate, String endDate) {
        return eventRepository.findByDateRange(startDate, endDate);
    }
}