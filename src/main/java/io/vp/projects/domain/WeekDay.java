package io.vp.projects.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WeekDay.
 */
@Entity
@Table(name = "week_day")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeekDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "start_limit")
    private Integer startLimit;

    @Column(name = "end_limit")
    private Integer endLimit;

    @ManyToOne
    private Event event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAvailable() {
        return available;
    }

    public WeekDay available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getStartLimit() {
        return startLimit;
    }

    public WeekDay startLimit(Integer startLimit) {
        this.startLimit = startLimit;
        return this;
    }

    public void setStartLimit(Integer startLimit) {
        this.startLimit = startLimit;
    }

    public Integer getEndLimit() {
        return endLimit;
    }

    public WeekDay endLimit(Integer endLimit) {
        this.endLimit = endLimit;
        return this;
    }

    public void setEndLimit(Integer endLimit) {
        this.endLimit = endLimit;
    }

    public Event getEvent() {
        return event;
    }

    public WeekDay event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeekDay weekDay = (WeekDay) o;
        if(weekDay.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, weekDay.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WeekDay{" +
            "id=" + id +
            ", available='" + available + "'" +
            ", startLimit='" + startLimit + "'" +
            ", endLimit='" + endLimit + "'" +
            '}';
    }
}
