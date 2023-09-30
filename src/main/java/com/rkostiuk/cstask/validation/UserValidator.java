package com.rkostiuk.cstask.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserValidator extends CustomValidator<ValidUser> {
    private final EmailValidator emailValidator;
    private final BirthDateValidator birthDateValidator;


    public UserValidator(EmailValidator emailValidator, BirthDateValidator birthDateValidator) {
        this.emailValidator = emailValidator;
        this.birthDateValidator = birthDateValidator;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ValidUser.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var user = (ValidUser) target;
        emailValidator.validate(user.getEmail(), errors);
        birthDateValidator.validate(user.getBirthDate(), errors);
    }
}
