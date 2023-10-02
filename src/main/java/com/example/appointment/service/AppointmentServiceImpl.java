package com.example.appointment.service;

import com.example.appointment.domain.Appointment;
import com.example.appointment.repository.AppointmentRepository;
import com.example.appointment.repository.PhysicianRepository;
import com.example.appointment.web_service.CreateAppointmentRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final PhysicianRepository physicianRepository;

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(PhysicianRepository physicianRepository, AppointmentRepository appointmentRepository) {
        this.physicianRepository = physicianRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public Collection<UUID> createAppointments(CreateAppointmentRequest appointments) {
        Collection<UUID> result = new ArrayList<>();
        for (com.example.appointment.web_service.Appointment appointment: appointments.getAppointment()){
            Appointment appointmentDomain = new Appointment();
            appointmentDomain.setUuid(UUID.randomUUID());
            appointmentDomain.setStartDateTime(toLocalDateTime(appointment.getStartDateTime()));
            appointmentDomain.setEndDateTime(toLocalDateTime(appointment.getStartDateTime()).plusMinutes(appointment.getDuration()));
            appointmentDomain.setPhysician(physicianRepository.findByUuid(UUID.fromString(appointment.getDoctorId())).orElseThrow());
            appointmentDomain.setUuid(UUID.randomUUID());
            appointmentRepository.save(appointmentDomain);
            result.add(appointmentDomain.getUuid());
        }
        return result;
    }

    private LocalDateTime toLocalDateTime(XMLGregorianCalendar xgc){
        ZonedDateTime utcZoned = xgc.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDateTime();
    }
}
