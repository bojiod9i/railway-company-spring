package ru.tsystems.railway.repository.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.repository.TicketRepository;

import javax.persistence.Query;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TicketRepositoryImpl extends GenericRepositoryImpl<Ticket> implements TicketRepository {

    @Override
    public Ticket save(Ticket entity) {
        entity.setPurchaseDate(new Date());
        return super.save(entity);
    }

    @Override
    public Set<Ticket> getTicketsBetweenDate(Date from, Date to) {
        Query query = entityManager.createQuery("SELECT t FROM Ticket t " +
                "WHERE t.purchaseDate >= :fromDate AND t.purchaseDate <= :toDate");
        query.setParameter("fromDate", from);
        query.setParameter("toDate", to);
        return new HashSet<>(query.getResultList());
    }
}
