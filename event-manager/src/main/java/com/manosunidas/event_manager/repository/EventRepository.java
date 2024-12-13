package com.manosunidas.event_manager.repository;
import com.manosunidas.event_manager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE (:category IS NULL OR e.category = :category) AND (:location IS NULL OR e.location = :location) AND (:date IS NULL OR e.date = :date)")
    List<Event> findByFilters(String category, String location, String date);

    @Query("SELECT e FROM Event e WHERE (:keyword IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) ORDER BY e.date ASC")
    List<Event> searchEvents(String keyword);

    @Query("SELECT e.name, COUNT(r.id) FROM Event e LEFT JOIN Registration r ON e.id = r.event.id GROUP BY e.id, e.name")
    List<Object[]> getEventRegistrationCounts();

    @Query("SELECT e.category, COUNT(e.id) FROM Event e GROUP BY e.category ORDER BY COUNT(e.id) DESC")
    List<Object[]> getPopularCategories();

    @Query("SELECT e FROM Event e WHERE (:startDate IS NULL OR e.date >= :startDate) AND (:endDate IS NULL OR e.date <= :endDate)")
    List<Event> findByDateRange(String startDate, String endDate);
}