package com.rkostiuk.cstask.validation;

import org.springframework.validation.Errors;

import java.time.LocalDate;

public interface BirthDateValidator {
    void validate(LocalDate birthDate, Errors errors);
}
