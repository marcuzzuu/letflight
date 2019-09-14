package com.hackyeah.lotflights.controller;

import com.hackyeah.lotflights.model.AirportAvailable;
import com.hackyeah.lotflights.service.FlightsService;
import com.hackyeah.lotflights.service.GeoService;
import com.hackyeah.lotflights.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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
    private FlightsService service;
    @Autowired
    private MapsService mapsService;
    @Autowired
    private GeoService geoService;
    
    @GetMapping("/airport-available")
    @ResponseBody
    public ResponseEntity<List<AirportAvailable>> getAirports()
    {

        List<AirportAvailable> response =service.getAirportsAvailable();
        System.out.println(response.get(0).toString());
       return   ResponseEntity.ok().body(response);
    }
    
    @GetMapping(value = "/nearest-departure", params = "ip")
    @ResponseBody
    public ResponseEntity<AirportAvailable> nearestDepartureAirport(final String ip)
    {
        final GeoJsonPoint ipLocation = this.geoService.getIpLocation(ip);
        if(ipLocation!=null)
        {
            return nearestDepartureAirport(ipLocation);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping(value = "/nearest-departure", params = "location")
    @ResponseBody
    public ResponseEntity<AirportAvailable> nearestDepartureAirport(@RequestParam final GeoJsonPoint location)
    {
        final AirportAvailable nearestAirport = this.mapsService.findNearAirport(location);
        return nearestAirport != null ? ResponseEntity.ok(nearestAirport) : ResponseEntity.ok()
    }
}
