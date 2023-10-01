package com.rkostiuk.cstask.exception;

import com.rkostiuk.cstask.entity.User;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class UserPatchValidationException extends CustomValidationException {
    public UserPatchValidationException(Set<ConstraintViolation<User>> violations) {
        super(violations);
    }
}
