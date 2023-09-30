package com.rkostiuk.cstask.dto.mapper;

import com.rkostiuk.cstask.dto.request.NewUserRequest;
import com.rkostiuk.cstask.dto.request.PatchUserRequest;
import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.dto.response.UserWithAddressResponse;
import com.rkostiuk.cstask.entity.User;

public interface UserMapper {
    User toUser(NewUserRequest newUserRequest);
    User toUser(PatchUserRequest patchUserRequest);
    UserWithAddressResponse toUserWithAddressResponse(User user);
    UserResponse toUserResponse(User user);
}
