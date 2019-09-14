package com.hackyeah.lotflights.controller;

import com.hackyeah.lotflights.model.Airport;
import com.hackyeah.lotflights.model.AirportAvailable;
import com.hackyeah.lotflights.model.AirportPosition;
import com.hackyeah.lotflights.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightsController
{
    @Autowired
    private FlightsService service;
    
    @GetMapping("/airport-available")
    @ResponseBody
    public ResponseEntity<AirportPosition> getAirports()
    {

        AirportPosition response =service.getAirportsAvailable();
       return   ResponseEntity.ok().body(response);
    }
}
