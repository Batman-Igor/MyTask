package com.price.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscriber {

    private UUID uuid;
    private String name;
    private String surname;
    private Map<String, Integer> ccyPair;

}
