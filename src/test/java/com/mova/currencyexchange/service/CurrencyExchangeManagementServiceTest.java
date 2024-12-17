package com.mova.currencyexchange.service;

import static com.mova.currencyexchange.util.TestConstants.EUR;
import static com.mova.currencyexchange.util.TestConstants.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

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
        // given
        final var model = new ExchangeRateModel(Map.of(EUR, BigDecimal.valueOf(1.2)), OffsetDateTime.now());
        when(fixerApiClient.fetchExchangeRates(USD)).thenReturn(model);

        // when
        managementService.fetchAndStoreExchangeRates(USD);

        // then
        verify(exchangeRateCache, times(1)).put(USD, model);
        verify(eventPublisher, times(1)).publishEvent(any(CreateExchangeRateEvent.class));
    }

    @Test
    void shouldHandleApiClientException() {
        // given
        when(fixerApiClient.fetchExchangeRates(USD)).thenThrow(new RuntimeException("API Failure"));

        // when
        final var exception = assertThrows(RuntimeException.class,
            () -> managementService.fetchAndStoreExchangeRates(USD));

        // then
        assertEquals("API Failure", exception.getMessage());
        verify(exchangeRateCache, never()).put(anyString(), any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}
