package com.example.appointment.service;

import com.example.appointment.domain.AppointmentSlot;
import com.example.appointment.domain.Patient;
import com.example.appointment.domain.Physician;
import com.example.appointment.dto.AppointmentResponse;
import com.example.appointment.exception.*;
import com.example.appointment.repository.AppointmentRepository;
import com.example.appointment.repository.PatientRepository;
import com.example.appointment.repository.PhysicianRepository;
import com.example.appointment.web_service.AppointmentDetails;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;

import java.time.LocalDate;
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

    private final PatientRepository patientRepository;

    public AppointmentServiceImpl(PhysicianRepository physicianRepository, AppointmentRepository appointmentRepository,
            PatientRepository patientRepository) {
        this.physicianRepository = physicianRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public Collection<UUID> createAppointmentSlots(UUID physicianUuid, List<AppointmentDetails> appointments) {
        Collection<UUID> result = new ArrayList<>();
        Physician physician = physicianRepository.findByUuid(physicianUuid)
                .orElseThrow(
                        () -> new PhysicianNotFoundException(String.format("Physician %s not found", physicianUuid)));

        for (AppointmentDetails appointment : appointments) {
            //TODO: Check if there are conflicts with already scheduled appointments. How to optimise this?
            if (!appointmentRepository.findAllScheduledAppointments(physicianUuid,
                    toLocalDateTime(appointment.getStartDateTime())).isEmpty()
                    || !appointmentRepository.findAllScheduledAppointments(physicianUuid,
                            toLocalDateTime(appointment.getStartDateTime()).plusMinutes(appointment.getDuration().getMinutes()))
                    .isEmpty()) {
                throw new CouldNotCreateAppointmentSlotException(
                        "Could not create an appointment due to appointment conflict");
            }
            result.add(createAppointment(physician, appointment));
        }
        return result;
    }

    @Override
    @Transactional
    public Collection<AppointmentResponse> getAvailableAppointmentSlots(UUID physicianUuid, LocalDate date) {
        return appointmentRepository.getAvailableAppointments(physicianUuid, date)
                .stream()
                .map(AppointmentResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public AppointmentResponse bookAppointmentSlot(UUID appointmentUuid, UUID patientUuid) {
        AppointmentSlot appointment = appointmentRepository.findAndLockByUuid(appointmentUuid)
                .orElseThrow(() -> new AppointmentSlotNotFoundException(
                        String.format("Appointment %s not found", appointmentUuid)));

        if (appointment.getPatient() != null) {
            throw new AppointmentSlotAlreadyBookedException(
                    String.format("Appointment %s is already booked", appointmentUuid));
        }

        Patient patient = patientRepository.findOneByUuid(patientUuid)
                .orElseThrow(() -> new PatientNotFoundException(String.format("Patient %s not found", patientUuid)));
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        return AppointmentResponse.fromEntity(appointment);
    }

    @Override
    @Transactional
    public Collection<AppointmentResponse> getBookedAppointmentSlots(UUID patientId) {
        return appointmentRepository.findBookedAppointments(patientId)
                .stream()
                .map(AppointmentResponse::fromEntity)
                .toList();
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
