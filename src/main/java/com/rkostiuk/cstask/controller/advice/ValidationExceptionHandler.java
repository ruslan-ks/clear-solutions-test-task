package com.rkostiuk.cstask.controller.advice;

import com.rkostiuk.cstask.dto.response.ApiValidationError;
import com.rkostiuk.cstask.dto.response.ApiErrorResponse;
import com.rkostiuk.cstask.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
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
        return ApiErrorResponse.validationFail(ex.getErrors());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ApiValidationError> apiValidationErrors = ApiValidationError.listOf(ex.getFieldErrors());
        return ApiErrorResponse.validationFail(apiValidationErrors);
    }
}
