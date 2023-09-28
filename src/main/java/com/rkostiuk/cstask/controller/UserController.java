package com.rkostiuk.cstask.controller;

import com.rkostiuk.cstask.dto.UserSearchRequest;
import com.rkostiuk.cstask.dto.UserAddressResponse;
import com.rkostiuk.cstask.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserAddressResponse> search(@RequestBody UserSearchRequest searchRequest, Pageable pageable) {
        return userService.findUsersWithBirthDateBetween(searchRequest, pageable);
    }
}
