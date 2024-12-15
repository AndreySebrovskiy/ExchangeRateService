package com.mova.currencyexchange.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.mova.currencyexchange.cache.ExchangeRateCache;
import com.mova.currencyexchange.cache.ExchangeRateModel;
import com.mova.currencyexchange.intergation.fixer.FixerApiClient;
import com.mova.currencyexchange.event.CreateExchangeRateEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyExchangeManagementService
{
    private final ExchangeRateCache<String, ExchangeRateModel> exchangeRateCache;
    private final FixerApiClient fixerApiClient;
    private final ApplicationEventPublisher eventPublisher;

    public void fetchAndStoreExchangeRates(final String baseCurrencyCode) {
        // Fetch rates from Fixer.io
        final var exchangeRateModel = fixerApiClient.fetchExchangeRates(baseCurrencyCode);

        exchangeRateCache.put(baseCurrencyCode, exchangeRateModel);
        eventPublisher.publishEvent(CreateExchangeRateEvent.builder()
            .exchangeRateModel(exchangeRateCache.get(baseCurrencyCode)).baseCurrencyCode(baseCurrencyCode).build());

    }
}
