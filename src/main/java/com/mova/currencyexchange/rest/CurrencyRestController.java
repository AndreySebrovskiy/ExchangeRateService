package com.mova.currencyexchange.rest;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mova.currencyexchange.model.CurrencyRequest;
import com.mova.currencyexchange.model.CurrencyResponse;
import com.mova.currencyexchange.service.CurrencyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * REST Controller for managing currencies.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyRestController {

    private final CurrencyService currencyService;

    /**
     * Retrieves the list of all available currencies.
     *
     * @return a list of currency codes
     */
    @GetMapping
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    //TODO add validation CurrencyValidator
    @PutMapping
    public CurrencyResponse addCurrency(@RequestBody @Valid final CurrencyRequest request) {
        return currencyService.create(request.getCode().trim(), request.getName().trim());
    }
}
