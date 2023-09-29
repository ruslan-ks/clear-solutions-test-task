package com.rkostiuk.cstask.exception;

import org.springframework.validation.Errors;

public class CustomValidationException extends RuntimeException {
    private final Errors errors;

    public CustomValidationException(Errors errors) {
        super("Validation failed: " + errors.getAllErrors());
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
