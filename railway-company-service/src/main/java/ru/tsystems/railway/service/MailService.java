package ru.tsystems.railway.service;

import ru.tsystems.railway.domain.accounts.UserRole;
import ru.tsystems.railway.domain.service.Ticket;

import java.util.Locale;

public interface MailService {

    void sendWelcomeEmail(String to, String password, Locale locale);

    void sendWelcomeEmail(String to, Locale locale);

    void sendWelcomeEmployeeEmail(String to, String password);

    void sendEmployEmail(String to);

    void sendDismissEmail(String to);

    void sendNotifyPurchaseTicketEmail(String to, Ticket ticket, Locale locale) throws Exception;
}
