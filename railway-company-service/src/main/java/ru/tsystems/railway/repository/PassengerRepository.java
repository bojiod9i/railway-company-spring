package ru.tsystems.railway.repository;

import ru.tsystems.railway.domain.service.Passenger;

import java.util.Date;

public interface PassengerRepository extends GenericRepository<Passenger> {

    Passenger getPassengerByParams(String firstName, String lastName, Date birthday);

}