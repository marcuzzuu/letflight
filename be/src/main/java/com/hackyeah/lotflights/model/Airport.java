package com.hackyeah.lotflights.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Airport {

    private AirportAvailable airportAvailable;

    public Airport()
    {

    }
    public Airport(AirportAvailable airportAvailable) {
        this.airportAvailable = airportAvailable;
    }

    public AirportAvailable getAirportAvailable() {
        return this.airportAvailable;
    }
}
