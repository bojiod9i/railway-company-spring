package ru.tsystems.railway.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tsystems.railway.domain.service.Route;
import ru.tsystems.railway.domain.service.Train;
import ru.tsystems.railway.repository.TrainRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Component
public class InitPeriodicallyTrain {

//    @Autowired
//    private TrainRepository trainRepository;
//    @Autowired
//    private TrainRepository trainRepository;
//
//    @Scheduled(cron = "0 0 0 1 * ?")
//    public void monthlyTrain() {
//        Calendar fromCalendar = Calendar.getInstance(), toCalendar = Calendar.getInstance();
//        fromCalendar.add(Calendar.MONTH, -1);
//        Set<Train> trainSet = trainRepository.searchBetweenDepartureDates(fromCalendar.getTime(), toCalendar.getTime());
//        for (Train train : trainSet) {
//            if (train.getPeriod() == Train.Period.EVERYMONTH) {
//                if(train.getRoutes().iterator().next().getDepartureDate().compareTo(fromCalendar.getTime()) > 0){
//                    Train newTrain = new Train(train.getSeats());
//                    for (Route route : train.getRoutes()) {
//                        Calendar departureCalendar = Calendar.getInstance();
//                        departureCalendar.setTime(route.getDepartureDate());
//                        departureCalendar.add(Calendar.MONTH, 1);
//                        Calendar arrivalCalendar = Calendar.getInstance();
//                        arrivalCalendar.setTime(route.getArrivalDate());
//                        arrivalCalendar.add(Calendar.MONTH, 1);
//                        Route newRoute = new Route();
//                        newRoute.setDepartureDate(departureCalendar.getTime());
//                        newRoute.setArrivalDate(arrivalCalendar.getTime());
//                        newRoute.setDepartureStation(route.getDepartureStation());
//                        newRoute.setArrivalStation(route.getArrivalStation());
//                        newRoute.setTrain(newTrain);
//
//                    }
//                }
//            }
//        }
//    }
//
//    @Scheduled(cron = "0 0 0 ? * MON")
//    public void weeklyTrain() {
//
//    }
//
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void dailyTrain() {
//
//    }
}
