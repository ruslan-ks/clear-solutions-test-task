package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.PageData;
import com.rkostiuk.cstask.TestUtils;
import com.rkostiuk.cstask.dto.UserSearchRequest;
import com.rkostiuk.cstask.dto.UserAddressResponse;
import com.rkostiuk.cstask.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

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
        PageData<UserAddressResponse> pageData = TestUtils.createPageData(createUserAddressResponses(10));
        var req = new UserSearchRequest(LocalDate.of(1900, 1, 1), LocalDate.of(2100, 1, 1));

        when(userRepository.findByBirthDateBetween(req.fromIncluding(), req.toExcluding(), pageData.pageable()))
                .thenReturn(pageData.page());

        Page<UserAddressResponse> actualPage = userService.findUsersWithBirthDateBetween(req, pageData.pageable());

        assertThat(actualPage).isEqualTo(pageData.page());
        verify(userRepository).findByBirthDateBetween(req.fromIncluding(), req.toExcluding(), pageData.pageable());
    }

    private List<UserAddressResponse> createUserAddressResponses(int size) {
        return userAddressResponses(size, usersWithSequentialId(), addressesWithUserId());
    }
}