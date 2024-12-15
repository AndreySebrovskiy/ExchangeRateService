package com.mova.currencyexchange.intergation.fixer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mova.currencyexchange.cache.ExchangeRateModel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//TODO add interface APiClient
@Slf4j
@Component
@RequiredArgsConstructor
public class FixerApiClient {

    private final RestTemplate restTemplate;

    @Value("${fixer.api-key}")
    private String apiKey;
    @Value("${fixer.url}")
    private String baseUrl;

    public ExchangeRateModel fetchExchangeRates(@NonNull final String baseCurrency) {
        final var url = String.format("%s?access_key=%s", baseUrl + "/latest", apiKey);
        log.info("Starting fetching rate from: {} for code: {}", baseUrl, baseCurrency);
        final var response = restTemplate.getForObject(url, FixerResponse.class);

        if (response == null || !response.isSuccess()) {
            throw new RuntimeException("Failed to fetch exchange rates from Fixer.io");
        }

        // Convert rates to BigDecimal
        final var rates = new HashMap<String, BigDecimal>();
        response.getRates().forEach((currency, rate) -> rates.put(currency, BigDecimal.valueOf(rate)));

        final var dateTime =  OffsetDateTime.ofInstant(Instant.ofEpochMilli(response.getTimestamp()), ZoneOffset.UTC);
        log.info("Fetching rate from: {} for code: {} finished", baseUrl, baseCurrency);
        return ExchangeRateModel.builder()
            .rates(rates)
            .dateTime(dateTime)
            .build();
    }
}
