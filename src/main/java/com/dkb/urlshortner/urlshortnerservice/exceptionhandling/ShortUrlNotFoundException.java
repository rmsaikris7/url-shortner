package com.dkb.urlshortner.urlshortnerservice.exceptionhandling;

import org.springframework.http.HttpStatus;

public class ShortUrlNotFoundException extends BusinessException {

    private static final String MESSAGE = "The short url with key %s not found.";

    public ShortUrlNotFoundException(String key) {
        super(String.format(MESSAGE, key), HttpStatus.NOT_FOUND);
    }
}
