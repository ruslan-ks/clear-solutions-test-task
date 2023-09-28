package com.rkostiuk.cstask.dto;

import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;

public record UserAddressResponse(UserResponse user, AddressResponse address) {
    public UserAddressResponse(User user, Address address) {
        this(new UserResponse(user), createResponseIfNotNull(address));
    }

    private static AddressResponse createResponseIfNotNull(Address address) {
        return address != null
                ? new AddressResponse(address)
                : null;
    }
}
