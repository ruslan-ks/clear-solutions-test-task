package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.config.UserValidationProperties;
import com.rkostiuk.cstask.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class UserValidator extends CustomValidator<ValidUser> {
    private final UserService userService;

    private final UserValidationProperties validationProperties;

    public UserValidator(UserService userService, UserValidationProperties validationProperties) {
        this.userService = userService;
        this.validationProperties = validationProperties;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ValidUser.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var validUser = (ValidUser) target;
        validateEmail(validUser, errors);
        validateAge(validUser, errors);
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

    private void validateAge(ValidUser user, Errors errors) {
        long currentUserAge = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());
        if (currentUserAge < validationProperties.minAge()) {
            rejectAgeNotReachedMin(errors);
        }
    }

    private void rejectAgeNotReachedMin(Errors errors) {
        errors.rejectValue("birthDate", "Min.age", "Minimum age allowed is " +
                validationProperties.minAge());
    }
}
