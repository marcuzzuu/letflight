package com.hackyeah.lotflights.controller;

import com.hackyeah.lotflights.model.Airport;
import com.hackyeah.lotflights.service.AirportAvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightsController
{
    @Autowired
    private AirportAvailableService service;
    
    @GetMapping("/airport-available")
    @ResponseBody
    public Airport getAirports()
    {
        return null;
    }
}
