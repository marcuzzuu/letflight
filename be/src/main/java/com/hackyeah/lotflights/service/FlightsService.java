package com.hackyeah.lotflights.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackyeah.lotflights.configuration.ApplicationConfiguration;
import com.hackyeah.lotflights.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightsService
{
    private Airport defaultAirport;
    
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    
    public FlightsService()
    {
        this.defaultAirport = Airport.builder().name("Warsaw Chopin Airport").iata("WAW").location(GeoJsonPoint.builder().x(52.16723689999999).y(20.9678911).build()).build();
    }
    
    @SuppressWarnings("unchecked")
    public List<Airport> getAvailableAirports()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", ApplicationConfiguration.X_APIX_KEY);
        headers.set("Authorization", ApplicationConfiguration.TOKEN_AUTHORIZATION);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<List> respEntity = null;
        try
        {
            respEntity = restTemplate.exchange("https://api.lot.com/flights-dev/v2/common/airports/get", HttpMethod.GET, entity, List.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        if (respEntity != null && respEntity.getBody() != null)
        {
            final List<AirportAvailable> availableAirports = (List<AirportAvailable>) respEntity.getBody().parallelStream().map(x -> objectMapper.convertValue(x, AirportAvailable.class)).collect(Collectors.toList());
            final List<Airport> airports = new ArrayList<>();
            for (final AirportAvailable airportAvailable : availableAirports)
            {
                for (final City airportCity : airportAvailable.getCities())
                {
                    final AirportPosition airportLocation = this.restTemplate.getForObject("http://iatageo.com/getLatLng/".concat(airportCity.getIata()), AirportPosition.class);
                    final Airport airport = Airport.builder().iata(airportCity.getIata()).country(airportAvailable.getCountry()).name(airportCity.getCity()).build();
                    if (airportLocation != null)
                    {
                        airport.setLocation(GeoJsonPoint.builder().x(airportLocation.getLatitude()).y(airportLocation.getLongitude()).build());
                    }
                    airports.add(airport);
                }
            }
            return airports;
        }
        return Collections.emptyList();
    }
    
    public Airport getDefaultAirport()
    {
        return this.defaultAirport;
    }
}
