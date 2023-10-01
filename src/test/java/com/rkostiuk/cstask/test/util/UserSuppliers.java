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
        return new UserSupplierDecorator(usersWithoutId()) {
            private Long id = 0L;

            @Override
            protected void decorateCreated(User user) {
                id++;
                user.setId(id);
            }
        };
    }

    /**
     * Supplier that creates initialized users with unique email but with id == null
     */
    public static Supplier<User> usersWithoutId() {
        return new Supplier<>() {
            private int number = 0;

            @Override
            public User get() {
                number++;
                var user = new User();
                user.setFirstName("User");
                user.setLastName("Testman");
                user.setEmail("user" + number + "@mail.com");
                int birthYear = 1970 + (number % 53);
                user.setBirthDate(LocalDate.of(birthYear, 1, 1));
                return user;
            }
        };
    }
}
