package com.rkostiuk.cstask;

import com.rkostiuk.cstask.dto.UserAddressResponse;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.entity.Address;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestData {

    /**
     * Create user using provided mapper
     * @param userSupplier will be called to create every User
     * @return not terminated Stream of User objects created
     */
    public static Stream<User> users(Supplier<User> userSupplier) {
        return Stream.generate(userSupplier);
    }

    /**
     * When called sequentially, increases id of User created
     * (e.g. 1-st call returns User with id=1, second with id=2 and so on)
     * @return User object with id > 0
     */
    public static Supplier<User> usersWithSequentialId() {
        return new Supplier<>() {
            private Long id = 0L;

            @Override
            public User get() {
                id++;
                var user = new User();
                user.setId(id);
                user.setFirstName("User");
                user.setLastName(String.valueOf(id));
                user.setEmail("user" + id + "@mail.com");
                int birthYear = 1970 + (id.intValue() % 53);
                user.setBirthDate(LocalDate.of(birthYear, 1, 1));
                return user;
            }
        };
    }

    /**
     * Same as {@link TestData#userAddressResponses(Supplier, Function)}, but with size specified
     * @param size number of objects to be created
     * @param userSupplier creates users
     * @param addressMapper creates addresses for users
     * @return List of DTOs of specified size
     */
    public static List<UserAddressResponse> userAddressResponses(int size,
                                                                 Supplier<User> userSupplier,
                                                                 Function<User, Address> addressMapper) {
        return userAddressResponses(userSupplier, addressMapper)
                .limit(size)
                .toList();
    }

    /**
     * @param userSupplier creates users
     * @param addressMapper creates addresses for users
     * @return Unterminated Stream of UserWithAddressResponse objects
     */
    public static Stream<UserAddressResponse> userAddressResponses(Supplier<User> userSupplier,
                                                                   Function<User, Address> addressMapper) {
        return users(userSupplier)
                .map(user -> createUserAddressResponse(user, addressMapper));
    }

    /**
     * @param user user to be included into dto
     * @param addressMapper takes user and creates UserAddress for this user
     * @return new UserWithAddressResponse containing user data and newly created UserAddress data
     */
    private static UserAddressResponse createUserAddressResponse(User user, Function<User, Address> addressMapper) {
        return new UserAddressResponse(user, addressMapper.apply(user));
    }

    public static Function<User, Address> addressesWithUserId() {
        return user -> {
            var address = new Address();
            address.setUser(user);
            address.setCity("City " + user.getId());
            address.setStreet("Street " + user.getId());
            address.setBuilding(user.getId().intValue());
            return address;
        };
    }
}
