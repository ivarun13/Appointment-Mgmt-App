package io.vp.projects.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "time_length", nullable = false)
    private Integer timeLength;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "max_invitee")
    private Integer maxInvitee;

    @NotNull
    @Column(name = "rolling_days", nullable = false)
    private Integer rollingDays;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WeekDay> weekdays = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeLength() {
        return timeLength;
    }

    public Event timeLength(Integer timeLength) {
        this.timeLength = timeLength;
        return this;
    }

    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    public String getUrl() {
        return url;
    }

    public Event url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxInvitee() {
        return maxInvitee;
    }

    public Event maxInvitee(Integer maxInvitee) {
        this.maxInvitee = maxInvitee;
        return this;
    }

    public void setMaxInvitee(Integer maxInvitee) {
        this.maxInvitee = maxInvitee;
    }

    public Integer getRollingDays() {
        return rollingDays;
    }

    public Event rollingDays(Integer rollingDays) {
        this.rollingDays = rollingDays;
        return this;
    }

    public void setRollingDays(Integer rollingDays) {
        this.rollingDays = rollingDays;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Event appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Event addAppointments(Appointment appointment) {
        appointments.add(appointment);
        appointment.setEvent(this);
        return this;
    }

    public Event removeAppointments(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setEvent(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<WeekDay> getWeekdays() {
        return weekdays;
    }

    public Event weekdays(Set<WeekDay> weekDays) {
        this.weekdays = weekDays;
        return this;
    }

    public Event addWeekdays(WeekDay weekDay) {
        weekdays.add(weekDay);
        weekDay.setEvent(this);
        return this;
    }

    public Event removeWeekdays(WeekDay weekDay) {
        weekdays.remove(weekDay);
        weekDay.setEvent(null);
        return this;
    }

    public void setWeekdays(Set<WeekDay> weekDays) {
        this.weekdays = weekDays;
    }

    public User getUser() {
        return user;
    }

    public Event user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if(event.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", timeLength='" + timeLength + "'" +
            ", url='" + url + "'" +
            ", description='" + description + "'" +
            ", maxInvitee='" + maxInvitee + "'" +
            ", rollingDays='" + rollingDays + "'" +
            '}';
    }
}
