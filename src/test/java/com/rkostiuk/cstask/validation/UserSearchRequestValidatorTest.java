package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserSearchRequestValidatorTest {
    @Mock
    private Errors errors;

    @Mock
    private UserSearchRequest searchRequest;

    private UserSearchRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserSearchRequestValidator();
    }

    @Test
    void validDataPassesValidation() {
        when(searchRequest.from()).thenReturn(LocalDate.of(2000, 12, 31));
        when(searchRequest.to()).thenReturn(LocalDate.of(2001, 1, 1));

        Errors errors = new BeanPropertyBindingResult(searchRequest, UserSearchRequest.class.getSimpleName());
        validator.validate(searchRequest, errors);

        assertThat(errors.getAllErrors()).isEmpty();
    }

    @Test
    void fromAfterToCausesValidationFail() {
        LocalDate from = LocalDate.of(2000, 1, 1);
        LocalDate to = LocalDate.of(1999, 12, 31);
        validationFails(from, to);
    }

    @Test
    void fromEqualToCausesValidationFail() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        validationFails(date, date);
    }

    private void validationFails(LocalDate from, LocalDate to) {
        when(searchRequest.from()).thenReturn(from);
        when(searchRequest.to()).thenReturn(to);

        validator.validate(searchRequest, errors);

        verify(errors).rejectValue(eq("to"), anyString(), anyString());
    }
}