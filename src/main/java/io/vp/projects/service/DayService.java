package io.vp.projects.service;

import io.vp.projects.domain.Day;

import java.util.List;

/**
 * Service Interface for managing Day.
 */
public interface DayService {

    /**
     * Save a day.
     *
     * @param day the entity to save
     * @return the persisted entity
     */
    Day save(Day day);

    /**
     *  Get all the days.
     *  
     *  @return the list of entities
     */
    List<Day> findAll();

    /**
     *  Get the "id" day.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Day findOne(Long id);

    /**
     *  Delete the "id" day.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
