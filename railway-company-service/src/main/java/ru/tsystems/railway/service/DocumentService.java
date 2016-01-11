package ru.tsystems.railway.service;

import ru.tsystems.railway.domain.service.Ticket;

public interface DocumentService {

    byte[] ticketPdfView(Ticket ticket) throws Exception;
}
