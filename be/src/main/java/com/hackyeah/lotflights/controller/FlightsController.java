package com.hackyeah.lotflights.controller;

import com.hackyeah.lotflights.model.Airport;
import com.hackyeah.lotflights.model.GeoJsonPoint;
import com.hackyeah.lotflights.service.FlightsService;
import com.hackyeah.lotflights.service.GeoService;
import com.hackyeah.lotflights.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightsController
{
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private MapsService mapsService;
    @Autowired
    private GeoService geoService;
    
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
            return nearestDepartureAirport(ipLocation);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping(value = "/nearest-departure", params = "location")
    @ResponseBody
    public ResponseEntity<Airport> nearestDepartureAirport(@RequestParam final GeoJsonPoint location)
    {
        final Airport nearestAirport = this.mapsService.findNearAirport(location);
        return nearestAirport != null ? ResponseEntity.ok(nearestAirport) : ResponseEntity.ok(this.flightsService.getDefaultAirport());
    }
}
