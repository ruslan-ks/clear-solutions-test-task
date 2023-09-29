package com.rkostiuk.cstask.test.util;

import com.rkostiuk.cstask.dto.response.UserAddressResponse;
import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SimpleTestDataFactory implements TestDataFactory {

    @Override
    public Stream<User> users(Supplier<User> userSupplier) {
        return Stream.generate(userSupplier);
    }

    @Override
    public List<UserAddressResponse> userAddressResponses(int size,
                                                          Supplier<User> userSupplier,
                                                          Function<User, Address> addressMapper) {
        return userAddressResponses(userSupplier, addressMapper)
                .limit(size)
                .toList();
    }

    @Override
    public Stream<UserAddressResponse> userAddressResponses(Supplier<User> userSupplier,
                                                                   Function<User, Address> addressMapper) {
        return users(userSupplier)
                .map(user -> createUserAddressResponse(user, addressMapper));
    }

    private UserAddressResponse createUserAddressResponse(User user, Function<User, Address> addressMapper) {
        return new UserAddressResponse(user, addressMapper.apply(user));
    }
}
