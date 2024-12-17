package com.mova.currencyexchange.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.error.EntityAlreadyExistsException;
import com.mova.currencyexchange.mapper.CurrencyMapper;
import com.mova.currencyexchange.model.CurrencyResponse;
import com.mova.currencyexchange.repository.CurrencyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public CurrencyResponse create(final String code, final String name) {
        if (currencyRepository.findByCode(code).isEmpty()) {
            return currencyMapper.toResponse(currencyRepository.save(Currency.builder().code(code).name(name).build()));
        }
        else throw new EntityAlreadyExistsException("Unable to save currency. Currency " + code + " already exists");
    }

    public List<CurrencyResponse> getAllCurrencies() {
        return currencyMapper.toResponse(currencyRepository.findAll());
    }
}
