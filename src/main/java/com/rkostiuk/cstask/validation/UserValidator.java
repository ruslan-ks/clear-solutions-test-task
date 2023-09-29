package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.exception.CustomValidationException;
import com.rkostiuk.cstask.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserValidator extends CustomValidator<ValidUser> {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected CustomValidationException createValidationException(Errors errors) {
        return new CustomValidationException(errors);
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ValidUser.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var validUser = (ValidUser) target;
        validateEmail(validUser, errors);
    }

    private void validateEmail(ValidUser user, Errors errors) {
        boolean emailIsAlreadyInUse = userService.findUserByEmail(user.getEmail())
                .isPresent();
        if (emailIsAlreadyInUse) {
            rejectEmailAlreadyInUse(errors);
        }
    }

    private void rejectEmailAlreadyInUse(Errors errors) {
        errors.rejectValue("email", "NotInUse.email", "Email is already in use");
    }
}
