package ru.tsystems.railway.domain.service;

import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * Simple JavaBean domain object representing a route between two stations.
 *
 * @author lazukovvs@gmail.com
 */
@Entity
@Table(name = "Route")
public class Route extends AbstractDomainEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "route_ticket_relation", joinColumns = {@JoinColumn(name = "RouteId", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "TicketId", nullable = false)})
    private Set<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "TrainId")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "DepartureStationId")
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "ArrivalStationId")
    private Station arrivalStation;

    @Column(name = "DepartureDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;

    @Column(name = "ArrivalCalendar")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDate;

    @Column(name = "Cost")
    private Double cost;

    public Route() {
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
