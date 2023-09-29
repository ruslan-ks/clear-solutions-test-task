package com.rkostiuk.cstask.dto.response;

import com.rkostiuk.cstask.entity.User;

public record UserResponse(long id, String firstName, String secondName, String email, String phone) {
    public UserResponse(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone());
    }
}
