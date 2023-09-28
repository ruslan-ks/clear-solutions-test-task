package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.UserSearchRequest;
import com.rkostiuk.cstask.dto.UserAddressResponse;
import com.rkostiuk.cstask.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.rkostiuk.cstask.TestData.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataJpaUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DataJpaUserService userService;

    @Test
    void findUsersWithBirthDateBetween() {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<UserAddressResponse> expectedPage = createPage(pageable, createUserAddressResponses(pageSize));
        var request = new UserSearchRequest(LocalDate.of(1900, 1, 1), LocalDate.of(2100, 1, 1));
        when(userRepository.findByBirthDateBetween(request.fromIncluding(), request.toExcluding(), pageable))
                .thenReturn(expectedPage);

        Page<UserAddressResponse> actualPage = userService.findUsersWithBirthDateBetween(request, pageable);

        assertThat(actualPage).isEqualTo(expectedPage);
        verify(userRepository).findByBirthDateBetween(request.fromIncluding(), request.toExcluding(), pageable);
    }

    private List<UserAddressResponse> createUserAddressResponses(int size) {
        return userAddressResponses(size, usersWithSequentialId(), addressesWithUserId());
    }

    private <T> Page<T> createPage(Pageable pageable, List<T> list) {
        return new PageImpl<>(list, pageable, list.size());
    }
}