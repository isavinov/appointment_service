package com.example.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhysicianNotFoundException extends RuntimeException{

    public PhysicianNotFoundException(String message) {
        super(message);
    }

    public PhysicianNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
