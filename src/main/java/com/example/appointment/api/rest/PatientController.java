package com.example.appointment.api.rest;

import com.example.appointment.dto.AppointmentResponse;
import com.example.appointment.dto.BookAppointmentSlotRequest;
import com.example.appointment.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final AppointmentService appointmentService;

    public PatientController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{uuid}/appointments")
    public Collection<AppointmentResponse> getBookedAppointmentSlots(@PathVariable("uuid") UUID patientUuid){
        return appointmentService.getBookedAppointmentSlots(patientUuid);
    }

    @PostMapping("/{uuid}/appointments")
    public AppointmentResponse bookAppointmentSlot(@PathVariable("uuid") UUID patientUuid, @RequestBody
            BookAppointmentSlotRequest request){
        return appointmentService.bookAppointmentSlot(request.appointmentSlotUuid(), patientUuid);
    }
}
