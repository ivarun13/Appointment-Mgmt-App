package io.vp.projects.service;

import io.vp.projects.domain.WeekDay;

import java.util.List;

/**
 * Service Interface for managing WeekDay.
 */
public interface WeekDayService {

    /**
     * Save a weekDay.
     *
     * @param weekDay the entity to save
     * @return the persisted entity
     */
    WeekDay save(WeekDay weekDay);

    /**
     *  Get all the weekDays.
     *  
     *  @return the list of entities
     */
    List<WeekDay> findAll();

    /**
     *  Get the "id" weekDay.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WeekDay findOne(Long id);

    /**
     *  Delete the "id" weekDay.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
