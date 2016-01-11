package ru.tsystems.railway.repository;


import ru.tsystems.railway.domain.service.Station;

public interface StationRepository extends GenericRepository<Station> {

    Station getStationByName(String name);

    Station getStationByRuName(String name);

}
