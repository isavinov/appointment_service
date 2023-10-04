package com.example.appointment.dto;

import com.example.appointment.domain.AppointmentSlot;

import java.util.UUID;

public record AppointmentResponse(UUID uuid, String startDateTime, String endDateTime) {

    public static AppointmentResponse fromEntity(AppointmentSlot appointmentSlot){
        return new AppointmentResponse(
                appointmentSlot.getUuid(),
                appointmentSlot.getStartDateTime().toString(),
                appointmentSlot.getEndDateTime().toString()
        );
    }

}
