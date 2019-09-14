package com.hackyeah.lotflights.service;

import com.google.gson.JsonParser;
import com.hackyeah.lotflights.model.GeoJsonPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class IATAService
{
    @Value("${iatageo.api.url}")
    private String apiUrl;
    
    @Autowired
    private RestTemplate client;
    
    public String getIataFromGeo(final GeoJsonPoint geoJsonPoint)
    {
        if (geoJsonPoint != null)
        {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(this.apiUrl);
            uriComponentsBuilder.pathSegment("getCode", geoJsonPoint.getX().toString(), geoJsonPoint.getY().toString());
            final ResponseEntity<String> responseEntity = this.client.getForEntity(uriComponentsBuilder.toUriString(), String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK && !StringUtils.isEmpty(responseEntity.getBody()))
            {
                final JsonParser parser = new JsonParser();
                try
                {
                    return parser.parse(responseEntity.getBody()).getAsJsonObject().get("IATA").getAsString();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public String getGeoFromIata(final String iata)
    {
        return null;
    }
}
