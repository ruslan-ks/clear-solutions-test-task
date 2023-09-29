package com.rkostiuk.cstask.dto.response;

import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;

public record UserWithAddressResponse(UserResponse user, AddressResponse address) {
    public UserWithAddressResponse(User user, Address address) {
        this(new UserResponse(user), createResponseIfNotNull(address));
    }

    private static AddressResponse createResponseIfNotNull(Address address) {
        return address != null
                ? new AddressResponse(address)
                : null;
    }
}
