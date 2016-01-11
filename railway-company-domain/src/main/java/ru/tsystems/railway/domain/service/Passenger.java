package ru.tsystems.railway.domain.service;


import org.hibernate.validator.constraints.NotBlank;
import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a passenger.
 *
 * @author lazukovvs@gmail.com
 */
@Entity
@Table(name = "Passenger")
public class Passenger extends AbstractDomainEntity {

    @Column(name = "FirstName")
    @NotBlank
    private String firstName;

    @Column(name = "LastName")
    @NotBlank
    private String lastName;

    @Column(name = "Birthday")
    @NotNull
    private Date birthday;

    @OneToMany(mappedBy = "passenger", fetch = FetchType.EAGER)
    private Set<Ticket> tickets;

    protected Passenger() {
    }

    public Passenger(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
