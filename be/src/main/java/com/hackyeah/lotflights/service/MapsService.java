package com.hackyeah.lotflights.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hackyeah.lotflights.model.AirportAvailable;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
@NoArgsConstructor
public class MapsService
{
    @Value("google.api.url")
    private String url;
    @Value("google.api.research.researchRadius")
    private Integer researchRadius;
    @Value("google.api.key")
    private String apiKey;
    
    @Autowired
    private RestTemplate client;
    
    public AirportAvailable findNearAirport(final GeoJsonPoint location)
    {
        if (location != null && location.getCoordinates() != null)
        {
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
            
            final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(this.url);
            
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            uriComponentsBuilder.queryParam("key").
              queryParam("inputtype", "textquery").
              queryParam("input", "airport").
              queryParam("language", "en").
              queryParam("fields", "geometry,name").
              queryParam("locationbias", String.format("circle:25000@%s,%s", String.valueOf(location.getX()), String.valueOf(location.getY())));
            ResponseEntity<String> responseEntity = this.client.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, String.class);
            if (responseEntity != null && responseEntity.getBody() != null)
            {
                final String response = responseEntity.getBody();
                final JsonParser parser = new JsonParser();
                final JsonElement responseElement = parser.parse(response);
                responseElement.getAsJsonObject();
            }
        }
        return null;
    }
}
