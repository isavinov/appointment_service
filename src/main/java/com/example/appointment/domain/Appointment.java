package com.example.appointment.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="appointment")
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "start_date")
    private LocalDateTime startDateTime;

    @Column(name = "end_date")
    private LocalDateTime endDateTime;

    @ManyToOne
    private Physician physician;

    @ManyToOne
    private Patient patient;

}
