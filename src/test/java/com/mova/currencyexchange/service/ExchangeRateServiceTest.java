package com.mova.currencyexchange.service;

import static com.mova.currencyexchange.util.TestConstants.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mova.currencyexchange.cache.ExchangeRateCache;
import com.mova.currencyexchange.cache.ExchangeRateModel;
import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.entity.ExchangeRate;
import com.mova.currencyexchange.repository.CurrencyRepository;
import com.mova.currencyexchange.repository.ExchangeRateRepository;


class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ExchangeRateCache<String, ExchangeRateModel> exchangeRateCache;

    @Mock
    private CurrencyExchangeManagementService exchangeManagementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnExchangeRateFromCache() {
        // given
        final var baseCurrency = USD;
        final var cachedRate = new ExchangeRateModel(
            Map.of("EUR", BigDecimal.valueOf(0.85)), OffsetDateTime.now());
        when(exchangeRateCache.get(baseCurrency)).thenReturn(cachedRate);

        // when
        final var result = exchangeRateService.getExchangeRate(baseCurrency);

        // then
        assertEquals(cachedRate, result);
        verify(exchangeRateCache, times(1)).get(baseCurrency);
        verifyNoInteractions(currencyRepository);
        verifyNoInteractions(exchangeManagementService);
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsNotSupported() {
        // given
        final var baseCurrencyCode = USD;
        final var baseCurrency = new Currency(UUID.randomUUID(), baseCurrencyCode, "US Dollar");

        final var exchangeRateModel = new ExchangeRateModel(
            Map.of("EUR", BigDecimal.valueOf(0.85)), OffsetDateTime.now());

        when(currencyRepository.findByCode(baseCurrencyCode)).thenReturn(Optional.of(baseCurrency));

        // when
        exchangeRateService.createExchangeRate(baseCurrencyCode, exchangeRateModel);

        // then
        verify(exchangeRateRepository, times(1)).save(any(ExchangeRate.class));
        verify(currencyRepository, times(1)).findByCode(baseCurrencyCode);
    }

    @Test
    void shouldFetchExchangeRateWhenNotInCacheButSupported() {
        // given
        final var baseCurrency = USD;
        final var fetchedRate = new ExchangeRateModel(
            Map.of("EUR", BigDecimal.valueOf(0.85)), OffsetDateTime.now());

        final var baseCurrencyEntity = new Currency(UUID.randomUUID(), baseCurrency, "US Dollar");

        // Mock cache to return null initially (not in cache)
        when(exchangeRateCache.get(baseCurrency)).thenReturn(null);

        // Mock repository to indicate the currency is supported
        when(currencyRepository.findByCode(baseCurrency)).thenReturn(Optional.of(baseCurrencyEntity));

        // Mock cache to return the fetched rate after the fetch
        doAnswer(invocation -> {
            // Simulate that the rate is added to the cache
            when(exchangeRateCache.get(baseCurrency)).thenReturn(fetchedRate);
            return null;
        }).when(exchangeManagementService).fetchAndStoreExchangeRates(baseCurrency);

        // when
        final var result = exchangeRateService.getExchangeRate(baseCurrency);

        // then
        assertEquals(fetchedRate, result);
        verify(exchangeManagementService, times(1)).fetchAndStoreExchangeRates(baseCurrency);
        verify(exchangeRateCache, times(2)).get(baseCurrency); // Before and after fetch
    }
}
