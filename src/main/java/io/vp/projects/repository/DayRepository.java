package io.vp.projects.repository;

import io.vp.projects.domain.Day;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Day entity.
 */
@SuppressWarnings("unused")
public interface DayRepository extends JpaRepository<Day,Long> {

}
