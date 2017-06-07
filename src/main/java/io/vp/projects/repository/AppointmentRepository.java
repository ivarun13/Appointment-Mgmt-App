package io.vp.projects.repository;

import io.vp.projects.domain.Appointment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query("select appointment from Appointment appointment where appointment.user.login = ?#{principal.username}")
    List<Appointment> findByUserIsCurrentUser();

}
