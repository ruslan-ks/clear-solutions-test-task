package com.rkostiuk.cstask.dto.response;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public record ApiValidationError(String field, Object rejectedValue, UIMessage uiMessage) implements Serializable {

    public static List<ApiValidationError> listOf(Errors errors) {
        return listOf(errors.getFieldErrors());
    }

    public static List<ApiValidationError> listOf(List<FieldError> errors) {
        return errors.stream()
                .map(ApiValidationError::of)
                .toList();
    }

    public static <T> List<ApiValidationError> listOf(Set<ConstraintViolation<T>> violations) {
        return violations.stream()
                .map(ApiValidationError::of)
                .toList();
    }

    public static ApiValidationError of(FieldError error) {
        var uiMessage = new UIMessage(error.getCode(), error.getDefaultMessage());
        return new ApiValidationError(error.getField(), error.getRejectedValue(), uiMessage);
    }

    public static ApiValidationError of(ConstraintViolation<?> violation) {
        String annotation = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
        String field = fieldName(violation.getPropertyPath());
        String constraint = annotation + "." + field;
        var uiMessage = new UIMessage(constraint, violation.getMessage());
        return new ApiValidationError(field, violation.getInvalidValue(), uiMessage);
    }

    private static String fieldName(Path propertyPath) {
        String field = null;
        for (Path.Node node : propertyPath) {
            field = node.getName();
        }
        return field;
    }
}
