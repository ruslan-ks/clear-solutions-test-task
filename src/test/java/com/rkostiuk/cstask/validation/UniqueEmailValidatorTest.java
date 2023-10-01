package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.service.UserService;
import com.rkostiuk.cstask.test.util.SimpleTestDataFactory;
import com.rkostiuk.cstask.test.util.TestDataFactory;
import com.rkostiuk.cstask.test.util.UserSuppliers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniqueEmailValidatorTest {
    private final TestDataFactory dataFactory = new SimpleTestDataFactory();

    @Mock
    private UserService userService;

    @InjectMocks
    private UniqueEmailValidator emailValidator;

    @Mock
    private Errors errors;

    @Nested
    class NewUserEmailValidationTest {
        @Test
        void emailInUseCausesValidationFail() {
            String existingEmail = "existing.email@gmail.com";
            Optional<User> foundUser = Optional.of(createUserWithEmail(existingEmail));
            when(userService.findUserByEmail(existingEmail)).thenReturn(foundUser);

            emailValidator.validate(existingEmail, errors);

            verify(errors).rejectValue(eq("email"), anyString(), anyString());
        }
    }

    @Nested
    class UserPatchValidationTest {
        @Test
        void emailInUseByAnotherUserCausesValidationFail() {
            String existingEmail = "existing.email@gmail.com";
            User user = createUserWithEmail(existingEmail);
            when(userService.findUserByEmail(existingEmail)).thenReturn(Optional.of(user));

            long userToPatchId = user.getId() + 1;
            emailValidator.validatePatch(userToPatchId, existingEmail, errors);

            verify(errors).rejectValue(eq("email"), anyString(), anyString());
        }

        @Test
        void emailInUseByTheSameUserDoesNotCauseValidationFail() {
            String existingEmail = "existing.email@gmail.com";
            User user = createUserWithEmail(existingEmail);
            when(userService.findUserByEmail(existingEmail)).thenReturn(Optional.of(user));

            Errors errors = new BeanPropertyBindingResult(user, User.class.getSimpleName());
            emailValidator.validatePatch(user.getId(), existingEmail, errors);

            assertThat(errors.getAllErrors()).isEmpty();
        }
    }

    private User createUserWithEmail(String email) {
        User user = dataFactory.users(UserSuppliers.usersWithSequentialId())
                .limit(1)
                .findAny()
                .orElseThrow();
        user.setEmail(email);
        return user;
    }
}