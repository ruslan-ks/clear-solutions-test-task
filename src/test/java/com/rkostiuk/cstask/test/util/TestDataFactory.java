package com.rkostiuk.cstask.test.util;

import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.User;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface TestDataFactory {

    /**
     * Create user using provided mapper
     * @param userSupplier will be called to create every User
     * @return not terminated Stream of User objects created
     */
    Stream<User> users(Supplier<User> userSupplier);

    /**
     * Same as {@link TestDataFactory#userResponses(Supplier)}, but with size specified
     * @param size number of objects to be created
     * @param userSupplier creates users
     * @return List of DTOs of specified size
     */
    List<UserResponse> userResponses(int size, Supplier<User> userSupplier);

    /**
     * @param userSupplier creates users
     * @return Unterminated Stream of UserResponse objects
     */
    Stream<UserResponse> userResponses(Supplier<User> userSupplier);
}
