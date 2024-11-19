package com.dkb.urlshortner.urlshortnerservice.exceptionhandling;

import com.dkb.urlshortner.urlshortnerservice.types.transport.ErrorTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShortUrlExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorTO> handleBusinessExceptions(BusinessException ex) {
        return new ResponseEntity<>(new ErrorTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<ErrorTO> handleNotFoundExceptions(BusinessException ex) {
        return new ResponseEntity<>(new ErrorTO(ex.getMessage()), ex.getStatusCode());
    }
}
