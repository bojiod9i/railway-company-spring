package ru.tsystems.railway.repository.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.railway.domain.service.Station;
import ru.tsystems.railway.repository.StationRepository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class StationRepositoryImpl extends GenericRepositoryImpl<Station> implements StationRepository {

    @Override
    public Station getStationByName(String name) {
        Query query = entityManager.createQuery("SELECT s FROM Station s WHERE s.name = :name");
        query.setParameter("name", name);
        try {
            return (Station) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Station getStationByRuName(String name) {
        Query query = entityManager.createQuery("SELECT s FROM Station s WHERE s.ruName = :ruName");
        query.setParameter("ruName", name);
        try {
            return (Station) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
