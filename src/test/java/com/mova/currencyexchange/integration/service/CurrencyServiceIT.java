package com.mova.currencyexchange.integration.service;

import static com.mova.currencyexchange.util.TestConstants.EUR;
import static com.mova.currencyexchange.util.TestConstants.EUR_NAME;
import static com.mova.currencyexchange.util.TestConstants.USD;
import static com.mova.currencyexchange.util.TestConstants.USD_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.error.EntityAlreadyExistsException;
import com.mova.currencyexchange.integration.IntegrationTestBase;


class CurrencyServiceIT extends IntegrationTestBase
{
    @AfterEach
    void tearDown()
    {
        exchangeRateRepository.deleteAll();
        currencyRepository.deleteAll();
    }

    @Test
    void shouldCreateNewCurrencyWhenNotExists()
    {
        // given
        final var code = USD;
        final var name = USD_NAME;

        final var response = currencyService.create(code, name);

        assertNotNull(response);
        assertEquals(code, response.getCode());
        assertEquals(name, response.getName());

        final var savedCurrency = currencyRepository.findByCode(code);
        assertTrue(savedCurrency.isPresent());
        assertEquals(name, savedCurrency.get().getName());
    }

    @Test
    void shouldThrowExceptionWhenCurrencyAlreadyExists()
    {
        // given
        final var code = USD;
        final var name = USD_NAME;
        currencyRepository.save(Currency.builder().code(code).name(name).build());

        //when
        final var exception = assertThrows(EntityAlreadyExistsException.class,
            () -> currencyService.create(code, name));

        // then
        assertEquals("Unable to save currency. Currency USD already exists", exception.getMessage());
    }

    @Test
    void shouldReturnAllCurrencies()
    {
        // given
        final var currency1 = Currency.builder().code(USD).name(USD_NAME).build();
        final var currency2 = Currency.builder().code(EUR).name(EUR_NAME).build();
        currencyRepository.saveAll(List.of(currency1, currency2));

        //when
        final var responses = currencyService.getAllCurrencies();

        // then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(c -> c.getCode().equals(USD) && c.getName().equals(USD_NAME)));
        assertTrue(responses.stream().anyMatch(c -> c.getCode().equals(EUR) && c.getName().equals(EUR_NAME)));
    }

    @Test
    void shouldReturnEmptyListWhenNoCurrenciesExist()
    {
        //when
        final var responses = currencyService.getAllCurrencies();

        // then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }
}
