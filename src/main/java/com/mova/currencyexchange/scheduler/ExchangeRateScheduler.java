package com.mova.currencyexchange.scheduler;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mova.currencyexchange.repository.CurrencyRepository;
import com.mova.currencyexchange.service.CurrencyExchangeManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Scheduler for fetching exchange rates periodically.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler
{
    private final CurrencyExchangeManagementService currencyExchangeManagementService;
    private final CurrencyRepository currencyRepository;

    @Value("${scheduler.thread.pool.size}")
    private int threadPoolSize;


    /**
     * Fetches exchange rates for all currencies
     */
    @Scheduled(cron = "${scheduler.cron}")
    public void updateExchangeRates()
    {
        final var currencies = currencyRepository.findAll();
        final var executorService = Executors.newFixedThreadPool(threadPoolSize);

        try {
            currencies.forEach(currency ->
                executorService.submit(() -> {
                    try
                    {
                        currencyExchangeManagementService.fetchAndStoreExchangeRates(currency.getCode());
                    }
                    catch (Exception e)
                    {
                        log.error("Failed to fetch rates for currency: {}", currency, e);
                    }
                })
            );
        } finally {
            executorService.shutdown();
        }
    }

}
