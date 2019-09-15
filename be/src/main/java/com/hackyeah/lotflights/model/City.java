package com.hackyeah.lotflights.model;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class City {

    private String city;
    private String iata;


    public City(String city, String iata) {
        this.city = city;
        this.iata = iata;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }
}
