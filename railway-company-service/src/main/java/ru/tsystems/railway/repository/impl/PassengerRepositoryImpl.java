package ru.tsystems.railway.repository.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.railway.domain.service.Passenger;
import ru.tsystems.railway.repository.PassengerRepository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;

@Repository
public class PassengerRepositoryImpl extends GenericRepositoryImpl<Passenger> implements PassengerRepository {

    @Override
    public Passenger getPassengerByParams(String firstName, String lastName, Date birthday) {
        Query query = entityManager.createQuery("SELECT p FROM Passenger p WHERE p.firstName = :firstName " +
                "AND p.lastName = :lastName AND p.birthday = :birthday");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("birthday", birthday);
        try {
            return (Passenger) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
