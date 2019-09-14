package com.hackyeah.lotflights.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackyeah.lotflights.configuration.ApplicationConfiguration;
import com.hackyeah.lotflights.model.Airport;
import com.hackyeah.lotflights.model.AirportAvailable;
import com.hackyeah.lotflights.model.AirportPosition;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Service
public class FlightsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public AirportPosition  getAirportsAvailable()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", ApplicationConfiguration.X_APIX_KEY);
        headers.set("Authorization",ApplicationConfiguration.TOKEN_AUTHORIZATION);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<List> respEntity= null;
    try {
      respEntity = restTemplate.exchange("https://api.lot.com/flights-dev/v2/common/airports/get", HttpMethod.GET, entity, List.class);
    }
    catch(Exception e)
    {
      e.printStackTrace();

    }

       List<Airport> airports =(List<Airport>) respEntity.getBody().stream().map(x->objectMapper.convertValue(x,Airport.class)).collect(Collectors.toList());

        ResponseEntity<AirportPosition> airportPosition;

       return this.restTemplate.getForObject("http://iatageo.com/getLatLng/"+airports.get(0).getAirportAvailable().getCities().get(0).getIata(), AirportPosition.class);

    }

}
