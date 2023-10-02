package com.example.appointment.service;

import com.example.appointment.domain.Appointment;
import com.example.appointment.web_service.CreateAppointmentRequest;

import java.util.Collection;
import java.util.UUID;

public interface AppointmentService {

    Collection<UUID> createAppointments(CreateAppointmentRequest appointments);
}
