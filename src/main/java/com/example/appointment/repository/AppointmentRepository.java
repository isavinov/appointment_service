package com.example.appointment.repository;

import com.example.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query("select a from Appointment a where a.physician.uuid = :physicianId and cast(a.startDateTime as localdate) = :date and a.patient is null")
    Collection<Appointment> getAvailableAppointments(UUID physicianId, LocalDate date);
}
