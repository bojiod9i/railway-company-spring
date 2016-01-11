package ru.tsystems.railway.service;

import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.accounts.UserRole;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.exception.UserCreationException;

import java.util.Set;

public interface AccountService {

    User createNewUser(String email, String password,  UserRole userRole) throws UserCreationException;

    Set<User> employeeSet();

    boolean dismissEmployee(Long employeeId);

    User getUserByEmail(String email);

    boolean employUser(Long userId);

    String getUserEmail(Long userId);

    boolean addPayment(Long userId, Double sum);

    Set<Ticket> getAllTickets(String email);
}
