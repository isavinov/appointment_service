package com.example.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CouldNotCreateAppointmentException extends RuntimeException{

    public CouldNotCreateAppointmentException(String message) {
        super(message);
    }

    public CouldNotCreateAppointmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
