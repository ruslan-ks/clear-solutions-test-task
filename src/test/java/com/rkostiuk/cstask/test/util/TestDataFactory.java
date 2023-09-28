package com.rkostiuk.cstask.test.util;

import com.rkostiuk.cstask.dto.UserAddressResponse;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;

import java.util.List;
import java.util.function.Function;
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
     * Same as {@link TestDataFactory#userAddressResponses(Supplier, Function)}, but with size specified
     * @param size number of objects to be created
     * @param userSupplier creates users
     * @param addressMapper creates addresses for users
     * @return List of DTOs of specified size
     */
    List<UserAddressResponse> userAddressResponses(int size,
                                                   Supplier<User> userSupplier,
                                                   Function<User, Address> addressMapper);

    /**
     * @param userSupplier creates users
     * @param addressMapper creates addresses for users
     * @return Unterminated Stream of UserWithAddressResponse objects
     */
    Stream<UserAddressResponse> userAddressResponses(Supplier<User> userSupplier,
                                                     Function<User, Address> addressMapper);
}
