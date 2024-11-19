package com.dkb.urlshortner.urlshortnerservice.exceptionhandling;

public class ShortUrlFailedValidationException extends BusinessException {

    private static final String MESSAGE = "Validation failed. Please check parameters";

    public ShortUrlFailedValidationException() {
        super(MESSAGE);
    }
}
