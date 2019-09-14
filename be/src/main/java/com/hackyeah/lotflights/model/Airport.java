package com.hackyeah.lotflights.model;

public class Airport {


    private String airportName;

    private double lantilatitude;

    private double longitude;

    public Airport(double lantilatitude, double longitude) {
        this.lantilatitude = lantilatitude;
        this.longitude = longitude;
    }
}
