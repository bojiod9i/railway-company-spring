package ru.tsystems.railway.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tsystems.railway.domain.AbstractDomainEntity;
import ru.tsystems.railway.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

@Repository
public abstract class GenericRepositoryImpl<T extends AbstractDomainEntity> implements GenericRepository<T> {

    @Autowired
    protected EntityManager entityManager;

    private Class<T> type;

    public GenericRepositoryImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T getEntityById(Long id) {
        return entityManager.find(type, id);
    }

    @Override
    public Set<T> entitySet() {
        String s = "FROM " + type.getSimpleName();
        Query query = entityManager.createQuery(s);
        return new HashSet<T>(query.getResultList());
    }

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
}
