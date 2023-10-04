package com.example.appointment.repository;

import com.example.appointment.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long>{

    Optional<Patient> findOneByUuid(UUID uuid);
}
