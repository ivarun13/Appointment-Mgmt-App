package io.vp.projects.repository;

import io.vp.projects.domain.WeekDay;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WeekDay entity.
 */
@SuppressWarnings("unused")
public interface WeekDayRepository extends JpaRepository<WeekDay,Long> {

}
