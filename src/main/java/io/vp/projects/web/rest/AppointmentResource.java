package io.vp.projects.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.vp.projects.domain.Appointment;
import io.vp.projects.service.AppointmentService;
import io.vp.projects.web.rest.util.HeaderUtil;
import io.vp.projects.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Appointment.
 */
@RestController
@RequestMapping("/api")
public class AppointmentResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentResource.class);
        
    @Inject
    private AppointmentService appointmentService;

    /**
     * POST  /appointments : Create a new appointment.
     *
     * @param appointment the appointment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appointment, or with status 400 (Bad Request) if the appointment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appointments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) throws URISyntaxException {
        log.debug("REST request to save Appointment : {}", appointment);
        if (appointment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("appointment", "idexists", "A new appointment cannot already have an ID")).body(null);
        }
        Appointment result = appointmentService.save(appointment);
        return ResponseEntity.created(new URI("/api/appointments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("appointment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appointments : Updates an existing appointment.
     *
     * @param appointment the appointment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appointment,
     * or with status 400 (Bad Request) if the appointment is not valid,
     * or with status 500 (Internal Server Error) if the appointment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appointments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment) throws URISyntaxException {
        log.debug("REST request to update Appointment : {}", appointment);
        if (appointment.getId() == null) {
            return createAppointment(appointment);
        }
        Appointment result = appointmentService.save(appointment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("appointment", appointment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appointments : get all the appointments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of appointments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/appointments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Appointment>> getAllAppointments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Appointments");
        Page<Appointment> page = appointmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appointments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /appointments/:id : get the "id" appointment.
     *
     * @param id the id of the appointment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appointment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/appointments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        log.debug("REST request to get Appointment : {}", id);
        Appointment appointment = appointmentService.findOne(id);
        return Optional.ofNullable(appointment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /appointments/:id : delete the "id" appointment.
     *
     * @param id the id of the appointment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/appointments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        log.debug("REST request to delete Appointment : {}", id);
        appointmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("appointment", id.toString())).build();
    }

}
