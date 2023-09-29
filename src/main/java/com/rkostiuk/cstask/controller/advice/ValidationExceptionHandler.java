package com.rkostiuk.cstask.controller.advice;

import com.rkostiuk.cstask.dto.response.ApiValidationError;
import com.rkostiuk.cstask.dto.response.UIMessage;
import com.rkostiuk.cstask.dto.response.ApiErrorResponse;
import com.rkostiuk.cstask.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public ApiErrorResponse handleCustomValidationException(CustomValidationException ex) {
        List<FieldError> fieldErrors = ex.getErrors().getFieldErrors();
        List<ApiValidationError> apiValidationErrors = buildApiValidationErrors(fieldErrors);
        return ApiErrorResponse.validationFail(apiValidationErrors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ApiValidationError> apiValidationErrors = buildApiValidationErrors(ex.getFieldErrors());
        return ApiErrorResponse.validationFail(apiValidationErrors);
    }

    private List<ApiValidationError> buildApiValidationErrors(List<FieldError> fieldErrors) {
        return fieldErrors
                .stream()
                .map(this::toFieldValidationError)
                .toList();
    }

    private ApiValidationError toFieldValidationError(FieldError error) {
        var uiMessage = new UIMessage(error.getCode(), error.getDefaultMessage());
        return new ApiValidationError(error.getField(), error.getRejectedValue(), uiMessage);
    }
}
