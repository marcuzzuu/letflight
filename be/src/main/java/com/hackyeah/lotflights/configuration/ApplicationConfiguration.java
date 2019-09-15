package com.hackyeah.lotflights.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
public class ApplicationConfiguration
{
    public static final String TOKEN_AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImQwNmYzOWNlY2JlNmE1MWZiZTg4MDM4YzhkZDMwYjgyYjk2ODQ2ODEiLCJib29rZXJwcm94eUlkIjoiVEZGTlpFWks4OFc0IiwiYm9va2VycHJveHlVdG0iOiJnaXRodWIiLCJpYXQiOjE1Njg0ODcyMjYsImV4cCI6MTU2ODQ4ODQyNn0.3GI7Nf-kQuoFYGK8CN6aNUCvhwCmhcJEmmlwfVyPx2";
    public static final String X_APIX_KEY = "9YFNNKS31u9gCFKPetPWdAAjEXnED0B3K18AeYgg";
    
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
    
    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }
    
}
