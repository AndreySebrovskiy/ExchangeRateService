package com.mova.currencyexchange.mapper;

import static com.mova.currencyexchange.util.TestConstants.EUR;
import static com.mova.currencyexchange.util.TestConstants.EUR_NAME;
import static com.mova.currencyexchange.util.TestConstants.USD;
import static com.mova.currencyexchange.util.TestConstants.USD_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.mova.currencyexchange.entity.Currency;

class CurrencyMapperTest
{
    private final CurrencyMapper mapper = Mappers.getMapper(CurrencyMapper.class);

    @Test
    void shouldMapCurrencyToCurrencyResponse() {
        // given
        final var currency = Currency.builder()
            .id(UUID.randomUUID())
            .code(USD)
            .name(USD_NAME)
            .build();

        // when
        final var response = mapper.toResponse(currency);

        // then
        assertNotNull(response);
        assertEquals(currency.getCode(), response.getCode());
        assertEquals(currency.getName(), response.getName());
    }

    @Test
    void shouldMapCurrencyListToCurrencyResponseList() {
        // given
        final var currency1 = Currency.builder()
            .id(UUID.randomUUID())
            .code(USD)
            .name(USD_NAME)
            .build();
        final var currency2 = Currency.builder()
            .id(UUID.randomUUID())
            .code(EUR)
            .name(EUR_NAME)
            .build();
        final var currencies = List.of(currency1, currency2);

        // when
        final var responses = mapper.toResponse(currencies);

        // then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        final var response1 = responses.get(0);
        assertEquals(currency1.getCode(), response1.getCode());
        assertEquals(currency1.getName(), response1.getName());

        final var response2 = responses.get(1);
        assertEquals(currency2.getCode(), response2.getCode());
        assertEquals(currency2.getName(), response2.getName());
    }

    @Test
    void shouldReturnNullWhenInputCurrencyIsNull() {
        // when
        final var response = mapper.toResponse((Currency) null);

        // then
        assertNull(response);
    }

    @Test
    void shouldReturnEmptyListWhenInputCurrencyListIsEmpty() {
        // when
        final var responses = mapper.toResponse(List.of());

        // then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }
}
