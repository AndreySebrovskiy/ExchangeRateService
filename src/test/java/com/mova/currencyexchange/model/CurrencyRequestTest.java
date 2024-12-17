package com.mova.currencyexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


class CurrencyRequestTest
{
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldPassValidationForValidCurrencyRequest()
    {
        // given
        final var request = new CurrencyRequest();
        request.setCode("USD");
        request.setName("United States Dollar");

        // when
        final Set<ConstraintViolation<CurrencyRequest>> violations = validator.validate(request);

        // then
        assertTrue(violations.isEmpty(), "No validation errors should occur for a valid request");
    }

    @Test
    void shouldFailValidationForBlankCode()
    {
        // given
        final var request = new CurrencyRequest();
        request.setCode(""); // Fails both @NotBlank and @Size
        request.setName("United States Dollar");

        // when
        final Set<ConstraintViolation<CurrencyRequest>> violations = validator.validate(request);

        // then
        assertEquals(2, violations.size());

        // Validate the exact violations
        assertTrue(violations.stream().anyMatch(v ->
            v.getMessage().equals("Currency code is required") && v.getPropertyPath().toString().equals("code"))
        );

        assertTrue(violations.stream().anyMatch(v ->
            v.getMessage().equals("Currency code must be exactly 3 characters") && v.getPropertyPath().toString().equals("code"))
        );
    }

    @Test
    void shouldFailValidationForInvalidCodeLength()
    {
        // given
        final var request = new CurrencyRequest();
        request.setCode("US");
        request.setName("United States Dollar");

        // when
        final Set<ConstraintViolation<CurrencyRequest>> violations = validator.validate(request);

        // then
        assertEquals(1, violations.size());
        final ConstraintViolation<CurrencyRequest> violation = violations.iterator().next();
        assertEquals("Currency code must be exactly 3 characters", violation.getMessage());
        assertEquals("code", violation.getPropertyPath().toString());
    }

    @Test
    void shouldFailValidationForBlankName()
    {
        // given
        final var request = new CurrencyRequest();
        request.setCode("USD");
        request.setName("");

        // when
        final Set<ConstraintViolation<CurrencyRequest>> violations = validator.validate(request);

        // then
        assertEquals(1, violations.size());
        final ConstraintViolation<CurrencyRequest> violation = violations.iterator().next();
        assertEquals("Currency name is required", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    void shouldFailValidationForNullFields()
    {
        // given
        final var request = new CurrencyRequest();
        request.setCode(null);
        request.setName(null);

        // when
        final Set<ConstraintViolation<CurrencyRequest>> violations = validator.validate(request);

        // then
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Currency code is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Currency name is required")));
    }
}
