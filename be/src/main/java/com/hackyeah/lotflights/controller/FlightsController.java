package com.hackyeah.lotflights.controller;

import com.hackyeah.lotflights.model.Airport;
import com.hackyeah.lotflights.model.GeoJsonPoint;
import com.hackyeah.lotflights.service.FlightsService;
import com.hackyeah.lotflights.service.GeoService;
import com.hackyeah.lotflights.service.IATAService;
import com.hackyeah.lotflights.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class FlightsController
{
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private MapsService mapsService;
    @Autowired
    private GeoService geoService;
    @Autowired
    private IATAService iataService;
    
    @GetMapping("/airport-available")
    @ResponseBody
    public ResponseEntity<List<Airport>> getAirports()
    {
        List<Airport> response = flightsService.getAvailableAirports();
        return response != null && !response.isEmpty() ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/nearest-departure", params = "ip")
    @ResponseBody
    public ResponseEntity<Airport> nearestDepartureAirport(final String ip)
    {
        final GeoJsonPoint ipLocation = this.geoService.getIpLocation(ip);
        if (ipLocation != null)
        {
            return nearestDepartureAirport(ipLocation.getX(), ipLocation.getY());
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping(value = "/nearest-departure")
    @ResponseBody
    public ResponseEntity<Airport> nearestDepartureAirport(final Double lat, final Double lon)
    {
        final Optional<Airport> nearestAirport = this.mapsService.findNearAirport(GeoJsonPoint.builder().x(lat).y(lon).build());
        if(nearestAirport.isPresent())
        {
            final Airport airport = nearestAirport.get();
            airport.setIata(this.iataService.getIataFromGeo(airport.getLocation()));
            return ResponseEntity.ok(airport);
        }
        return ResponseEntity.notFound().build();
    }
}
