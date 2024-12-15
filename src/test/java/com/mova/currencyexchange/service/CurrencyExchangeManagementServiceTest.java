package com.mova.currencyexchange.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import static org.mockito.Mockito.*;

import com.mova.currencyexchange.cache.ExchangeRateCache;
import com.mova.currencyexchange.cache.ExchangeRateModel;
import com.mova.currencyexchange.event.CreateExchangeRateEvent;
import com.mova.currencyexchange.intergation.fixer.FixerApiClient;


class CurrencyExchangeManagementServiceTest {

    @InjectMocks
    private CurrencyExchangeManagementService managementService;

    @Mock
    private ExchangeRateCache<String, ExchangeRateModel> exchangeRateCache;

    @Mock
    private FixerApiClient fixerApiClient;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFetchAndStoreExchangeRates() {
        // Mock data
        String baseCurrency = "USD";
        ExchangeRateModel model = new ExchangeRateModel(Map.of("EUR", BigDecimal.valueOf(1.2)), OffsetDateTime.now());
        when(fixerApiClient.fetchExchangeRates(baseCurrency)).thenReturn(model);

        // Run the method
        managementService.fetchAndStoreExchangeRates(baseCurrency);

        // Verify cache update and event publishing
        verify(exchangeRateCache, times(1)).put(baseCurrency, model);
        verify(eventPublisher, times(1)).publishEvent(any(CreateExchangeRateEvent.class));
    }

    @Test
    void shouldHandleApiClientException() {
        // Mock exception
        String baseCurrency = "USD";
        when(fixerApiClient.fetchExchangeRates(baseCurrency)).thenThrow(new RuntimeException("API Failure"));

        // Run the method
        try {
            managementService.fetchAndStoreExchangeRates(baseCurrency);
        } catch (Exception e) {
            // Ensure no exception propagates
        }

        // Verify that cache and events are not triggered
        verify(exchangeRateCache, never()).put(anyString(), any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}
