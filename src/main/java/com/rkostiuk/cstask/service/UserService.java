package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.UserSearchRequest;
import com.rkostiuk.cstask.dto.UserAddressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserAddressResponse> findUsersWithBirthDateBetween(UserSearchRequest request, Pageable pageable);
}
