package com.rkostiuk.cstask.test.util;

import com.rkostiuk.cstask.entity.Address;
import com.rkostiuk.cstask.entity.User;

import java.util.function.Function;

public class AddressMappers {

    /**
     * Predicate that creates Address with User.id
     * @return Predicate for Address creation
     */
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
