package com.hackyeah.lotflights.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@NoArgsConstructor
@Data
@Builder
public class AirportAvailable
{
    private String airportName;
    private String iata;
    private GeoJsonPoint geoJsonPoint;
    
    public AirportAvailable(GeoJsonPoint geoJsonPoint, String airportName)
    {
        this.geoJsonPoint = geoJsonPoint;
        this.airportName = airportName;
    }
}
