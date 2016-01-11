package ru.tsystems.railway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.accounts.UserRole;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.exception.UserCreationException;
import ru.tsystems.railway.repository.TicketRepository;
import ru.tsystems.railway.repository.UserRepository;
import ru.tsystems.railway.service.AccountService;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createNewUser(String email, String password, UserRole userRole) throws UserCreationException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            throw new UserCreationException("Email already exist");
        }
        user = new User(email, password, userRole);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Set<User> employeeSet() {
        return userRepository.getUsersByRole(UserRole.ROLE_EMPLOYEE);
    }

    @Override
    @Transactional
    public boolean dismissEmployee(Long employeeId) {
        User user = userRepository.getEntityById(employeeId);
        if (user.getUserRole() == UserRole.ROLE_EMPLOYEE) {
            user.setUserRole(UserRole.ROLE_CLIENT);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    @Transactional
    public boolean employUser(Long userId) {
        User user = userRepository.getEntityById(userId);
        if (user != null && user.getUserRole() == UserRole.ROLE_CLIENT) {
            user.setUserRole(UserRole.ROLE_EMPLOYEE);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public String getUserEmail(Long userId) {
        return userRepository.getEntityById(userId).getEmail();
    }

    @Override
    @Transactional
    public boolean addPayment(Long userId, Double sum) {
        User user = userRepository.getEntityById(userId);
        user.setBalance(user.getBalance() + sum);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public Set<Ticket> getAllTickets(String email) {
        User user = userRepository.findUserByEmail(email);
        return user.getTickets();
    }

}
