package ru.tsystems.railway.domain.service;

import org.hibernate.validator.constraints.NotEmpty;
import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a ticket.
 *
 * @author lazukovvs@gmail.com
 */
@Entity
@Table(name = "Ticket")
public class Ticket extends AbstractDomainEntity {

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tickets")
    @OrderBy("departureDate ASC")
    @NotEmpty
    private Set<Route> routes;

    @ManyToOne
    @JoinColumn(name = "PassengerId")
    @NotNull
    private Passenger passenger;

    @Column(name = "PurchaseDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

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

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
