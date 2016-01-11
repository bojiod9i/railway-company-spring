package ru.tsystems.railway.service;


import ru.tsystems.railway.domain.service.Station;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.domain.service.Train;
import ru.tsystems.railway.exception.BuyTicketException;
import ru.tsystems.railway.exception.StationCreationException;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface RailwayService {

    Station createStation(String name, String ruName, Double latitude, Double longitude)
            throws StationCreationException;

    Set<Station> findAllStation();

    Train initTrain(List<Long> departureStationIds, List<Long> arrivalStationIds, List<Date> departureDates,
                    List<Date> arrivalDates, List<Double> costs, Integer seats, Train.Period period);

    Set<Train> searchTrain(Date dateFrom, Date dateTo);

    Train getTrainById(Long id);

    Set<Ticket> getTicketsByRoute(Long routeId);

    Ticket buyTicket(String email, Long trainId, Long startStationId, Long endStationId, String firstName,
                     String lastName, Date birthday) throws BuyTicketException;

    Ticket buyTicket(String email, Long trainId, Long startStationId, Long endStationId,
                     Long passengerId) throws BuyTicketException;

    Set<Train> getPassingTrain(Long stationId, Date startDate, Date endDate);

    Station getStationById(Long stationId);

    Set<Train> searchTrains(Long startStationId, Long endStationId, Date startDate, Date endDate);

    Double getCostRoute(Long trainId, Long startStationId, Long endStationId);

    Set<Ticket> getTicketsBetweenDate(Date from, Date to);

}
