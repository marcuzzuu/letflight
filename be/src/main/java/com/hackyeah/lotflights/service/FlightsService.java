package com.hackyeah.lotflights.service;

import com.hackyeah.lotflights.model.AirportAvailable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Service
public class FlightsService
{
    private AirportAvailable defaultAirport;
    private List<AirportAvailable> airports;
    
    public FlightsService()
    {
        this.defaultAirport = AirportAvailable.builder().airportName("Warsaw Chopin Airport").iata("WAW").geoJsonPoint(new GeoJsonPoint(52.16723689999999, 20.9678911)).build();
    }
    
    private List<AirportAvailable> addMockAirportAvailable()
    {
        this.airports=new ArrayList<>();
        this.airports.add(new AirportAvailable(new GeoJsonPoint(2.2,4.3),"Gioia Tauro"));
        return this.airports;
    }

    public List<AirportAvailable> getAirportsAvailable()
    {
        return addMockAirportAvailable();
    }
    
    public AirportAvailable getDefaultAirport()
    {
        return this.defaultAirport;
    }
}
