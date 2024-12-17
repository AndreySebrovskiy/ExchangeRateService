package com.mova.currencyexchange.cache;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


/**
 * ExchangeRate Cache Interface
 * @param <K> base currency
 * @param <V> exchange rate values
 */
public interface ExchangeRateCache<K, V>
{
    void put(K key, @Valid @NotNull V value);

    V get(K key);
}
