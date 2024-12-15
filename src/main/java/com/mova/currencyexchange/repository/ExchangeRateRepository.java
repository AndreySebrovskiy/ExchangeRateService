package com.mova.currencyexchange.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mova.currencyexchange.entity.ExchangeRate;


public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID>
{
}
