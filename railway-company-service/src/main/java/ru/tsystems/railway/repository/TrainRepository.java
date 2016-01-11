package ru.tsystems.railway.repository;

import ru.tsystems.railway.domain.service.Station;
import ru.tsystems.railway.domain.service.Train;

import java.util.Date;
import java.util.Set;

public interface TrainRepository extends GenericRepository<Train>  {
    Set<Train> searchBetweenDepartureDates(Date from, Date to);

    Set<Train> searchPassingTrain(Station station, Date start, Date end);

    Set<Train> searchTrainByParams(Station fromStation, Station toStation, Date startDate, Date endDate);
}
