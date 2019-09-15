package com.hackyeah.lotflights.model;


import lombok.*;

@Data
@NoArgsConstructor
public class AirportPosition
{
    private String latitude;
    private String longitude;
    private String code;
}
