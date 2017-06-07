package io.vp.projects.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.vp.projects.domain.Invitee;

import io.vp.projects.repository.InviteeRepository;
import io.vp.projects.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Invitee.
 */
@RestController
@RequestMapping("/api")
public class InviteeResource {

    private final Logger log = LoggerFactory.getLogger(InviteeResource.class);
        
    @Inject
    private InviteeRepository inviteeRepository;

    /**
     * POST  /invitees : Create a new invitee.
     *
     * @param invitee the invitee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invitee, or with status 400 (Bad Request) if the invitee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/invitees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Invitee> createInvitee(@Valid @RequestBody Invitee invitee) throws URISyntaxException {
        log.debug("REST request to save Invitee : {}", invitee);
        if (invitee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("invitee", "idexists", "A new invitee cannot already have an ID")).body(null);
        }
        Invitee result = inviteeRepository.save(invitee);
        return ResponseEntity.created(new URI("/api/invitees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("invitee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invitees : Updates an existing invitee.
     *
     * @param invitee the invitee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invitee,
     * or with status 400 (Bad Request) if the invitee is not valid,
     * or with status 500 (Internal Server Error) if the invitee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/invitees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Invitee> updateInvitee(@Valid @RequestBody Invitee invitee) throws URISyntaxException {
        log.debug("REST request to update Invitee : {}", invitee);
        if (invitee.getId() == null) {
            return createInvitee(invitee);
        }
        Invitee result = inviteeRepository.save(invitee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("invitee", invitee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invitees : get all the invitees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invitees in body
     */
    @RequestMapping(value = "/invitees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Invitee> getAllInvitees() {
        log.debug("REST request to get all Invitees");
        List<Invitee> invitees = inviteeRepository.findAll();
        return invitees;
    }

    /**
     * GET  /invitees/:id : get the "id" invitee.
     *
     * @param id the id of the invitee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invitee, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/invitees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Invitee> getInvitee(@PathVariable Long id) {
        log.debug("REST request to get Invitee : {}", id);
        Invitee invitee = inviteeRepository.findOne(id);
        return Optional.ofNullable(invitee)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invitees/:id : delete the "id" invitee.
     *
     * @param id the id of the invitee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/invitees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInvitee(@PathVariable Long id) {
        log.debug("REST request to delete Invitee : {}", id);
        inviteeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("invitee", id.toString())).build();
    }

}
