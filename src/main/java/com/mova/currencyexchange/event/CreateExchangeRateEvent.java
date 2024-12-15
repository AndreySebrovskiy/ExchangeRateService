package com.mova.currencyexchange.event;

import com.mova.currencyexchange.cache.ExchangeRateModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class CreateExchangeRateEvent
{
    private String baseCurrencyCode;
    private ExchangeRateModel exchangeRateModel;
}
