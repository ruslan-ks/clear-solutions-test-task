package com.rkostiuk.cstask.controller.advice;

import com.rkostiuk.cstask.dto.response.FieldValidationError;
import com.rkostiuk.cstask.dto.response.UIMessage;
import com.rkostiuk.cstask.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldValidationError> fieldValidationErrors = buildFieldValidationErrors(ex);
        return ApiErrorResponse.validationError(fieldValidationErrors);
    }

    private List<FieldValidationError> buildFieldValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(this::toFieldValidationError)
                .toList();
    }

    private FieldValidationError toFieldValidationError(FieldError error) {
        var uiMessage = new UIMessage(error.getCode(), error.getDefaultMessage());
        return new FieldValidationError(error.getField(), error.getRejectedValue(), uiMessage);
    }
}
