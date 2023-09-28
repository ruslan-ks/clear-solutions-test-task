package com.rkostiuk.cstask.controller;

import com.rkostiuk.cstask.dto.UserSearchRequest;
import com.rkostiuk.cstask.dto.UserAddressResponse;
import com.rkostiuk.cstask.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.deleteUserById(userId);
    }
}
