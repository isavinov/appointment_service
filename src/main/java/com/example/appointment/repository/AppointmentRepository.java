package com.example.appointment.repository;

import com.example.appointment.domain.AppointmentSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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
     * Find available appointments scheduled by a physician on a given date
     *
     * @param physicianId Physician UUID
     * @param date        Date
     * @return Collection of appointments
     */
    @Query("select a from AppointmentSlot a join fetch a.physician p where p.uuid = :physicianId and cast(a.startDateTime as localdate) = :date and a.patient is null")
    Collection<AppointmentSlot> getAvailableAppointments(UUID physicianId, LocalDate date);

    /**
     * Find all appointments scheduled for a physician on a given date and time. Implemented to check if there are already scheduled appointments for a given time slot
     *
     * @param physicianId Physician UUID
     * @param date        Date and Time
     * @return Collection of appointments
     */
    @Query("select a from AppointmentSlot a join fetch a.physician p where p.uuid = :physicianId and a.startDateTime < :date and a.endDateTime > :date")
    Collection<AppointmentSlot> findAllScheduledAppointments(UUID physicianId, LocalDateTime date);

    /**
     * Find all booked appointments for a patient
     * @param patientId Patient UUID
     * @return Collection of appointments
     */
    @Query("select a from AppointmentSlot a join fetch a.patient p join fetch a.physician where p.uuid = :patientId")
    Collection<AppointmentSlot> findBookedAppointments(UUID patientId);


    /**
     * Find an appointment by UUID and lock it for update
     *
     * @param uuid Appointment UUID
     * @return Appointment
     */
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<AppointmentSlot> findAndLockByUuid(UUID uuid);
}
