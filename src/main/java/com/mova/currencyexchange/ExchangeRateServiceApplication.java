package com.mova.currencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class ExchangeRateServiceApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(ExchangeRateServiceApplication.class, args);
    }
}
