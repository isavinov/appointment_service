package com.example.appointment.api.rest;

import com.example.appointment.dto.AppointmentResponse;
import com.example.appointment.service.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(params = { "physician", "date" })
    public Collection<AppointmentResponse> getAvailableSlots(@RequestParam("physician") UUID physicianUuid,
            @RequestParam LocalDate date) {
        return appointmentService.getAvailableAppointmentSlots(physicianUuid, date);
    }

}
