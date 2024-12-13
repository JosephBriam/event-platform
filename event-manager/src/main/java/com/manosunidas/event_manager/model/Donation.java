package com.manosunidas.event_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "donations")
@Data
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount; // Monto de la donación
    private String donorName; // Nombre del donante
    private LocalDate date; // Fecha de la donación

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Usuario que hizo la donación
}
