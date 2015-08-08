package ru.tsystems.railway.domain.service;

import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Ticket")
public class Ticket extends AbstractDomainEntity {

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private Set<Route> routes;

    @ManyToOne
    @JoinColumn(name = "PassengerId")
    private Passenger passenger;

    public Ticket() {
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }
}
