package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserAddressResponse;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserAddressResponse> findUsersWithBirthDateBetween(UserSearchRequest request, Pageable pageable);
    User findUserById(long id) throws UserNotFoundException;

    /**
     * Saves new user
     * @return User object with generated id
     */
    User addUser(User user);

    void deleteUserById(long userId) throws UserNotFoundException;
}
