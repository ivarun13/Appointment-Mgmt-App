package io.vp.projects.service.impl;

import io.vp.projects.service.AppointmentService;
import io.vp.projects.domain.Appointment;
import io.vp.projects.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Appointment.
 */
@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{

    private final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);
    
    @Inject
    private AppointmentRepository appointmentRepository;

    /**
     * Save a appointment.
     *
     * @param appointment the entity to save
     * @return the persisted entity
     */
    public Appointment save(Appointment appointment) {
        log.debug("Request to save Appointment : {}", appointment);
        Appointment result = appointmentRepository.save(appointment);
        return result;
    }

    /**
     *  Get all the appointments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Appointment> findAll(Pageable pageable) {
        log.debug("Request to get all Appointments");
        Page<Appointment> result = appointmentRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one appointment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Appointment findOne(Long id) {
        log.debug("Request to get Appointment : {}", id);
        Appointment appointment = appointmentRepository.findOne(id);
        return appointment;
    }

    /**
     *  Delete the  appointment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Appointment : {}", id);
        appointmentRepository.delete(id);
    }
}
