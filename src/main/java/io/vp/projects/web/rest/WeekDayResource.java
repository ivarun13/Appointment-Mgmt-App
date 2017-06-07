package io.vp.projects.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.vp.projects.domain.WeekDay;
import io.vp.projects.service.WeekDayService;
import io.vp.projects.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WeekDay.
 */
@RestController
@RequestMapping("/api")
public class WeekDayResource {

    private final Logger log = LoggerFactory.getLogger(WeekDayResource.class);
        
    @Inject
    private WeekDayService weekDayService;

    /**
     * POST  /week-days : Create a new weekDay.
     *
     * @param weekDay the weekDay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weekDay, or with status 400 (Bad Request) if the weekDay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/week-days",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeekDay> createWeekDay(@RequestBody WeekDay weekDay) throws URISyntaxException {
        log.debug("REST request to save WeekDay : {}", weekDay);
        if (weekDay.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("weekDay", "idexists", "A new weekDay cannot already have an ID")).body(null);
        }
        WeekDay result = weekDayService.save(weekDay);
        return ResponseEntity.created(new URI("/api/week-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("weekDay", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /week-days : Updates an existing weekDay.
     *
     * @param weekDay the weekDay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weekDay,
     * or with status 400 (Bad Request) if the weekDay is not valid,
     * or with status 500 (Internal Server Error) if the weekDay couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/week-days",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeekDay> updateWeekDay(@RequestBody WeekDay weekDay) throws URISyntaxException {
        log.debug("REST request to update WeekDay : {}", weekDay);
        if (weekDay.getId() == null) {
            return createWeekDay(weekDay);
        }
        WeekDay result = weekDayService.save(weekDay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("weekDay", weekDay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /week-days : get all the weekDays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of weekDays in body
     */
    @RequestMapping(value = "/week-days",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<WeekDay> getAllWeekDays() {
        log.debug("REST request to get all WeekDays");
        return weekDayService.findAll();
    }

    /**
     * GET  /week-days/:id : get the "id" weekDay.
     *
     * @param id the id of the weekDay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weekDay, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/week-days/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WeekDay> getWeekDay(@PathVariable Long id) {
        log.debug("REST request to get WeekDay : {}", id);
        WeekDay weekDay = weekDayService.findOne(id);
        return Optional.ofNullable(weekDay)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /week-days/:id : delete the "id" weekDay.
     *
     * @param id the id of the weekDay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/week-days/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWeekDay(@PathVariable Long id) {
        log.debug("REST request to delete WeekDay : {}", id);
        weekDayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("weekDay", id.toString())).build();
    }

}
