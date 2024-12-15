package com.mova.currencyexchange.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mova.currencyexchange.cache.ExchangeRateCache;
import com.mova.currencyexchange.repository.ExchangeRateRepository;


class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private ExchangeRateCache exchangeRateCacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void shouldGetExchangeRatesFromCache() {
//        String baseCurrency = "USD";
//        ExchangeRateModel model = new ExchangeRateModel(Map.of("EUR", BigDecimal.valueOf(1.2)), OffsetDateTime.now());
//        when(exchangeRateCacheService.get(baseCurrency)).thenReturn(model);
//
//        ExchangeRateModel result = exchangeRateService.fetchAndStoreExchangeRates(baseCurrency);
//
//        assertThat(result).isEqualTo(model);
//        verify(exchangeRateCacheService, times(1)).get(baseCurrency);
//    }
}
