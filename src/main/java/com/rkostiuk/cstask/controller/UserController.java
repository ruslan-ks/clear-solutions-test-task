package com.rkostiuk.cstask.controller;

import com.rkostiuk.cstask.dto.request.NewUserRequest;
import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserWithAddressResponse;
import com.rkostiuk.cstask.dto.mapper.UserMapper;
import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.service.UserService;
import com.rkostiuk.cstask.validation.UserSearchRequestValidator;
import com.rkostiuk.cstask.validation.UserValidator;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final UserSearchRequestValidator userSearchRequestValidator;

    public UserController(UserService userService,
                          UserMapper userMapper,
                          UserValidator userValidator,
                          UserSearchRequestValidator userSearchRequestValidator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
        this.userSearchRequestValidator = userSearchRequestValidator;
    }

    @GetMapping
    public Page<UserResponse> search(@RequestBody UserSearchRequest searchRequest, Pageable pageable) {
        userSearchRequestValidator.validate(searchRequest);
        return userService.findUsersWithBirthDateBetween(searchRequest, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserWithAddressResponse create(@Valid @RequestBody NewUserRequest newUserRequest) {
        userValidator.validate(newUserRequest);
        User user = userMapper.toUser(newUserRequest);
        user = userService.addUser(user);
        return userMapper.toUserWithAddressResponse(user);
    }

    @GetMapping("/{userId}")
    public UserResponse findById(@PathVariable long userId) {
        User user = userService.findUserById(userId);
        return userMapper.toUserResponse(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.deleteUserById(userId);
    }
}
