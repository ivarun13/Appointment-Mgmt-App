package io.vp.projects.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Day.
 */
@Entity
@Table(name = "day")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "date")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Day date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Day appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Day addAppointments(Appointment appointment) {
        appointments.add(appointment);
        appointment.setDate(this);
        return this;
    }

    public Day removeAppointments(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setDate(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Day day = (Day) o;
        if(day.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, day.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Day{" +
            "id=" + id +
            ", date='" + date + "'" +
            '}';
    }
}
