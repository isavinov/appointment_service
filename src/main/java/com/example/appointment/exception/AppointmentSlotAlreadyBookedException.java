package com.example.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppointmentSlotAlreadyBookedException extends RuntimeException{

        public AppointmentSlotAlreadyBookedException(String message) {
            super(message);
        }

        public AppointmentSlotAlreadyBookedException(String message, Throwable cause) {
            super(message, cause);
        }
}
