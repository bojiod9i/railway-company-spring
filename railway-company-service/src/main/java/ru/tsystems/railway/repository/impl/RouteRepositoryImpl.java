package ru.tsystems.railway.repository.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.railway.domain.service.Route;
import ru.tsystems.railway.repository.RouteRepository;

@Repository
public class RouteRepositoryImpl extends GenericRepositoryImpl<Route> implements RouteRepository {

}
