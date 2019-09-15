package com.hackyeah.lotflights.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hackyeah.lotflights.model.Airport;
import com.hackyeah.lotflights.model.GeoJsonPoint;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@NoArgsConstructor
public class MapsService
{
    @Value("${google.api.url}")
    private String url;
    @Value("${google.api.research.radius}")
    private Integer researchRadius;
    @Value("${google.api.key}")
    private String apiKey;
    
    @Autowired
    private RestTemplate client;
    
    public Optional<Airport> findNearAirport(final GeoJsonPoint location)
    {
        if (location != null && location.getX() != null && location.getY()!=null)
        {
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
            
            final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(this.url);
            
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            uriComponentsBuilder.queryParam("key",this.apiKey).
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
                if(responseElement!=null)
                {
                    try
                    {
                        final JsonObject airportObject = responseElement.getAsJsonObject().getAsJsonArray("candidates").get(0).getAsJsonObject();
                        if(airportObject!=null)
                        {
                            final JsonObject airportLocationObject = airportObject.getAsJsonObject("geometry").getAsJsonObject("location");
                            return Optional.of(Airport.builder().name(airportObject.get("name").getAsString()).location(GeoJsonPoint.builder().x(airportLocationObject.get("lat").getAsDouble()).y(airportLocationObject.get("lng").getAsDouble()).build()).build());
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return Optional.empty();
    }
}
