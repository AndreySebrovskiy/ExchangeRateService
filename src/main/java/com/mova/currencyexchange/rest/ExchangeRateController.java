package com.mova.currencyexchange.rest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mova.currencyexchange.mapper.ExchangeRateMapper;
import com.mova.currencyexchange.model.ExchangeRateResponse;
import com.mova.currencyexchange.service.ExchangeRateService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController
{
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateMapper exchangeRateMapper;

    @GetMapping("/{baseCurrency}")
    public ExchangeRateResponse getExchangeRate(@PathVariable @Valid @NotBlank final String baseCurrency)
    {
        return exchangeRateMapper.fromModel(exchangeRateService.getExchangeRate(baseCurrency), baseCurrency);
    }
}
