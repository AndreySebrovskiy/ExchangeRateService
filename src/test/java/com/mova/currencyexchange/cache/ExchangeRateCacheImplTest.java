package com.mova.currencyexchange.cache;

import static com.mova.currencyexchange.util.TestConstants.EUR;
import static com.mova.currencyexchange.util.TestConstants.GBP;
import static com.mova.currencyexchange.util.TestConstants.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExchangeRateCacheImplTest
{
    private ExchangeRateCacheImpl cache;

    @BeforeEach
    void setUp() {
        cache = new ExchangeRateCacheImpl();
    }

    @Test
    void shouldStoreAndRetrieveExchangeRateModel() {
        // given
        final var key = USD;
        final var exchangeRateModel = new ExchangeRateModel(
            Map.of(EUR, BigDecimal.valueOf(0.85)),
            OffsetDateTime.now()
        );

        // when
        cache.put(key, exchangeRateModel);
        final var result = cache.get(key);

        // then
        assertNotNull(result);
        assertEquals(exchangeRateModel, result);
    }

    @Test
    void shouldReturnNullForNonExistentKey() {
        // when
        final var result = cache.get("INVALID_KEY");

        // then
        assertNull(result);
    }

    @Test
    void shouldOverwriteExistingValue() {
        // given
        final var key = USD;
        final var oldExchangeRateModel = new ExchangeRateModel(
            Map.of(EUR, BigDecimal.valueOf(0.85)),
            OffsetDateTime.now()
        );
        final var newExchangeRateModel = new ExchangeRateModel(
            Map.of(GBP, BigDecimal.valueOf(0.75)),
            OffsetDateTime.now().plusMinutes(10)
        );

        // when
        cache.put(key, oldExchangeRateModel);
        cache.put(key, newExchangeRateModel);
        final var result = cache.get(key);

        // then
        assertNotNull(result);
        assertEquals(newExchangeRateModel, result);
        assertNotEquals(oldExchangeRateModel, result);
    }

    @Test
    void shouldStoreAndRetrieveMultipleKeys() {
        // given
        final var usdModel = new ExchangeRateModel(
            Map.of(EUR, BigDecimal.valueOf(0.85)),
            OffsetDateTime.now()
        );
        final var eurModel = new ExchangeRateModel(
            Map.of(USD, BigDecimal.valueOf(1.15)),
            OffsetDateTime.now()
        );

        // when
        cache.put(USD, usdModel);
        cache.put(EUR, eurModel);
        final var usdResult = cache.get(USD);
        final var eurResult = cache.get(EUR);

        // then
        assertNotNull(usdResult);
        assertEquals(usdModel, usdResult);

        assertNotNull(eurResult);
        assertEquals(eurModel, eurResult);
    }
}
