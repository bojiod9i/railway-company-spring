package ru.tsystems.railway.repository;

import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.accounts.UserRole;

import java.util.Set;

public interface UserRepository extends GenericRepository<User> {

    User findUserByEmail(String email);

    Set<User> getUsersByRole(UserRole role);
}
