package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.config.UserValidationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class MinimumAgeBirthDateValidator implements BirthDateValidator {
    private final UserValidationProperties validationProperties;

    public MinimumAgeBirthDateValidator(UserValidationProperties validationProperties) {
        this.validationProperties = validationProperties;
    }

    @Override
    public void validate(LocalDate birthDate, Errors errors) {
        if (isInvalidAge(birthDate)) {
            rejectAgeNotReachedMin(errors);
        }
    }

    private boolean isInvalidAge(LocalDate birthDate) {
        long currentUserAge = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        return currentUserAge < validationProperties.minAge();
    }

    private void rejectAgeNotReachedMin(Errors errors) {
        errors.rejectValue("birthDate", "Min.age", "Minimum age allowed is " +
                validationProperties.minAge());
    }
}
