package com.rkostiuk.cstask.service;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.exception.AddressNotFoundException;
import com.rkostiuk.cstask.exception.UserNotFoundException;
import com.rkostiuk.cstask.exception.UserPatchValidationException;
import com.rkostiuk.cstask.repository.AddressRepository;
import com.rkostiuk.cstask.repository.UserRepository;
import com.rkostiuk.cstask.util.BeanHelper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class DataJpaUserService implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BeanHelper beanHelper;
    private final Validator validator;

    public DataJpaUserService(UserRepository userRepository,
                              AddressRepository addressRepository,
                              BeanHelper beanHelper,
                              Validator validator) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.beanHelper = beanHelper;
        this.validator = validator;
    }

    @Override
    public List<UserResponse> findUsersWithBirthDateBetween(UserSearchRequest request, Pageable pageable) {
        return userRepository.findUsersByBirthDateBetween(request.from(), request.to(), pageable);
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
        address.setUser(user);
        addressRepository.save(address);
    }

    @Transactional
    @Override
    public void patchUser(long userId, User userPatch) throws UserNotFoundException, UserPatchValidationException {
        User existingUser = findUserById(userId);
        beanHelper.copyNonNullProperties(existingUser, userPatch);
        validatePatchedUser(existingUser);
    }

    private void validatePatchedUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new UserPatchValidationException(violations);
        }
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
