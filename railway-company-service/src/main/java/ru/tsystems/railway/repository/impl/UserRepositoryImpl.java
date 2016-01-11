package ru.tsystems.railway.repository.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.domain.accounts.UserRole;
import ru.tsystems.railway.repository.UserRepository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements UserRepository {

    @Override
    public User save(User entity) {
        entity.setCreateDate(new Date());
        return super.save(entity);
    }

    @Override
    public User findUserByEmail(String email) {
        Query query = entityManager.createQuery("SELECT user FROM User user WHERE user.email =:email");
        query.setParameter("email", email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Set<User> getUsersByRole(UserRole role) {
        Query query = entityManager.createQuery("SELECT user FROM User user WHERE user.userRole =:role");
        query.setParameter("role", role);
        return new HashSet<>(query.getResultList());
    }
}
