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
        when(searchRequest.fromIncluding()).thenReturn(LocalDate.of(2000, 12, 31));
        when(searchRequest.toExcluding()).thenReturn(LocalDate.of(2001, 1, 1));

        Errors errors = new BeanPropertyBindingResult(searchRequest, UserSearchRequest.class.getSimpleName());
        validator.validate(searchRequest, errors);

        assertThat(errors.getAllErrors()).isEmpty();
    }

    @Test
    void fromAfterToCausesValidationFail() {
        LocalDate fromIncluding = LocalDate.of(2000, 1, 1);
        LocalDate toExcluding = LocalDate.of(1999, 12, 31);
        validationFails(fromIncluding, toExcluding);
    }

    @Test
    void fromEqualToCausesValidationFail() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        validationFails(date, date);
    }

    private void validationFails(LocalDate fromIncluding, LocalDate toExcluding) {
        when(searchRequest.fromIncluding()).thenReturn(fromIncluding);
        when(searchRequest.toExcluding()).thenReturn(toExcluding);

        validator.validate(searchRequest, errors);

        verify(errors).rejectValue(eq("toExcluding"), anyString(), anyString());
    }
}