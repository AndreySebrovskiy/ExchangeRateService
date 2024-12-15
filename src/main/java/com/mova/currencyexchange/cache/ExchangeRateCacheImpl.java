package com.mova.currencyexchange.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ExchangeRateCacheImpl implements ExchangeRateCache<String, ExchangeRateModel>
{
    private static final ConcurrentHashMap<String, ExchangeRateModel> EXCHANGE_RATE_MODEL_CACHE = new ConcurrentHashMap<>();

    @Override
    public void put(final String key, final ExchangeRateModel value)
    {
        EXCHANGE_RATE_MODEL_CACHE.put(key, value);
    }

    @Override
    public ExchangeRateModel get(final String key)
    {
        return EXCHANGE_RATE_MODEL_CACHE.get(key);
    }
}
