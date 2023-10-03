package com.example.appointment.service;

import com.example.appointment.dto.AppointmentDTO;
import com.example.appointment.web_service.AppointmentDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    /**
     * Create appointment slots for a physician
     *
     * @param physicianUuid Physician UUID
     * @param appointments  List of appointments
     * @return Collection of appointment UUIDs
     */
    Collection<UUID> createAppointmentSlots(UUID physicianUuid, List<AppointmentDetails> appointments);


    /**
     * Find all available appointments for a physician on a given date
     *
     * @param physicianUuid Physician UUID
     * @param date          Date
     * @return Collection of appointments
     */
    Collection<AppointmentDTO> findAvailableAppointments(UUID physicianUuid, LocalDate date);
}
