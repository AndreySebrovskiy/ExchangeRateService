package com.mova.currencyexchange.scheduler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.repository.CurrencyRepository;
import com.mova.currencyexchange.service.CurrencyExchangeManagementService;


class ExchangeRateSchedulerTest
{
    private static final String USD = "USD";
    private static final String EUR = "EUR";
    @InjectMocks
    private ExchangeRateScheduler scheduler;

    @Mock
    private CurrencyExchangeManagementService currencyExchangeManagementService;

    @Mock
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(scheduler, "threadPoolSize", 2); // Set threadPoolSize manually
    }

    @AfterEach
    void tearDown()
    {
        reset(currencyExchangeManagementService);
        reset(currencyRepository);
    }

    @Test
    void shouldUpdateExchangeRatesForAllCurrencies() throws InterruptedException
    {

        // given
        final var currency1 = new Currency();
        currency1.setCode(USD);
        final var currency2 = new Currency();
        currency2.setCode(EUR);

        when(currencyRepository.findAll()).thenReturn(List.of(currency1, currency2));
        // Mock the behavior of fetchAndStoreExchangeRates

        final CountDownLatch latch = new CountDownLatch(2); // Number of tasks
        doAnswer(invocation -> {
            latch.countDown(); // Decrement latch when the mock is called
            return null;
        }).when(currencyExchangeManagementService).fetchAndStoreExchangeRates(anyString());

        // when
        scheduler.updateExchangeRates();
        latch.await(); // Wait for all tasks to complete

        // then
        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates(USD);
        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates(EUR);
    }

    @Test
    void shouldHandleExceptions()
    {
        // given
        final var currency1 = new Currency();
        currency1.setCode(USD);

        final var currency2 = new Currency();
        currency2.setCode(EUR);

        when(currencyRepository.findAll()).thenReturn(List.of(currency1, currency2));
        // Mock exception for one currency
        doThrow(new RuntimeException("API Failure")).when(currencyExchangeManagementService).fetchAndStoreExchangeRates(USD);

        // when
        scheduler.updateExchangeRates();

        // then
        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates(USD);
        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates(EUR);
    }
}
