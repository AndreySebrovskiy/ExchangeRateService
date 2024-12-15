package com.mova.currencyexchange.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.RequiredArgsConstructor;


@AutoConfigureBefore(JpaRepositoriesAutoConfiguration.class)
@Configuration
@EnableJpaRepositories(basePackages = "com.mova.currencyexchange")
@RequiredArgsConstructor
public class JpaConfig
{
}
