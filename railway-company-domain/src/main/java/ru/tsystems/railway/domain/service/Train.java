package ru.tsystems.railway.domain.service;

import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Train")
public class Train extends AbstractDomainEntity {

    @Enumerated(EnumType.STRING)
    private Period period;
    @OneToMany(mappedBy = "train", fetch = FetchType.EAGER)
    private Set<Route> routes;
    @Column(name = "SEATS")
    private Integer seats;

    public Train() {
    }

    public Train(Integer seats) {
        this.seats = seats;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public enum Period {
        SINGLE, EVERYWEEK, EVERYDAY, EVERYMONTH
    }
}
