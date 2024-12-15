package com.mova.currencyexchange.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mova.currencyexchange.entity.Currency;
import com.mova.currencyexchange.model.CurrencyResponse;


@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyResponse toResponse(Currency currency);

    List<CurrencyResponse> toResponse(List<Currency> currencies);
}
