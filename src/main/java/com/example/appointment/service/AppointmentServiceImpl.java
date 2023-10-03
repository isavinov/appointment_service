package com.example.appointment.service;

import com.example.appointment.domain.AppointmentSlot;
import com.example.appointment.domain.Physician;
import com.example.appointment.exception.CouldNotCreateAppointmentException;
import com.example.appointment.exception.PhysicianNotFoundException;
import com.example.appointment.repository.AppointmentRepository;
import com.example.appointment.repository.PhysicianRepository;
import com.example.appointment.web_service.AppointmentDetails;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final PhysicianRepository physicianRepository;

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(PhysicianRepository physicianRepository,
            AppointmentRepository appointmentRepository) {
        this.physicianRepository = physicianRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public Collection<UUID> createAppointmentSlots(UUID physicianUuid, List<AppointmentDetails> appointments) {
        Collection<UUID> result = new ArrayList<>();
        Physician physician = physicianRepository.findByUuid(physicianUuid)
                .orElseThrow(
                        () -> new PhysicianNotFoundException(String.format("Physician %s not found", physicianUuid)));

        for (AppointmentDetails appointment : appointments) {
            if (!appointmentRepository.findAllScheduledAppointments(physicianUuid,
                    toLocalDateTime(appointment.getStartDateTime())).isEmpty()
                    || !appointmentRepository.findAllScheduledAppointments(physicianUuid,
                            toLocalDateTime(appointment.getStartDateTime()).plusMinutes(appointment.getDuration().getMinutes()))
                    .isEmpty()) {
                throw new CouldNotCreateAppointmentException(
                        "Could not create an appointment due to appointment conflict");
            }
            result.add(createAppointment(physician, appointment));
        }
        return result;
    }

    private LocalDateTime toLocalDateTime(XMLGregorianCalendar xgc) {
        ZonedDateTime utcZoned = xgc.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDateTime();
    }

    private UUID createAppointment(Physician physician, AppointmentDetails appointmentDetails) {
        AppointmentSlot appointment = new AppointmentSlot();
        appointment.setUuid(UUID.randomUUID());
        appointment.setStartDateTime(toLocalDateTime(appointmentDetails.getStartDateTime()));
        appointment.setEndDateTime(toLocalDateTime(appointmentDetails.getStartDateTime()).plusMinutes(
                appointmentDetails.getDuration().getMinutes()));
        appointment.setPhysician(physician);
        appointment.setUuid(UUID.randomUUID());
        appointmentRepository.save(appointment);
        return appointment.getUuid();
    }
}
