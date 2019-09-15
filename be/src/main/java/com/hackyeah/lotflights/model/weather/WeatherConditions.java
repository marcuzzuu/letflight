package com.hackyeah.lotflights.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherConditions
{
    private String summary;
    private String icon;
    private Double actualTemp;
    private Double minTemp;
    private Double maxTemp;
}
