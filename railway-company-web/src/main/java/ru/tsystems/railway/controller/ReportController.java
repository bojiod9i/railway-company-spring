package ru.tsystems.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.service.RailwayService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping(value = "/webService")
public class ReportController {

    @Autowired
    private RailwayService railwayService;

    @RequestMapping(value = "{startDate}/{endDate}", method = RequestMethod.GET)
    @ResponseBody
    public Set<Ticket> getTickets(@PathVariable String startDate, @PathVariable String endDate) {
        Date dateFrom = null, dateTo = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFrom = dateFormat.parse(startDate);
            dateTo = dateFormat.parse(endDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Incorrect date format" + startDate + ' ' + endDate);
        }
        return railwayService.getTicketsBetweenDate(dateFrom, dateTo);
    }



}
