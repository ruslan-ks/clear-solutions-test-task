package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.repository.UserRepository;
import com.rkostiuk.cstask.test.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataJpaUserServiceTest {
    private final TestDataFactory dataFactory = new SimpleTestDataFactory();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DataJpaUserService userService;

    @Test
    void findUsersWithBirthDateBetween() {
        List<UserResponse> expectedResponses = createUserResponses(10);
        var req = new UserSearchRequest(LocalDate.of(1900, 1, 1), LocalDate.of(2100, 1, 1));

        Pageable pageable = PageRequest.of(0, expectedResponses.size());
        when(userRepository.findUsersByBirthDateBetween(req.fromIncluding(), req.toExcluding(), pageable))
                .thenReturn(expectedResponses);

        List<UserResponse> actualResponses = userService.findUsersWithBirthDateBetween(req, pageable);

        assertThat(actualResponses).isEqualTo(expectedResponses);
        verify(userRepository).findUsersByBirthDateBetween(req.fromIncluding(), req.toExcluding(), pageable);
    }

    private List<UserResponse> createUserResponses(int size) {
        Supplier<User> userSupplier = UserSuppliers.usersWithSequentialId();
        return dataFactory.userResponses(size, userSupplier);
    }
}