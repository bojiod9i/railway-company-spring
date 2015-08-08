package ru.tsystems.railway.domain.accounts;

import ru.tsystems.railway.domain.AbstractDomainEntity;
import ru.tsystems.railway.domain.service.Passenger;
import ru.tsystems.railway.domain.service.Ticket;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a user of service.
 *
 * @author lazukovvs@gmail.com
 */
@Entity
@Table(name = "User")
public class User extends AbstractDomainEntity {

    @Column(name = "Email", nullable = false, unique = true)
    private String email;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "UserRole")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Column(name = "Balance")
    private Double balance;
    @ManyToMany
    @JoinTable(name = "PassengerClientRelation",
            joinColumns = {@JoinColumn(name = "ClientId")},
            inverseJoinColumns = {@JoinColumn(name = "PassengerId")})
    private Set<Passenger> passengers;
    @OneToMany
    @JoinColumn(name = "TicketId")
    private Set<Ticket> tickets;

    @Column(name = "RegistrationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    protected User() {
    }

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Double getBalance() {
        if (balance == null) {
            return new Double(0);
        } else {
            return balance;
        }
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
