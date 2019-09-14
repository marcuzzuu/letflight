package com.hackyeah.lotflights.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Getter
@Setter
@ToString
public class AirportAvailable {


    private String airportName;

    private GeoJsonPoint geoJsonPoint;

    public AirportAvailable(GeoJsonPoint geoJsonPoint, String airportName) {
      this.geoJsonPoint=geoJsonPoint;
      this.airportName=airportName;
    }

}
