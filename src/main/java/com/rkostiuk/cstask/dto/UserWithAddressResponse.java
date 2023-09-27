package com.rkostiuk.cstask.dto;

import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.entity.UserAddress;

public record UserWithAddressResponse(UserResponse user, UserAddressResponse address) {
    public UserWithAddressResponse(User user, UserAddress userAddress) {
        this(new UserResponse(user), createResponseIfNotNull(userAddress));
    }

    private static UserAddressResponse createResponseIfNotNull(UserAddress userAddress) {
        return userAddress != null
                ? new UserAddressResponse(userAddress)
                : null;
    }
}
