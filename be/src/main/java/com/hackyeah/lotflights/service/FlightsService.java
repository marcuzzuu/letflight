package com.hackyeah.lotflights.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hackyeah.lotflights.configuration.ApplicationConfiguration;
import com.hackyeah.lotflights.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
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
    @Cacheable("available-airports")
    public List<Airport> getAvailableAirports()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", ApplicationConfiguration.X_APIX_KEY);
        headers.set("Authorization", ApplicationConfiguration.TOKEN_AUTHORIZATION);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
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
            final JsonParser jsonParser = new JsonParser();
            for (final AirportAvailable airportAvailable : availableAirports)
            {
                for (final City airportCity : airportAvailable.getCities())
                {
                    final ResponseEntity<String> airportPosition = this.restTemplate.getForEntity(UriComponentsBuilder.fromHttpUrl("http://iatageo.com").pathSegment("getLatLng", airportCity.getIata()).toUriString(), String.class);
                    if (airportPosition != null && airportPosition.getStatusCode()==HttpStatus.OK && airportPosition.getBody()!=null && !airportPosition.getBody().contains("error"))
                    {
                        final Airport airport = Airport.builder().iata(airportCity.getIata()).country(airportAvailable.getCountry()).name(airportCity.getCity()).build();
                        final JsonObject locationObject = jsonParser.parse(airportPosition.getBody()).getAsJsonObject();
                        airport.setLocation(GeoJsonPoint.builder().x(Double.parseDouble(locationObject.get("latitude").getAsString())).y(Double.parseDouble(locationObject.get("longitude").getAsString())).build());
                        airports.add(airport);
                    }
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
