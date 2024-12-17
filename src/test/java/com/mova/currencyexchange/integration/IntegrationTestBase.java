package com.mova.currencyexchange.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.mova.currencyexchange.ExchangeRateServiceApplication;
import com.mova.currencyexchange.config.TestConfig;
import com.mova.currencyexchange.mapper.ExchangeRateMapper;
import com.mova.currencyexchange.repository.CurrencyRepository;
import com.mova.currencyexchange.repository.ExchangeRateRepository;
import com.mova.currencyexchange.service.CurrencyExchangeManagementService;
import com.mova.currencyexchange.service.CurrencyService;
import com.mova.currencyexchange.service.ExchangeRateService;


@AutoConfigureMockMvc
@SpringBootTest(
    properties = {"spring.main.allow-bean-definition-overriding=true"},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(
    initializers = {ITInitializer.class},
    classes = {
        ExchangeRateServiceApplication.class,
        TestConfig.class
    },
    loader = SpringBootContextLoader.class
)
public abstract class IntegrationTestBase
{
    protected static final String API_PATH = "/api";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected CurrencyRepository currencyRepository;
    @Autowired
    protected ExchangeRateRepository exchangeRateRepository;

    @Autowired
    protected CurrencyService currencyService;

    @MockBean
    protected ExchangeRateService exchangeRateService;

    @MockBean
    protected ExchangeRateMapper exchangeRateMapper;

    @MockBean
    protected CurrencyExchangeManagementService exchangeManagementService;
}
