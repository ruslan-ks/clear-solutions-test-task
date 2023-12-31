package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.exception.AddressNotFoundException;
import com.rkostiuk.cstask.exception.UserNotFoundException;
import com.rkostiuk.cstask.exception.UserPatchValidationException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> findUsersWithBirthDateBetween(UserSearchRequest request, Pageable pageable);

    User findUserById(long id) throws UserNotFoundException;

    Optional<User> findUserByEmail(String email);

    /**
     * Saves new user
     * @return User object with generated id
     */
    User addUser(User user);

    void setAddress(long userId, Address address) throws UserNotFoundException;

    /**
     * Set all non-null values of provided User object to existing one
     * @param userId user to be updated id
     * @param user contains user data
     */
    void patchUser(long userId, User user) throws UserNotFoundException, UserPatchValidationException;

    void deleteUserById(long userId) throws UserNotFoundException;

    Address findAddressByUserId(long userId) throws UserNotFoundException, AddressNotFoundException;
}
