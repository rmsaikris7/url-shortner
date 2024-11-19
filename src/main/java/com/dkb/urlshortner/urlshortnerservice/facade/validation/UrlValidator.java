package com.dkb.urlshortner.urlshortnerservice.facade.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlValidator implements ConstraintValidator<URL, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            new URI(value).toURL();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
