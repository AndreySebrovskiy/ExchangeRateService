package com.mova.currencyexchange.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundExistsException extends RuntimeException
{
    public EntityNotFoundExistsException(String message) {
        super(message);
    }
}
