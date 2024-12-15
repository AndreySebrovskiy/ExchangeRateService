package com.mova.currencyexchange.intergation.fixer;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class FixerResponse
{
    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    @JsonProperty("rates")
    private Map<String, Double> rates;
}
