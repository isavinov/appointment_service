package com.example.appointment.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "physician")
public class Physician {

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

    @OneToMany(mappedBy = "physician")
    private Set<AppointmentSlot> appointments;
}
