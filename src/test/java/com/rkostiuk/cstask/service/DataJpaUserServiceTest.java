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
import org.springframework.data.domain.Page;

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
        PageData<UserResponse> pageData = TestUtils.createPageData(createUserResponses(10));
        var req = new UserSearchRequest(LocalDate.of(1900, 1, 1), LocalDate.of(2100, 1, 1));

        when(userRepository.findUsersByBirthDateBetween(req.fromIncluding(), req.toExcluding(), pageData.pageable()))
                .thenReturn(pageData.page());

        Page<UserResponse> actualPage = userService.findUsersWithBirthDateBetween(req, pageData.pageable());

        assertThat(actualPage).isEqualTo(pageData.page());
        verify(userRepository).findUsersByBirthDateBetween(req.fromIncluding(), req.toExcluding(), pageData.pageable());
    }

    private List<UserResponse> createUserResponses(int size) {
        Supplier<User> userSupplier = UserSuppliers.usersWithSequentialId();
        return dataFactory.userResponses(size, userSupplier);
    }
}