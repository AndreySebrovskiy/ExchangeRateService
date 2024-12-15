package com.mova.currencyexchange.cache;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * An in-memory cache value model
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateModel
{
    private Map<String, BigDecimal> rates;
    private OffsetDateTime dateTime;
}
