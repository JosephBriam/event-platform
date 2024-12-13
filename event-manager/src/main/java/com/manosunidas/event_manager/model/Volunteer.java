package com.manosunidas.event_manager.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "volunteers")
@Data
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}