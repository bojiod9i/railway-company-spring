package ru.tsystems.railway.repository;

import ru.tsystems.railway.domain.service.Ticket;

import java.util.Date;
import java.util.Set;

public interface TicketRepository extends GenericRepository<Ticket> {
    Set<Ticket> getTicketsBetweenDate(Date from, Date to);
}
