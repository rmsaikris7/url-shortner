package com.dkb.urlshortner.urlshortnerservice.facade.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UrlValidator.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface URL {

    String message() default "Url is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
