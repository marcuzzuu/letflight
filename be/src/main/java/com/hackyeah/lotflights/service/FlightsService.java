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
public class FlightsService {

    private List<AirportAvailable> airports;


    private List<AirportAvailable> addMockAirportAvailable()
    {
        this.airports=new ArrayList<AirportAvailable>();
        this.airports.add(new AirportAvailable(new GeoJsonPoint(2.2,4.3),"Gioia Tauro"));
        return this.airports;
    }

    public List<AirportAvailable> getAirportsAvailable()
    {
        return addMockAirportAvailable();
    }

}
