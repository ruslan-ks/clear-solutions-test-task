package com.rkostiuk.cstask.validation;

import org.springframework.validation.Errors;

public interface EmailValidator {
    void validate(String email, Errors errors);
    void validatePatch(long userId, String newEmail, Errors errors);
}
