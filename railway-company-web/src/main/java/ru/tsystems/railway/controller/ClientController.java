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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.service.Station;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.domain.service.Train;
import ru.tsystems.railway.exception.BuyTicketException;
import ru.tsystems.railway.service.AccountService;
import ru.tsystems.railway.service.MailService;
import ru.tsystems.railway.service.RailwayService;
import ru.tsystems.railway.util.DateFormatUtil;
import ru.tsystems.railway.util.MessagesDescription;

import java.security.Principal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Controller
public class ClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RailwayService railwayService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/getAllStations", method = RequestMethod.GET)
    @ResponseBody
    public Set<Station> getAllStation() {
        return railwayService.findAllStation();
    }

    @RequestMapping(value = "/stationTimetable", method = RequestMethod.GET)
    public String stationTimetable(@RequestParam(value = "stationId", required = false) Long stationId,
                                   @RequestParam(value = "timetableFromDate", required = false) String startDateStr,
                                   @RequestParam(value = "timetableToDate", required = false) String endDateStr, Model model) {
        if (stationId == null || startDateStr == null || endDateStr == null) {
            return "client/stationTimetable";
        }
        Date startDate, endDate;
        try {
            startDate = DateFormatUtil.DATE_FORMAT.parse(startDateStr);
            endDate = DateFormatUtil.DATE_FORMAT.parse(endDateStr);
        } catch (ParseException e) {
            model.addAttribute("errorMsg", messageSource.getMessage(MessagesDescription.INVALID_INPUTTING_PARAMS, null,
                    LocaleContextHolder.getLocale()));
            return "client/stationTimetable";
        }
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.DATE, 1);
        endCalendar.add(Calendar.SECOND, -1);
        Set<Train> trainSet = railwayService.getPassingTrain(stationId, startDate, endCalendar.getTime());
        Station station = railwayService.getStationById(stationId);
        model.addAttribute("foundTrains", trainSet);
        model.addAttribute("station", station);
        return "client/stationTimetable";
    }

    @RequestMapping(value = "/client/increaseBalance", method = RequestMethod.POST)
    public String addPayment(@RequestParam("outSum") Double outSum, Principal principal,
                             RedirectAttributes redirectAttributes) {
        if (outSum <= 0) {
            throw new IllegalArgumentException("Payment is less than zero");
        }

        User user = accountService.getUserByEmail(principal.getName());
        if (accountService.addPayment(user.getId(), outSum)) {
            redirectAttributes.addFlashAttribute("infoMsg", messageSource.getMessage(
                    MessagesDescription.BALANCE_WAS_INCREASED, new Object[]{outSum}, LocaleContextHolder.getLocale()));
            LOGGER.info("The user with id = " + user.getId() + " replenished balance on " + outSum);
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", messageSource.getMessage(
                    MessagesDescription.BALANCE_INCREASE_ERROR, null, LocaleContextHolder.getLocale()));
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/searchTrain", method = RequestMethod.GET)
    public String searchTrain(@RequestParam(value = "departureStationId", required = false) Long startStationId,
                              @RequestParam(value = "arrivalStationId", required = false) Long endStationId,
                              @RequestParam(value = "scheduleFromDate", required = false) String startDateStr,
                              @RequestParam(value = "scheduleToDate", required = false) String endDateStr, Model model) {
        if (startStationId == null || endStationId == null || startDateStr == null || endDateStr == null) {
            return "client/searchTrain";
        }

        Date startDate, endDate;
        try {
            startDate = DateFormatUtil.DATE_FORMAT.parse(startDateStr);
            endDate = DateFormatUtil.DATE_FORMAT.parse(endDateStr);
        } catch (ParseException e) {
            model.addAttribute("errorMsg", messageSource.getMessage(MessagesDescription.INVALID_INPUTTING_PARAMS, null,
                    LocaleContextHolder.getLocale()));
            return "client/stationTimetable";
        }
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.DATE, 1);
        endCalendar.add(Calendar.SECOND, -1);

        Set<Train> trains = railwayService.searchTrains(startStationId, endStationId, startDate, endCalendar.getTime());
        model.addAttribute("foundTrains", trains);
        model.addAttribute("fromStation", railwayService.getStationById(startStationId));
        model.addAttribute("toStation", railwayService.getStationById(endStationId));
        return "client/searchTrain";
    }

    @RequestMapping(value = "/lookTrain", method = RequestMethod.GET)
    public String lookTrain(@RequestParam(value = "trainId", required = false) Long trainId, Model model) {
        if (trainId != null) {
            model.addAttribute("infoTrain", railwayService.getTrainById(trainId));
        }
        return "client/trainInfo";
    }

    @RequestMapping(value = "/client/buyTicket", method = RequestMethod.GET)
    public String buyPage(@RequestParam(value = "trainId") Long trainId,
                          @RequestParam(value = "startStationId", required = false) Long startStationId,
                          @RequestParam(value = "endStationId", required = false) Long endStationId, Model model) {
        Train train = railwayService.getTrainById(trainId);
        model.addAttribute("train", train);
        model.addAttribute("startStationId", startStationId);
        model.addAttribute("endStationId", endStationId);
        return "client/buyForm";
    }

    @RequestMapping(value = "/client/buy", method = RequestMethod.POST)
    public String buy(@RequestParam(value = "trainId") Long trainId,
                      @RequestParam(value = "startStationId") Long startStationId,
                      @RequestParam(value = "endStationId") Long endStationId,
                      @RequestParam(value = "passengerId", required = false) Long passengerId,
                      @RequestParam(value = "firstname", required = false) String firstname,
                      @RequestParam(value = "lastname", required = false) String lastname,
                      @RequestParam(value = "birthday", required = false) String birthdayStr,
                      RedirectAttributes redirectAttributes, Principal principal) {
        try {
            Ticket ticket;
            if (passengerId != null) {
                ticket = railwayService.buyTicket(principal.getName(), trainId, startStationId, endStationId, passengerId);
            } else if (firstname != null && lastname != null && birthdayStr != null) {
                Date birthday = null;
                try {
                    birthday = DateFormatUtil.DATE_FORMAT.parse(birthdayStr);
                } catch (ParseException e) {
                    LOGGER.error("Incorrect date format");
                }
                ticket = railwayService.buyTicket(principal.getName(), trainId, startStationId, endStationId,
                        firstname, lastname, birthday);
            } else {
                throw new IllegalArgumentException("Not enough input data");
            }
            try {
                mailService.sendNotifyPurchaseTicketEmail(principal.getName(), ticket, LocaleContextHolder.getLocale());
            } catch (Exception e) {
                LOGGER.error("Cant send ticket to mail");
            }
            redirectAttributes.addAttribute("infoMsg", messageSource.getMessage(MessagesDescription.TICKET_WAS_BOUGHT,
                    null, LocaleContextHolder.getLocale()));
        } catch (BuyTicketException e) {
            switch (e.getReason()) {
                case EXPIRED_TIME:
                    redirectAttributes.addAttribute("errorMsg",
                            messageSource.getMessage(MessagesDescription.EXPIRED_TIME_PURCHASE_TICKET,
                                    null, LocaleContextHolder.getLocale()));
                case NO_AVAILABLE_SEATS:
                    redirectAttributes.addAttribute("errorMsg",
                            messageSource.getMessage(MessagesDescription.ALL_TICKET_WAS_SOLD,
                                    null, LocaleContextHolder.getLocale()));
                case PASSENGER_ALREADY_REGISTER:
                    redirectAttributes.addAttribute("errorMsg",
                            messageSource.getMessage(MessagesDescription.PASSENGER_ALREADY_REGISTERED_ON_TRAIN,
                                    null, LocaleContextHolder.getLocale()));
            }
        }
        return "redirect:client/ticketsHistory";
    }

    @RequestMapping(value = "/client/ticketsHistory", method = RequestMethod.GET)
    public String ticketsHistory(Model model, Principal principal) {
        Set<Ticket> tickets = accountService.getAllTickets(principal.getName());
        model.addAttribute("tickets", tickets);
        return "client/ticketHistory";
    }

    @RequestMapping(value = "getCost", method = RequestMethod.GET)
    @ResponseBody
    public Double getCost(@RequestParam(value = "trainId") Long trainId,
                          @RequestParam(value = "startStationId") Long startStationId,
                          @RequestParam(value = "endStationId") Long endStationId) {
        return railwayService.getCostRoute(trainId, startStationId, endStationId);
    }
}
