package com.hackyeah.lotflights.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "suggestions")
@Data
@NoArgsConstructor
public class Suggestion
{
    @Id
    private String id;
    private String name;
    private GeoJsonPoint location;
    private List<Sight> sights;
}
