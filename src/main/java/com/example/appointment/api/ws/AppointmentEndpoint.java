package com.example.appointment.api.ws;

import com.example.appointment.service.AppointmentService;
import com.example.appointment.web_service.ScheduleAppointmentRequest;
import com.example.appointment.web_service.ScheduleAppointmentResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

@Endpoint
public class AppointmentEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/appointment/web-service";

    private final AppointmentService appointmentService;

    public AppointmentEndpoint(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "scheduleAppointmentRequest")
    @ResponsePayload
    public ScheduleAppointmentResponse getCountry(@RequestPayload ScheduleAppointmentRequest request) {
        ScheduleAppointmentResponse response = new ScheduleAppointmentResponse();
        response.getAppointmentId()
                .addAll(appointmentService.createAppointmentSlots(UUID.fromString(request.getPhysicianUuid()),
                        request.getAppointment()).stream().map(UUID::toString).toList());
        return response;
    }
}
