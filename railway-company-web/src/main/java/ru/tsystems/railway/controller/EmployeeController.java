package ru.tsystems.railway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.tsystems.railway.domain.service.Station;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.domain.service.Train;
import ru.tsystems.railway.exception.StationCreationException;
import ru.tsystems.railway.service.RailwayService;
import ru.tsystems.railway.util.DateFormatUtil;
import ru.tsystems.railway.util.MessagesDescription;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private RailwayService railwayService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/index")
    public String employeePage() {
        return "employee/index";
    }

    @RequestMapping(value = "/getAllStations")
    public ModelAndView getAllStations() {
        return new ModelAndView("redirect:/getAllStations");
    }

    @RequestMapping(value = "/newStation", method = RequestMethod.POST)
    public String addStation(
            @RequestParam(value = "stationEnName") String enName,
            @RequestParam(value = "stationRuName") String ruName,
            @RequestParam(value = "stationLat") Double latitude,
            @RequestParam(value = "stationLong") Double longitude,
            RedirectAttributes redirectAttributes, Principal principal) {

        try {
            Station station = railwayService.createStation(enName, ruName, latitude, longitude);
            redirectAttributes.addFlashAttribute("infoMsg",
                    messageSource.getMessage(MessagesDescription.STATION_SUCCESSFULLY_ADDED, null,
                            LocaleContextHolder.getLocale()));
            LOGGER.info("Station with id " + station.getId() + " was registered by " + principal.getName());
        } catch (StationCreationException e) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    messageSource.getMessage(MessagesDescription.CREATION_STATION_WITH_EXIST_NAME_ERROR, null,
                            LocaleContextHolder.getLocale()));
            LOGGER.error("Attempt create station with already existing name");
        }
        return "redirect:/employee/index";
    }

    @RequestMapping(value = "/newTrain", method = RequestMethod.POST)
    public String addTrain(@RequestParam Map<String, String> parameterMap, RedirectAttributes redirectAttributes,
                           Principal principal) {
        Integer seats = new Integer(parameterMap.get("seats"));
        Integer periodNumber = new Integer(parameterMap.get("period"));
        List<Long> departureStationIds = new ArrayList<>();
        List<Long> arrivalStationIds = new ArrayList<>();
        List<Date> departureDates = new ArrayList<>();
        List<Date> arrivalDates = new ArrayList<>();
        List<Double> costs = new ArrayList<>();
        for (int i = 0; parameterMap.get("departureStationId" + i) != null; ++i) {
            parameterMap.get("departureStationId" + i);
            departureStationIds.add(new Long(parameterMap.get("departureStationId" + i)));
            arrivalStationIds.add(new Long(parameterMap.get("arrivalStationId" + i)));
            String departureDateString = parameterMap.get("departureDate" + i) + " " + parameterMap.get("departureTime" + i);
            String arrivalDateString = parameterMap.get("arrivalDate" + i) + " " + parameterMap.get("arrivalTime" + i);
            try {
                departureDates.add(DateFormatUtil.DATETIME_FORMAT.parse(departureDateString));
                arrivalDates.add(DateFormatUtil.DATETIME_FORMAT.parse(arrivalDateString));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Illegal date format");
            }
            costs.add(new Double(parameterMap.get("cost" + i)));
        }
        Train train = railwayService.initTrain(departureStationIds, arrivalStationIds, departureDates, arrivalDates, costs,
                seats, Train.Period.getByValue(periodNumber));
        LOGGER.info("Train with id " + train.getId() + " was registered by " + principal.getName());
        redirectAttributes.addFlashAttribute("infoMsg",
                messageSource.getMessage(MessagesDescription.TRAIN_SUCCESSFULLY_ADDED, null,
                        LocaleContextHolder.getLocale()));
        return "redirect:/employee/index";
    }

    @RequestMapping(value = "/searchTrains", method = RequestMethod.GET)
    public String searchTrains(@RequestParam(value = "timetableFromDate", required = false) String dateFromStr,
                               @RequestParam(value = "timetableToDate", required = false) String dateToStr,
                               Model model) {
        if (dateFromStr == null || dateToStr == null) {
            return "employee/trainList";
        }
        Date dateFrom, dateTo;
        try {
            dateFrom = DateFormatUtil.DATE_FORMAT.parse(dateFromStr);
            dateTo = DateFormatUtil.DATE_FORMAT.parse(dateToStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal date format");
        }
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(dateTo);
        calendarTo.add(Calendar.DATE, 1);
        calendarTo.add(Calendar.SECOND, -1);
        model.addAttribute("foundTrains", railwayService.searchTrain(dateFrom, calendarTo.getTime()));
        return "employee/trainList";
    }

    @RequestMapping(value = "/lookTrain", method = RequestMethod.GET)
    public String lookTrain(@RequestParam(value = "trainId", required = false) Long trainId, Model model) {
        if (trainId != null) {
            model.addAttribute("infoTrain", railwayService.getTrainById(trainId));
        }
        return "employee/trainInfo";
    }

    //todo
    @RequestMapping(value = "/lookSellingTickets", method = RequestMethod.GET)
    public String lookPassengers(@RequestParam(value = "routeId") Long routeId, RedirectAttributes redirectAttributes) {
        Set<Ticket> tickets = railwayService.getTicketsByRoute(routeId);
        redirectAttributes.addFlashAttribute("tickets", tickets);
        return "redirect:/employee/index";
    }


}
