package io.vp.projects.service.impl;

import io.vp.projects.service.DayService;
import io.vp.projects.domain.Day;
import io.vp.projects.repository.DayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Day.
 */
@Service
@Transactional
public class DayServiceImpl implements DayService{

    private final Logger log = LoggerFactory.getLogger(DayServiceImpl.class);
    
    @Inject
    private DayRepository dayRepository;

    /**
     * Save a day.
     *
     * @param day the entity to save
     * @return the persisted entity
     */
    public Day save(Day day) {
        log.debug("Request to save Day : {}", day);
        Day result = dayRepository.save(day);
        return result;
    }

    /**
     *  Get all the days.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Day> findAll() {
        log.debug("Request to get all Days");
        List<Day> result = dayRepository.findAll();

        return result;
    }

    /**
     *  Get one day by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Day findOne(Long id) {
        log.debug("Request to get Day : {}", id);
        Day day = dayRepository.findOne(id);
        return day;
    }

    /**
     *  Delete the  day by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Day : {}", id);
        dayRepository.delete(id);
    }
}
