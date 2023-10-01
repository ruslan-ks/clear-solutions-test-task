package com.rkostiuk.cstask.exception;

import com.rkostiuk.cstask.dto.response.ApiValidationError;
import jakarta.validation.ConstraintViolation;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Set;

public class CustomValidationException extends RuntimeException {
    private final List<ApiValidationError> errors;

    public CustomValidationException(Errors errors) {
        this.errors = ApiValidationError.listOf(errors);
    }

    public <T> CustomValidationException(Set<ConstraintViolation<T>> violations) {
        this.errors = ApiValidationError.listOf(violations);
    }

    @Override
    public String getMessage() {
        return "Validation failed: " + errors;
    }

    public List<ApiValidationError> getErrors() {
        return errors;
    }
}
