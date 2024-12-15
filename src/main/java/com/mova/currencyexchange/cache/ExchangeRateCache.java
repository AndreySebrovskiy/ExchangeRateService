package com.mova.currencyexchange.cache;

/**
 * ExchangeRate Cache Interface
 * @param <K> base currency
 * @param <V> exchange rate values
 */
public interface ExchangeRateCache<K, V>
{
    void put(K key, V value);

    V get(K key);
}
