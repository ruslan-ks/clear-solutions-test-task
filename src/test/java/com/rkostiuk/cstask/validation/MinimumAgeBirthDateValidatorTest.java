package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.config.UserValidationProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.Year;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinimumAgeBirthDateValidatorTest {

    @Mock
    private UserValidationProperties validationProperties;

    @Mock
    private Errors errors;

    @InjectMocks
    private MinimumAgeBirthDateValidator validator;

    @Test
    void underOneYearCausesValidationFail() {
        validationFails(LocalDate.now(), 1);
    }

    @Test
    void underEighteenCausesValidationFail() {
        int currentYear = Year.now().getValue();
        LocalDate seventeenYearOldBirth = LocalDate.of(currentYear - 17, 1, 1);
        validationFails(seventeenYearOldBirth, 18);
    }

    private void validationFails(LocalDate birthDate, int minAgeProperty) {
        when(validationProperties.minAge()).thenReturn(minAgeProperty);

        validator.validate(birthDate, errors);

        verify(errors).rejectValue(eq("birthDate"), anyString(), anyString());
    }
}