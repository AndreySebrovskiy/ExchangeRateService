package com.mova.currencyexchange.service;

import static com.mova.currencyexchange.util.TestConstants.EUR;
import static com.mova.currencyexchange.util.TestConstants.EUR_NAME;
import static com.mova.currencyexchange.util.TestConstants.USD;
import static com.mova.currencyexchange.util.TestConstants.USD_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.error.EntityAlreadyExistsException;
import com.mova.currencyexchange.mapper.CurrencyMapper;
import com.mova.currencyexchange.model.CurrencyResponse;
import com.mova.currencyexchange.repository.CurrencyRepository;


class CurrencyServiceTest
{
    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyMapper currencyMapper;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewCurrencyWhenItDoesNotExist()
    {
        // given
        final var currency = Currency.builder().code(USD).name(USD_NAME).build();

        final var savedCurrency = Currency.builder().id(UUID.randomUUID()).code(USD).name(USD_NAME).build();
        final var expectedResponse = new CurrencyResponse(USD, USD_NAME);

        when(currencyRepository.findByCode(USD)).thenReturn(Optional.empty());
        when(currencyRepository.save(currency)).thenReturn(savedCurrency);
        when(currencyMapper.toResponse(savedCurrency)).thenReturn(expectedResponse);

        // when
        final var response = currencyService.create(USD, USD_NAME);

        // then
        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(currencyRepository, times(1)).findByCode(USD);
        verify(currencyRepository, times(1)).save(currency);
        verify(currencyMapper, times(1)).toResponse(savedCurrency);
    }

    @Test
    void shouldThrowExceptionWhenCurrencyAlreadyExists()
    {
        // given
        final var existingCurrency = Currency.builder().id(UUID.randomUUID()).code(USD).name(USD_NAME).build();

        when(currencyRepository.findByCode(USD)).thenReturn(Optional.of(existingCurrency));

        // when - then
        final var exception = assertThrows(EntityAlreadyExistsException.class,
            () -> currencyService.create(USD, USD_NAME));
        assertEquals("Unable to save currency. Currency USD already exists", exception.getMessage());

        verify(currencyRepository, times(1)).findByCode(USD);
        verify(currencyRepository, never()).save(any());
        verifyNoInteractions(currencyMapper);
    }

    @Test
    void shouldReturnAllCurrencies()
    {
        // given
        final var currency1 = Currency.builder().id(UUID.randomUUID()).code(USD).name(USD_NAME).build();
        final var currency2 = Currency.builder().id(UUID.randomUUID()).code(EUR).name(EUR_NAME).build();
        final var currencies = List.of(currency1, currency2);
        final var expectedResponses = List.of(
            new CurrencyResponse(USD, USD_NAME),
            new CurrencyResponse(EUR, EUR_NAME)
        );

        when(currencyRepository.findAll()).thenReturn(currencies);
        when(currencyMapper.toResponse(currencies)).thenReturn(expectedResponses);

        // when
        final var responses = currencyService.getAllCurrencies();

        // then
        assertNotNull(responses);
        assertEquals(expectedResponses.size(), responses.size());
        assertEquals(expectedResponses, responses);

        verify(currencyRepository, times(1)).findAll();
        verify(currencyMapper, times(1)).toResponse(currencies);
    }

    @Test
    void shouldReturnEmptyListWhenNoCurrenciesExist()
    {
        // given
        when(currencyRepository.findAll()).thenReturn(List.of());
        when(currencyMapper.toResponse(List.of())).thenReturn(List.of());

        // when
        final var responses = currencyService.getAllCurrencies();

        // then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());

        verify(currencyRepository, times(1)).findAll();
        verify(currencyMapper, times(1)).toResponse(List.of());
    }
}
