package com.mova.currencyexchange.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.mova.currencyexchange.service.ExchangeRateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventConsumer
{
    private final ExchangeRateService exchangeRateService;

    @Async
    @EventListener(CreateExchangeRateEvent.class)
    public void notifyEmailSendRequested(final CreateExchangeRateEvent event)
    {
        log.debug("received CreateExchangeRateEvent event: {} ", event);
        exchangeRateService.createExchangeRate(event.getBaseCurrencyCode(), event.getExchangeRateModel());
    }
}
