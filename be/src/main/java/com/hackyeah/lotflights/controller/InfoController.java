package com.hackyeah.lotflights.controller;

import com.hackyeah.lotflights.model.GeoJsonPoint;
import com.hackyeah.lotflights.model.Suggestion;
import com.hackyeah.lotflights.model.weather.WeatherConditions;
import com.hackyeah.lotflights.service.FlightsService;
import com.hackyeah.lotflights.service.SuggestionService;
import com.hackyeah.lotflights.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/info")
public class InfoController
{
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private FlightsService flightsService;
    
    @GetMapping(value = "/weather")
    public ResponseEntity<WeatherConditions> getWeatherAtLocation(final Double lat, final Double lon)
    {
        final WeatherConditions weatherConditions = this.weatherService.weatherAtLocation(GeoJsonPoint.builder().x(lat).y(lon).build());
        return weatherConditions != null ? ResponseEntity.ok(weatherConditions) : ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/weather-at-served-airports")
    public ResponseEntity<Map<GeoJsonPoint, WeatherConditions>> getWeatherAtServedAirports()
    {
        final Map<GeoJsonPoint, WeatherConditions> weatherConditions = this.weatherService.weatherAtServedAirports(this.flightsService.getAvailableAirports());
        return weatherConditions != null ? ResponseEntity.ok(weatherConditions) : ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/suggestions")
    public ResponseEntity<List<Suggestion>> suggestionByAirport(final String iata)
    {
        if (!StringUtils.isEmpty(iata))
        {
            final List<Suggestion> suggestions = this.suggestionService.getSuggestionsBasedOnIata(iata);
            return suggestions != null && !suggestions.isEmpty() ? ResponseEntity.ok(suggestions) : ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
