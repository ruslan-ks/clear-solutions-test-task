package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.exception.AddressNotFoundException;
import com.rkostiuk.cstask.exception.UserNotFoundException;
import com.rkostiuk.cstask.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class DataJpaUserService implements UserService {
    private final UserRepository userRepository;

    public DataJpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserResponse> findUsersWithBirthDateBetween(UserSearchRequest request, Pageable pageable) {
        return userRepository.findUsersByBirthDateBetween(request.fromIncluding(), request.toExcluding(), pageable);
    }

    @Override
    public User findUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void setAddress(long userId, Address address) throws UserNotFoundException {
        User user = findUserById(userId);
        user.setAddress(address);
    }

    @Transactional
    @Override
    public void deleteUserById(long id) throws UserNotFoundException {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    @Override
    public Address findAddressByUserId(long userId) throws UserNotFoundException, AddressNotFoundException {
        User user = findUserById(userId);
        Address address = user.getAddress();
        if (address == null) {
            throw new AddressNotFoundException("Address for user with id " + userId + " not found");
        }
        return address;
    }
}
