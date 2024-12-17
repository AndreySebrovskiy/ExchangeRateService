package com.mova.currencyexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mova.currencyexchange.cache.ExchangeRateCache;
import com.mova.currencyexchange.cache.ExchangeRateCacheImpl;
import com.mova.currencyexchange.cache.ExchangeRateModel;


@Configuration
public class TestConfig
{
//    @Bean
//    @Primary
//    public ExchangeRateCache<String, ExchangeRateModel> exchangeRateCache()
//    {
//        return new ExchangeRateCacheImpl();
//    }
}
