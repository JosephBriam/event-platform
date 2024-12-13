package com.manosunidas.event_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private int age;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Ignorar la serialización recursiva del usuario
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}