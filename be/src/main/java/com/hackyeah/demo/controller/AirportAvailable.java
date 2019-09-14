package com.hackyeah.demo.controller;


import com.hackyeah.demo.service.AirportAvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AirportAvailable {

    @Autowired
    private AirportAvailableService service;

        @GetMapping("/airportAvailable")
        @ResponseBody
        public AirportAvailable getAirports() {
          return null;
        }

}
