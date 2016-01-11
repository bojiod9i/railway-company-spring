package ru.tsystems.railway.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.service.*;
import ru.tsystems.railway.exception.BuyTicketException;
import ru.tsystems.railway.exception.StationCreationException;
import ru.tsystems.railway.repository.*;
import ru.tsystems.railway.service.AccountService;
import ru.tsystems.railway.service.RailwayService;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RailwayServiceImpl implements RailwayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RailwayServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public Set<Station> findAllStation() {
        return stationRepository.entitySet();
    }

    @Override
    @Transactional
    public Station createStation(String name, String ruName, Double latitude, Double longitude)
            throws StationCreationException {
        if (stationRepository.getStationByName(name) == null && stationRepository.getStationByRuName(ruName) == null) {
            Station station = new Station(name, ruName, latitude, longitude);
            stationRepository.save(station);
            return station;
        } else {
            throw new StationCreationException("Station already exist");
        }
    }

    @Override
    @Transactional
    public Train initTrain(List<Long> departureStationIds, List<Long> arrivalStationIds, List<Date> departureDates,
                           List<Date> arrivalDates, List<Double> costs, Integer seats, Train.Period period) {
        int listLength = departureStationIds.size();
        if (arrivalStationIds.size() != listLength || departureDates.size() != listLength ||
                arrivalDates.size() != listLength || costs.size() != listLength) {
            throw new IllegalArgumentException();
        }
        Train train = new Train(seats);
        train.setPeriod(period);
        for (int i = 0; i < listLength; ++i) {
            Route route = new Route();
            route.setDepartureStation(stationRepository.getEntityById(departureStationIds.get(i)));
            route.setArrivalStation(stationRepository.getEntityById(arrivalStationIds.get(i)));
            route.setDepartureDate(departureDates.get(i));
            route.setArrivalDate(arrivalDates.get(i));
            route.setCost(costs.get(i));
            route.setTrain(train);
            routeRepository.save(route);
        }
        trainRepository.save(train);
        return train;
    }

    @Override
    @Transactional
    public Set<Train> searchTrain(Date dateFrom, Date dateTo) {
        return trainRepository.searchBetweenDepartureDates(dateFrom, dateTo);
    }

    @Override
    @Transactional
    public Train getTrainById(Long id) {
        return trainRepository.getEntityById(id);
    }

    @Override
    @Transactional
    public Set<Ticket> getTicketsByRoute(Long routeId) {
        Route route = routeRepository.getEntityById(routeId);
        return route.getTickets();
    }

    @Override
    @Transactional
    public Set<Train> getPassingTrain(Long stationId, Date startDate, Date endDate) {
        Station station = stationRepository.getEntityById(stationId);
        return trainRepository.searchPassingTrain(station, startDate, endDate);
    }

    @Override
    @Transactional
    public Station getStationById(Long stationId) {
        return stationRepository.getEntityById(stationId);
    }

    @Override
    @Transactional
    public Set<Train> searchTrains(Long startStationId, Long endStationId, Date startDate, Date endDate) {
        Station startStation = stationRepository.getEntityById(startStationId);
        Station endStation = stationRepository.getEntityById(endStationId);
        return trainRepository.searchTrainByParams(startStation, endStation, startDate, endDate);
    }

    @Override
    @Transactional
    public Ticket buyTicket(String email, Long trainId, Long startStationId, Long endStationId, String firstName,
                            String lastName, Date birthday) throws BuyTicketException {
        Passenger p = passengerRepository.getPassengerByParams(firstName, lastName, birthday);
        if (p == null) {
            p = new Passenger(firstName, lastName, birthday);
            passengerRepository.save(p);
            accountService.getUserByEmail(email).getPassengers().add(p);
        }
        return buyTicket(email, trainId, startStationId, endStationId, p.getId());
    }

    @Override
    @Transactional
    public Ticket buyTicket(String email, Long trainId, Long startStationId, Long endStationId, Long passengerId)
            throws BuyTicketException {
        Ticket ticket = new Ticket();
        Passenger passenger = passengerRepository.getEntityById(passengerId);
        ticket.setPassenger(passenger);
        Train train = trainRepository.getEntityById(trainId);
        boolean startRecord = false;
        for (Route route : train.getRoutes()) {
            if (route.getDepartureStation().getId().equals(startStationId)) {
                startRecord = true;
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 10);
                if (route.getDepartureDate().compareTo(calendar.getTime()) <= 0) {
                    throw new BuyTicketException(BuyTicketException.Reason.EXPIRED_TIME);
                }
            }
            if (startRecord) {
                int soldTicketCount = 0;
                for (Ticket t : route.getTickets()) {
                    ++soldTicketCount;
                    if (t.getPassenger().getId().equals(passengerId)) {
                        throw new BuyTicketException(BuyTicketException.Reason.PASSENGER_ALREADY_REGISTER);
                    }
                }
                if (soldTicketCount >= train.getSeats()) {
                    throw new BuyTicketException(BuyTicketException.Reason.NO_AVAILABLE_SEATS);
                }
                ticket.getRoutes().add(route);
            }
            if (route.getArrivalStation().getId().equals(endStationId)) {
                break;
            }
        }
        User user = accountService.getUserByEmail(email);
        ticketRepository.save(ticket);
        user.getTickets().add(ticket);
        return ticket;
    }

    @Override
    @Transactional
    public Double getCostRoute(Long trainId, Long startStationId, Long endStationId) {
        Double result = 0D;
        boolean startCount = false;
        Train train = trainRepository.getEntityById(trainId);
        for (Route route : train.getRoutes()) {
            if (route.getDepartureStation().getId().equals(startStationId)) {
                startCount = true;
            }
            if (startCount) {
                result += route.getCost();
            }
            if (route.getArrivalStation().getId().equals(endStationId)) {
                return result;
            }
        }
        throw new IllegalArgumentException("Train don't pass via station");
    }

    @Override
    @Transactional
    public Set<Ticket> getTicketsBetweenDate(Date from, Date to) {
        return ticketRepository.getTicketsBetweenDate(from, to);
    }
}
