package com.hackyeah.lotflights.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
public class GeoService
{
    @Value("${ip-registry.api.key}")
    private String apiKey;
    @Value("${ip-registry.api.url}")
    private String apiUrl;
    
    @Autowired
    private RestTemplate client;
    
    public GeoJsonPoint getIpLocation(final String ipAddress)
    {
        if (!StringUtils.isEmpty(ipAddress))
        {
            try
            {
                UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl).path(ipAddress).queryParam("key", apiKey);
                ResponseEntity<String> responseEntity = client.getForEntity(uriComponentsBuilder.toUriString(), String.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK && !StringUtils.isEmpty(responseEntity) && !StringUtils.isEmpty(responseEntity.getBody()))
                {
                    final JsonParser parser = new JsonParser();
                    final JsonElement response = parser.parse(responseEntity.getBody());
                    if (response.isJsonObject())
                    {
                        final JsonObject responseLocation = response.getAsJsonObject().getAsJsonObject("location");
                        return GeoJsonPoint.builder().x(responseLocation.get("latitude").getAsDouble()).y(responseLocation.get("longitude").getAsDouble()).build();
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
