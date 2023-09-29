package com.rkostiuk.cstask.controller;

import com.rkostiuk.cstask.dto.mapper.AddressMapper;
import com.rkostiuk.cstask.dto.request.NewAddressRequest;
import com.rkostiuk.cstask.dto.request.NewUserRequest;
import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import com.rkostiuk.cstask.dto.response.UserAddressResponse;
import com.rkostiuk.cstask.dto.mapper.UserMapper;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.service.UserService;
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
    private final AddressMapper addressMapper;

    public UserController(UserService userService, UserMapper userMapper, AddressMapper addressMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    @GetMapping
    public Page<UserAddressResponse> search(@RequestBody UserSearchRequest searchRequest, Pageable pageable) {
        return userService.findUsersWithBirthDateBetween(searchRequest, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserAddressResponse create(@Valid @RequestBody NewUserRequest newUserRequest) {
        User user = userMapper.toUser(newUserRequest);
        user = userService.addUser(user);
        return userMapper.toUserAddressResponse(user);
    }

    @PutMapping("/{userId}/address")
    public void setAddress(@PathVariable long userId,
                           @Valid @RequestBody NewAddressRequest newAddressRequest) {
        Address address = addressMapper.toAddress(newAddressRequest);
        userService.setAddress(userId, address);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.deleteUserById(userId);
    }
}
