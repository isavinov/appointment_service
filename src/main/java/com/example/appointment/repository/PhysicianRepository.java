package com.example.appointment.repository;

import com.example.appointment.domain.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long>{

    Optional<Physician> findByUuid(UUID uuid);

}
