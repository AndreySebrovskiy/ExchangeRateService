package com.mova.currencyexchange.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto
{
    private String baseCurrencyCode;
    private Map<String, BigDecimal> rates;
    private OffsetDateTime dateTime;
}
