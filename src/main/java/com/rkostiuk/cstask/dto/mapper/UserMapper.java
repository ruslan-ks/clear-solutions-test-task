package com.rkostiuk.cstask.dto.mapper;

import com.rkostiuk.cstask.dto.request.NewUserRequest;
import com.rkostiuk.cstask.dto.response.UserWithAddressResponse;
import com.rkostiuk.cstask.entity.User;

public interface UserMapper {

    /**
     * @param newUserRequest dto containing User and Address data
     * @return new User object with Address
     */
    User toUser(NewUserRequest newUserRequest);

    UserWithAddressResponse toUserAddressResponse(User user);
}
