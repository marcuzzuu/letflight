package com.hackyeah.lotflights.service;

import com.hackyeah.lotflights.model.Airport;

import java.util.ArrayList;
import java.util.List;

public class AirportAvailableService {

    private List<Airport> airports;


    private List<Airport> addMockAirportAvailable()
    {
        this.airports=new ArrayList<>();
        this.airports.add(new Airport(3.77,2.76));
        return this.airports;
    }

    public List<Airport> getAirportsAvailable()
    {
        return this.airports;
    }

}
