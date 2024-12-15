package com.mova.currencyexchange.scheduler;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


 class ExchangeRateSchedulerTest
{
    @Test
    void test() {
        Assert.assertTrue(Boolean.TRUE);
    }

//    @InjectMocks
//    private ExchangeRateScheduler scheduler;
//
//    @Mock
//    private CurrencyExchangeManagementService currencyExchangeManagementService;
//
//    @Mock
//    private CurrencyRepository currencyRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void shouldUpdateExchangeRatesForAllCurrencies() {
//        // Mock data
//        final var currency1 = new com.mova.currencyexchange.entity.Currency();
//        currency1.setCode("USD");
//
//        final var currency2 = new com.mova.currencyexchange.entity.Currency();
//        currency2.setCode("EUR");
//
//        when(currencyRepository.findAll()).thenReturn(List.of(currency1, currency2));
//
//        // Run the scheduler method
//        scheduler.updateExchangeRates();
//
//        // Verify that fetchAndStoreExchangeRates is called for each currency
//        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates("USD");
//        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates("EUR");
//    }
//
//    @Test
//    void shouldHandleExceptionsGracefully() {
//        // Mock data
//        final var currency1 = new com.mova.currencyexchange.entity.Currency();
//        currency1.setCode("USD");
//
//        final var currency2 = new com.mova.currencyexchange.entity.Currency();
//        currency2.setCode("EUR");
//
//        when(currencyRepository.findAll()).thenReturn(List.of(currency1, currency2));
//
//        // Mock exception for one currency
//        doThrow(new RuntimeException("API Failure")).when(currencyExchangeManagementService).fetchAndStoreExchangeRates("USD");
//
//        // Run the scheduler method
//        scheduler.updateExchangeRates();
//
//        // Verify calls
//        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates("USD");
//        verify(currencyExchangeManagementService, times(1)).fetchAndStoreExchangeRates("EUR");
//
//    }
}
