package com.hackyeah.lotflights.service;

import com.hackyeah.lotflights.model.AirportAvailable;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;

public class FlightsService {

    private List<AirportAvailable> airports;


    private List<AirportAvailable> addMockAirportAvailable()
    {
        this.airports=new ArrayList<>();
        this.airports.add(new AirportAvailable(new GeoJsonPoint(2.2,4.3),"Gioia Tauro"));
        return this.airports;
    }

    public List<AirportAvailable> getAirportsAvailable()
    {
        return this.airports;
    }

}
