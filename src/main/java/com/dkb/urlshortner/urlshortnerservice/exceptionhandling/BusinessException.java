package com.dkb.urlshortner.urlshortnerservice.exceptionhandling;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {

    private HttpStatus statusCode;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
