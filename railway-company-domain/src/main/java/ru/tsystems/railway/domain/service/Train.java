package ru.tsystems.railway.domain.service;

import org.hibernate.validator.constraints.NotEmpty;
import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a train.
 *
 * @author lazukovvs@gmail.com
 */
@Entity
@Table(name = "Train")
public class Train extends AbstractDomainEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private Period period;

    @OneToMany(mappedBy = "train", fetch = FetchType.EAGER)
    @OrderBy("departureDate ASC")
    @NotEmpty
    private Set<Route> routes;

    @Column(name = "SEATS")
    @Min(1)
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
        SINGLE(0), EVERYWEEK(1), EVERYDAY(2), EVERYMONTH(3);

        int value;

        Period(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static Period getByValue(int value) {
            for (Period p : Period.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return null;
        }
    }
}
