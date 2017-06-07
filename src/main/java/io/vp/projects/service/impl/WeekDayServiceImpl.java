package io.vp.projects.service.impl;

import io.vp.projects.service.WeekDayService;
import io.vp.projects.domain.WeekDay;
import io.vp.projects.repository.WeekDayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WeekDay.
 */
@Service
@Transactional
public class WeekDayServiceImpl implements WeekDayService{

    private final Logger log = LoggerFactory.getLogger(WeekDayServiceImpl.class);
    
    @Inject
    private WeekDayRepository weekDayRepository;

    /**
     * Save a weekDay.
     *
     * @param weekDay the entity to save
     * @return the persisted entity
     */
    public WeekDay save(WeekDay weekDay) {
        log.debug("Request to save WeekDay : {}", weekDay);
        WeekDay result = weekDayRepository.save(weekDay);
        return result;
    }

    /**
     *  Get all the weekDays.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<WeekDay> findAll() {
        log.debug("Request to get all WeekDays");
        List<WeekDay> result = weekDayRepository.findAll();

        return result;
    }

    /**
     *  Get one weekDay by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WeekDay findOne(Long id) {
        log.debug("Request to get WeekDay : {}", id);
        WeekDay weekDay = weekDayRepository.findOne(id);
        return weekDay;
    }

    /**
     *  Delete the  weekDay by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WeekDay : {}", id);
        weekDayRepository.delete(id);
    }
}
