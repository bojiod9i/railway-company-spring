package ru.tsystems.railway.repository.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.railway.domain.service.Station;
import ru.tsystems.railway.domain.service.Train;
import ru.tsystems.railway.repository.TrainRepository;

import javax.persistence.Query;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TrainRepositoryImpl extends GenericRepositoryImpl<Train> implements TrainRepository {

    @Override
    public Set<Train> searchBetweenDepartureDates(Date from, Date to) {
        Query query = entityManager.createQuery("SELECT t FROM Train t JOIN t.routes route " +
                "WHERE route.departureDate >= :fromDate AND route.departureDate <= :toDate");
        query.setParameter("fromDate", from);
        query.setParameter("toDate", to);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Set<Train> searchPassingTrain(Station station, Date start, Date end) {
        Query query = entityManager.createQuery("SELECT t FROM Train t JOIN t.routes route " +
                "WHERE route.departureStation = :station AND route.departureDate >= :fromDate " +
                "AND route.departureDate <= :toDate");
        query.setParameter("fromDate", start);
        query.setParameter("toDate", end);
        query.setParameter("station", station);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Set<Train> searchTrainByParams(Station fromStation, Station toStation, Date startDate, Date endDate) {
        Query query = entityManager.createQuery("SELECT t FROM Train t JOIN t.routes route1 JOIN t.routes route2 " +
                "WHERE route1.departureStation = :fromStation AND route1.departureDate >= :fromDate " +
                "AND route2.arrivalDate <= :toDate AND route2.arrivalStation = :toStation");
        query.setParameter("fromDate", startDate);
        query.setParameter("toDate", endDate);
        query.setParameter("toStation", toStation);
        query.setParameter("fromStation", fromStation);
        return new HashSet<>(query.getResultList());
    }
}
