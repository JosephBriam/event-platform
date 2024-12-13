package com.manosunidas.event_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private Integer availableSeats;
    private String bannerImageUrl;
    private String thumbnailImageUrl;

    private String category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonBackReference // Esto permite evitar ciclos con Registration
    private List<Registration> registrations;
}


