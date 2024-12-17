package com.mova.currencyexchange.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Validated
@Service
public class ExchangeRateCacheImpl implements ExchangeRateCache<String, ExchangeRateModel>
{
    private static final ConcurrentHashMap<String, ExchangeRateModel> EXCHANGE_RATE_MODEL_CACHE = new ConcurrentHashMap<>();

    @Override
    public void put(String key, @Valid @NotNull ExchangeRateModel value)
    {
        EXCHANGE_RATE_MODEL_CACHE.put(key, value);
    }

    @Override
    public ExchangeRateModel get(final String key)
    {
        return EXCHANGE_RATE_MODEL_CACHE.get(key);
    }
}
