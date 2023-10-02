package com.example.appointment.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;
}
