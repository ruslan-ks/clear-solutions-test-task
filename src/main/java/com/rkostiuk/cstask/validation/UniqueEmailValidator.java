package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UniqueEmailValidator implements EmailValidator {
    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validate(String email, Errors errors) {
        boolean emailIsAlreadyInUse = userService.findUserByEmail(email)
                .isPresent();
        if (emailIsAlreadyInUse) {
            rejectEmailAlreadyInUse(errors);
        }
    }

    @Override
    public void validatePatch(long userId, String newEmail, Errors errors) {
        userService.findUserByEmail(newEmail)
                .ifPresent(existingUser -> rejectIfNotSameUser(existingUser, userId, errors));
    }

    private void rejectIfNotSameUser(User existingUser, long userToPatchId, Errors errors) {
        if (!existingUser.getId().equals(userToPatchId)) {
            rejectEmailAlreadyInUse(errors);
        }
    }

    private void rejectEmailAlreadyInUse(Errors errors) {
        errors.rejectValue("email", "NotInUse.email", "Email is already in use");
    }
}
