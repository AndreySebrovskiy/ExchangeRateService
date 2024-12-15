package com.mova.currencyexchange.integration;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
class ITInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("currencydb")
        .withUsername("postgres")
        .withPassword("postgres");


    @Override
    public void initialize(@NotNull final ConfigurableApplicationContext configurableApplicationContext)
    {
        POSTGRES_CONTAINER.start();

        TestPropertyValues.of(
            "datasource.url=" + POSTGRES_CONTAINER.getJdbcUrl(),
            "datasource.password=" + POSTGRES_CONTAINER.getUsername(),
            "datasource.username=" + POSTGRES_CONTAINER.getPassword()
        ).applyTo(configurableApplicationContext);
    }
}
