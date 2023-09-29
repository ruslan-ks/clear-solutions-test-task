package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.exception.CustomValidationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class CustomValidator<T> implements Validator {

    public void validate(T target) {
        var bindingResult = new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
        validate(target, bindingResult);
        if (bindingResult.hasErrors()) {
            throw createValidationException(bindingResult);
        }
    }

    /**
     * Used to create ValidationException if validation fails
     * @param errors validation errors
     * @return new ValidationException instance
     */
    protected abstract CustomValidationException createValidationException(Errors errors);
}
