package com.mova.currencyexchange.mapper;

import static com.mova.currencyexchange.util.TestConstants.EUR;
import static com.mova.currencyexchange.util.TestConstants.USD;
import static com.mova.currencyexchange.util.TestConstants.USD_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.mova.currencyexchange.cache.ExchangeRateModel;
import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.entity.ExchangeRate;


class ExchangeRateMapperTest
{
    private final ExchangeRateMapper mapper = Mappers.getMapper(ExchangeRateMapper.class);

    @Test
    void shouldMapExchangeRateToExchangeRateResponse()
    {
        // given
        final var baseCurrency = Currency.builder()
            .id(UUID.randomUUID())
            .code(USD)
            .name(USD_NAME)
            .build();

        final var exchangeRate = ExchangeRate.builder()
            .id(UUID.randomUUID())
            .baseCurrency(baseCurrency)
            .rates(Map.of(EUR, BigDecimal.valueOf(0.85)))
            .dateTime(OffsetDateTime.now())
            .build();

        // when
        final var response = mapper.fromEntity(exchangeRate);

        // then
        assertNotNull(response);
        assertEquals(USD, response.getBaseCurrency());
        assertTrue(response.getRates().containsKey(EUR));
        assertEquals(BigDecimal.valueOf(0.85), response.getRates().get(EUR));
        assertEquals(exchangeRate.getDateTime(), response.getDateTime());
    }

    @Test
    void shouldMapExchangeRateModelToExchangeRateResponse()
    {
        // given
        final var exchangeRateModel = new ExchangeRateModel(
            Map.of(EUR, BigDecimal.valueOf(0.85)),
            OffsetDateTime.now()
        );

        // when
        final var response = mapper.fromModel(exchangeRateModel, USD);

        // then
        assertNotNull(response);
        assertEquals(USD, response.getBaseCurrency());
        assertTrue(response.getRates().containsKey(EUR));
        assertEquals(BigDecimal.valueOf(0.85), response.getRates().get(EUR));
        assertEquals(exchangeRateModel.getDateTime(), response.getDateTime());
    }

    @Test
    void shouldHandleNullExchangeRate()
    {
        // when
        final var response = mapper.fromEntity((ExchangeRate) null);

        // then
        assertNull(response);
    }

    @Test
    void shouldHandleNullExchangeRateModel()
    {
        // when
        final var response = mapper.fromModel(null, USD);

        // then
        assertNull(response.getRates());
        assertNotNull(response.getBaseCurrency());
    }
}
