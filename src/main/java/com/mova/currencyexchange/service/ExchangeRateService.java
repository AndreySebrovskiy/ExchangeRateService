package com.mova.currencyexchange.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mova.currencyexchange.cache.ExchangeRateCache;
import com.mova.currencyexchange.cache.ExchangeRateModel;
import com.mova.currencyexchange.entity.ExchangeRate;
import com.mova.currencyexchange.repository.CurrencyRepository;
import com.mova.currencyexchange.repository.ExchangeRateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService
{
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateCache<String, ExchangeRateModel> exchangeRateCache;
    private final CurrencyExchangeManagementService exchangeManagementService;

    public ExchangeRateModel getExchangeRate(final String baseCurrencyCode)
    {
        // not clear logic what after addNew currency -> fetch immediately?
        final var rateModel = exchangeRateCache.get(baseCurrencyCode);
        if (Objects.nonNull(rateModel))
        {
            return rateModel;
        }
        else if (currencyRepository.findByCode(baseCurrencyCode).isEmpty())
        {
            throw new RuntimeException("Currency " + baseCurrencyCode + "is not supported");
        }
        else
        {
            exchangeManagementService.fetchAndStoreExchangeRates(baseCurrencyCode);

            return exchangeRateCache.get(baseCurrencyCode);
        }
    }

    public void createExchangeRate(final String baseCurrencyCode, final ExchangeRateModel exchangeRateModel)
    {
        final var baseCurrency = currencyRepository.findByCode(baseCurrencyCode).get();
        final var exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setRates(exchangeRateModel.getRates());
        exchangeRate.setDateTime(exchangeRateModel.getDateTime());
        exchangeRateRepository.save(exchangeRate);
    }

}
