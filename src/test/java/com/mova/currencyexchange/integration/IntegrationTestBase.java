package com.mova.currencyexchange.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.mova.currencyexchange.ExchangeRateServiceApplication;


@AutoConfigureMockMvc
@SpringBootTest(
    properties = {"spring.main.allow-bean-definition-overriding=true"},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(
    initializers = {ITInitializer.class},
    classes = {
        ExchangeRateServiceApplication.class,
    },
    loader = SpringBootContextLoader.class
)
public abstract class IntegrationTestBase {


}
