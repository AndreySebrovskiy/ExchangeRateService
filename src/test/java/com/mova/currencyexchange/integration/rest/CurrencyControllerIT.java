package com.mova.currencyexchange.integration.rest;

import static com.mova.currencyexchange.util.TestConstants.EUR;
import static com.mova.currencyexchange.util.TestConstants.EUR_NAME;
import static com.mova.currencyexchange.util.TestConstants.USD;
import static com.mova.currencyexchange.util.TestConstants.USD_NAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.integration.IntegrationTestBase;
import com.mova.currencyexchange.model.CurrencyRequest;


class CurrencyControllerIT extends IntegrationTestBase
{
    @AfterEach
    void tearDown()
    {
        exchangeRateRepository.deleteAll();
        currencyRepository.deleteAll();
    }

    @Test
    void shouldCreateNewCurrency() throws Exception
    {
        // given
        final var request = new CurrencyRequest();
        request.setCode(USD);
        request.setName(USD_NAME);

        // when
        mockMvc.perform(put(API_PATH + "/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "code": "USD",
                        "name": "United States Dollar"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(USD)))
            .andExpect(jsonPath("$.name", is(USD_NAME)));

        // then
        final var currency = currencyRepository.findByCode(USD);
        assertTrue(currency.isPresent());
        assertEquals(USD_NAME, currency.get().getName());
    }

    @Test
    void shouldReturnAllCurrencies() throws Exception
    {
        // given
        final var currency1 = Currency.builder().code(USD).name(USD_NAME).build();
        final var currency2 = Currency.builder().code(EUR).name(EUR_NAME).build();
        currencyRepository.saveAll(List.of(currency1, currency2));

        // when - then
        mockMvc.perform(get(API_PATH + "/currencies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].code", is(USD)))
            .andExpect(jsonPath("$[0].name", is(USD_NAME)))
            .andExpect(jsonPath("$[1].code", is(EUR)))
            .andExpect(jsonPath("$[1].name", is(EUR_NAME)));
    }

    @Test
    void shouldNotAllowDuplicateCurrencyCode() throws Exception
    {
        // given
        currencyRepository.save(Currency.builder().code(USD).name(USD_NAME).build());

        // when - then
        mockMvc.perform(put(API_PATH + "/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "code": "USD",
                        "name": "Duplicate Currency"
                    }
                    """))
            .andExpect(status().isBadRequest());

    }

    @Test
    void shouldFailValidationForBlankCurrencyCode() throws Exception {
        mockMvc.perform(put(API_PATH + "/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "code": "",
                        "name": "DOLLAR"
                    }
                    """))
            .andExpect(status().isBadRequest());

    }

    @Test
    void shouldFailValidationForBlankCurrencyName() throws Exception {
        mockMvc.perform(put(API_PATH + "/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "code": "USD",
                        "name": ""
                    }
                    """))
            .andExpect(status().isBadRequest());
    }
}
