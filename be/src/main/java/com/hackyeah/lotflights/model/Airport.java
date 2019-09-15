package com.hackyeah.lotflights.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Airport
{
    private String name;
    private String iata;
    private String country;
    private GeoJsonPoint location;
}
