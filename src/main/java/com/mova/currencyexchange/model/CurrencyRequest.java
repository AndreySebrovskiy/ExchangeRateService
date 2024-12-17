package com.mova.currencyexchange.model;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Validated
@NoArgsConstructor
public class CurrencyRequest {

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters")
    private String code;

    @NotBlank(message = "Currency name is required")
    private String name;
}
