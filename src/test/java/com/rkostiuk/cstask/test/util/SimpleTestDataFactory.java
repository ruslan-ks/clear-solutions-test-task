package com.rkostiuk.cstask.test.util;

import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.User;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SimpleTestDataFactory implements TestDataFactory {

    @Override
    public Stream<User> users(Supplier<User> userSupplier) {
        return Stream.generate(userSupplier);
    }

    @Override
    public List<UserResponse> userResponses(int size, Supplier<User> userSupplier) {
        return userResponses(userSupplier)
                .limit(size)
                .toList();
    }

    @Override
    public Stream<UserResponse> userResponses(Supplier<User> userSupplier) {
        return users(userSupplier).map(this::createUserResponse);
    }

    private UserResponse createUserResponse(User user) {
        return new UserResponse(user);
    }
}
