package com.rkostiuk.cstask.test.util;

import com.rkostiuk.cstask.entity.User;

import java.time.LocalDate;
import java.util.function.Supplier;

public class UserSuppliers {

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
     * @return Supplier based on {@link UserSuppliers#usersWithSequentialId()}
     */
    public static Supplier<User> usersBornBetweenYearsSupplier(int fromIncluding, int toExcluding) {
        throwIfInvalidRange(fromIncluding, toExcluding);

        Supplier<User> userSupplier = usersWithSequentialId();
        return new UserSupplierDecorator(userSupplier) {
            @Override
            protected void decorateCreated(User user) {
                int birthYear = user.getId().intValue() % (toExcluding - fromIncluding);
                LocalDate oldDate = user.getBirthDate();
                LocalDate newDate = LocalDate.of(birthYear, oldDate.getMonth(), oldDate.getYear());
                user.setBirthDate(newDate);
            }
        };
    }

    private static void throwIfInvalidRange(int fromIncluding, int toExcluding) {
        if (fromIncluding >= toExcluding) {
            throw new IllegalArgumentException();
        }
    }
}
