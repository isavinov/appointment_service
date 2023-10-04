package com.example.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CouldNotCreateAppointmentSlotException extends RuntimeException{

    public CouldNotCreateAppointmentSlotException(String message) {
        super(message);
    }

    public CouldNotCreateAppointmentSlotException(String message, Throwable cause) {
        super(message, cause);
    }
}
