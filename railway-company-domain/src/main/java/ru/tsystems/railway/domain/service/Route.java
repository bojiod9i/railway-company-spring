package ru.tsystems.railway.domain.service;

import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Route")
public class Route extends AbstractDomainEntity {

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
    private Date departureCalendar;

    @Column(name = "ArrivalCalendar")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalCalendar;

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

    public Date getDepartureCalendar() {
        return departureCalendar;
    }

    public void setDepartureCalendar(Date departureCalendar) {
        this.departureCalendar = departureCalendar;
    }

    public Date getArrivalCalendar() {
        return arrivalCalendar;
    }

    public void setArrivalCalendar(Date arrivalCalendar) {
        this.arrivalCalendar = arrivalCalendar;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
