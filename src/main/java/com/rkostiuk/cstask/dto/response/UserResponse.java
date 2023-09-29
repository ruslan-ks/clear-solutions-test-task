package com.rkostiuk.cstask.dto.response;

import com.rkostiuk.cstask.entity.User;

import java.time.LocalDate;

public record UserResponse(long id,
                           String firstName,
                           String lastName,
                           String email,
                           LocalDate birthDate,
                           String phone) {
    public UserResponse(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthDate(),
                user.getPhone());
    }
}
