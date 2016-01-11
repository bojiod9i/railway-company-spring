package ru.tsystems.railway.domain.service;

import org.hibernate.validator.constraints.NotBlank;
import ru.tsystems.railway.domain.AbstractDomainEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Simple JavaBean domain object representing a station.
 *
 * @author lazukovvs@gmail.com
 */
@Entity
@Table(name = "Station")
public class Station extends AbstractDomainEntity {

    @Column(name = "Name")
    @NotBlank
    private String name;

    @Column(name = "ruName")
    @NotBlank
    private String ruName;

    @Column(name = "Latitude")
    @Min(-90)
    @Max(90)
    @NotNull
    private Double latitude;

    @Column(name = "Longitude")
    @Min(-180)
    @Max(180)
    @NotNull
    private Double longitude;

    protected Station() {
    }

    public Station(String name, String ruName, Double latitude, Double longitude) {
        this.name = name;
        this.ruName = ruName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }
}
