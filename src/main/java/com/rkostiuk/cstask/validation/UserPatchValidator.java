package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserPatchValidator extends CustomValidator<User> {
    private final EmailValidator emailValidator;
    private final BirthDateValidator birthDateValidator;

    public UserPatchValidator(EmailValidator emailValidator, BirthDateValidator birthDateValidator) {
        this.emailValidator = emailValidator;
        this.birthDateValidator = birthDateValidator;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(User.class);
    }

    /**
     * @param user user with existing id
     */
    @Override
    public void validate(User user) {
        if (user.getId() == null || user.getId() < 1) {
            throw new IllegalArgumentException("User.id must be greater than 0: " + user.getId());
        }
        super.validate(user);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var user = (User) target;
        emailValidator.validatePatch(user.getId(), user.getEmail(), errors);
        birthDateValidator.validate(user.getBirthDate(), errors);
    }
}
