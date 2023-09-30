package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.exception.AddressNotFoundException;
import com.rkostiuk.cstask.exception.UserNotFoundException;
import com.rkostiuk.cstask.repository.AddressRepository;
import com.rkostiuk.cstask.repository.UserRepository;
import com.rkostiuk.cstask.util.BeanHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class DataJpaUserService implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BeanHelper beanHelper;

    public DataJpaUserService(UserRepository userRepository,
                              AddressRepository addressRepository,
                              BeanHelper beanHelper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.beanHelper = beanHelper;
    }

    @Override
    public List<UserResponse> findUsersWithBirthDateBetween(UserSearchRequest request, Pageable pageable) {
        return userRepository.findUsersByBirthDateBetween(request.fromIncluding(), request.toExcluding(), pageable);
    }

    @Override
    public User findUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
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
        address.setId(user.getId());
        addressRepository.save(address);
    }

    @Transactional
    @Override
    public void patchUser(long userId, User user) throws UserNotFoundException {
        User existingUser = findUserById(userId);
        beanHelper.copyNonNullProperties(existingUser, user);
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
        return addressRepository.findById(user.getId())
                .orElseThrow(() -> new AddressNotFoundException("Address for user with id " + userId + " not found"));
    }
}
