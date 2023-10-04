package com.example.appointment.repository;

import com.example.appointment.domain.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentSlot, Long> {

    /**
     * Find an appointment by UUID
     *
     * @param uuid Appointment UUID
     * @return Appointment
     */
    Optional<AppointmentSlot> findOneByUuid(UUID uuid);

    /**
     * Find all appointments scheduled for a physician on a given date
     *
     * @param physicianId Physician UUID
     * @param date        Date
     * @return Collection of appointments
     */
    @Query("select a from AppointmentSlot a where a.physician.uuid = :physicianId and cast(a.startDateTime as localdate) = :date and a.patient is null")
    Collection<AppointmentSlot> getAvailableAppointments(UUID physicianId, LocalDate date);

    /**
     * Find all appointments scheduled for a physician on a given date and time. Implemented to check if there are already scheduled appointments for a given time slot
     *
     * @param physicianId Physician UUID
     * @param date        Date and Time
     * @return Collection of appointments
     */
    @Query("select a from AppointmentSlot a join a.physician p where p.uuid = :physicianId and a.startDateTime < :date and a.endDateTime > :date")
    Collection<AppointmentSlot> findAllScheduledAppointments(UUID physicianId, LocalDateTime date);

    /**
     * Find all booked appointments for a patient
     * @param patientId Patient UUID
     * @return Collection of appointments
     */
    @Query("select a from AppointmentSlot a join a.patient p where p.uuid = :patientId")
    Collection<AppointmentSlot> findBookedAppointments(UUID patientId);
}
