package com.example.appointment.dto;

import com.example.appointment.domain.AppointmentSlot;

import java.util.UUID;

public record AppointmentDTO(UUID uuid, PhysicianDTO physician, String startDateTime, String endDateTime) {

    public static AppointmentDTO fromEntity(AppointmentSlot appointmentSlot){
        return new AppointmentDTO(
                appointmentSlot.getUuid(),
                new PhysicianDTO(
                        appointmentSlot.getPhysician().getFirstName(),
                        appointmentSlot.getPhysician().getLastName(),
                        appointmentSlot.getPhysician().getMiddleName()
                ),
                appointmentSlot.getStartDateTime().toString(),
                appointmentSlot.getEndDateTime().toString()
        );
    }
    public record PhysicianDTO(String firstName, String lastName, String middleName) {
    }
}
