package ru.tsystems.railway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.railway.domain.service.Station;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Service
public class SampleService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Station createUser() {
        try {
            Query query = entityManager
                    .createQuery("select u from Station u where u.name = ?");
            query.setParameter(1, "Moscow");
            return (Station) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}