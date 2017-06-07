package io.vp.projects.repository;

import io.vp.projects.domain.Invitee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Invitee entity.
 */
@SuppressWarnings("unused")
public interface InviteeRepository extends JpaRepository<Invitee,Long> {

}
