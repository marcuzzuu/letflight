package com.hackyeah.lotflights.model;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class AirportAvailable {


    private String airportName;

    private GeoJsonPoint geoJsonPoint;

    public AirportAvailable(GeoJsonPoint geoJsonPoint, String airportName) {
      this.geoJsonPoint=geoJsonPoint;
      this.airportName=airportName;
    }

    public String getAirportName() {
        return airportName;
    }



    public GeoJsonPoint getGeoJsonPoint() {
        return geoJsonPoint;
    }


}
