package com.rkostiuk.cstask.controller;

import com.rkostiuk.cstask.dto.mapper.AddressMapper;
import com.rkostiuk.cstask.dto.request.NewAddressRequest;
import com.rkostiuk.cstask.dto.response.AddressResponse;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users/{userId}/address")
@RestController
public class AddressController {
    private final UserService userService;
    private final AddressMapper addressMapper;

    public AddressController(UserService userService, AddressMapper addressMapper) {
        this.userService = userService;
        this.addressMapper = addressMapper;
    }

    @GetMapping
    public AddressResponse getAddress(@PathVariable long userId) {
        Address address = userService.findAddressByUserId(userId);
        return addressMapper.toAddressResponse(address);
    }

    @PutMapping
    public void setAddress(@PathVariable long userId, @Valid @RequestBody NewAddressRequest newAddressRequest) {
        Address address = addressMapper.toAddress(newAddressRequest);
        userService.setAddress(userId, address);
    }

}
