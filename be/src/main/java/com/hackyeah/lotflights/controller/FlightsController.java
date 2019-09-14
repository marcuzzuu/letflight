package com.hackyeah.lotflights.controller;

import com.hackyeah.lotflights.model.AirportAvailable;
import com.hackyeah.lotflights.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightsController
{
    @Autowired
    private FlightsService service;
    
    @GetMapping("/airport-available")
    @ResponseBody
    public AirportAvailable getAirports()
    {
        return null;
    }
}
