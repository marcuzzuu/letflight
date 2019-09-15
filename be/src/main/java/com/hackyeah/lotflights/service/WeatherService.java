package com.hackyeah.lotflights.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hackyeah.lotflights.model.Airport;
import com.hackyeah.lotflights.model.GeoJsonPoint;
import com.hackyeah.lotflights.model.weather.WeatherConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherService
{
    @Value("${darksky.api.url}")
    private String apiUrl;
    @Value("${darksky.api.key}")
    private String apiKey;
    
    @Autowired
    private RestTemplate client;
    
    @Cacheable("weathers-at-airports")
    public Map<GeoJsonPoint, WeatherConditions> weatherAtServedAirports(final List<Airport> servedAirports)
    {
        final Map<GeoJsonPoint, WeatherConditions> weatherConditions = new HashMap<>(servedAirports.size());
        return servedAirports.parallelStream().collect(Collectors.toMap(Airport::getLocation, x->weatherAtLocation(x.getLocation())));
    }
    
    public WeatherConditions weatherAtLocation(final GeoJsonPoint location)
    {
        if(location!=null && location.getX()!=null && location.getY()!=null)
        {
            final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl).pathSegment(apiKey, String.format("%s,%s",location.getX().toString(), location.getY().toString()));
            uriComponentsBuilder.queryParam("exclude", "['minutely','hourly','flags','alerts']").queryParam("units","si");
            final String response = this.client.getForObject(uriComponentsBuilder.toUriString(), String.class);
            if(!StringUtils.isEmpty(response))
            {
                final JsonParser parser = new JsonParser();
                final JsonObject object = parser.parse(response).getAsJsonObject();
                final WeatherConditions weatherConditions = new WeatherConditions();
                weatherConditions.setActualTemp(object.get("currently").getAsJsonObject().get("temperature").getAsDouble());
                JsonObject dailyConditionsObject = object.get("daily").getAsJsonObject();
                weatherConditions.setIcon(dailyConditionsObject.get("icon").getAsString());
                weatherConditions.setSummary(dailyConditionsObject.get("summary").getAsString());
                dailyConditionsObject = dailyConditionsObject.get("data").getAsJsonArray().get(0).getAsJsonObject();
                weatherConditions.setMinTemp(dailyConditionsObject.get("temperatureMin").getAsDouble());
                weatherConditions.setMaxTemp(dailyConditionsObject.get("temperatureMax").getAsDouble());
                return weatherConditions;
            }
        }
        return null;
    }
}
