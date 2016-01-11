package ru.tsystems.railway.repository;


import ru.tsystems.railway.domain.AbstractDomainEntity;

import java.util.Set;

public interface GenericRepository<T extends AbstractDomainEntity> {

    T getEntityById(Long id);

    Set<T> entitySet();

    T save(T entity);
}
