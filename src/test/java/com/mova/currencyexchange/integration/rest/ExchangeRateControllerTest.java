package com.mova.currencyexchange.integration.rest;

import static com.mova.currencyexchange.util.TestConstants.USD;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.mova.currencyexchange.cache.ExchangeRateModel;
import com.mova.currencyexchange.integration.IntegrationTestBase;
import com.mova.currencyexchange.model.ExchangeRateResponse;


class ExchangeRateControllerTest extends IntegrationTestBase
{
    private static final String BASE_URL = API_PATH + "/exchange-rates";

    @Test
    void shouldReturnExchangeRateForValidBaseCurrency() throws Exception {
        final var baseCurrency = USD;
        final var model = new ExchangeRateModel();
        final var response = new ExchangeRateResponse(baseCurrency, null, null);

        when(exchangeRateService.getExchangeRate(baseCurrency)).thenReturn(model);
        when(exchangeRateMapper.fromModel(model, baseCurrency)).thenReturn(response);

        mockMvc.perform(get(BASE_URL + "/USD"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.baseCurrency").value("USD"));
    }

    @Test
    void shouldReturnBadRequestWhenBaseCurrencyIsBlank() throws Exception {
        mockMvc.perform(get(BASE_URL + "/ "))
            .andExpect(status().isBadRequest());
    }
}
