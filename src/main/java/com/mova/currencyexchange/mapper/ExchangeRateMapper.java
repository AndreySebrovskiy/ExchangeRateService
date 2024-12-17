package com.mova.currencyexchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mova.currencyexchange.cache.ExchangeRateModel;
import com.mova.currencyexchange.entity.ExchangeRate;
import com.mova.currencyexchange.model.ExchangeRateResponse;


@Mapper(componentModel = "spring", uses = CurrencyMapper.class)
public interface ExchangeRateMapper
{
    @Mapping(source = "baseCurrency.code", target = "baseCurrency")
    ExchangeRateResponse fromEntity(ExchangeRate exchangeRate);

    @Mapping(source = "baseCurrency", target = "baseCurrency")
    ExchangeRateResponse fromModel(ExchangeRateModel exchangeRateModel, String baseCurrency);
}
