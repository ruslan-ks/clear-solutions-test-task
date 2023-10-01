package com.rkostiuk.cstask.repository;

import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.User;
import com.rkostiuk.cstask.test.util.SimpleTestDataFactory;
import com.rkostiuk.cstask.test.util.TestDataFactory;
import com.rkostiuk.cstask.test.util.UserSupplierDecorator;
import com.rkostiuk.cstask.test.util.UserSuppliers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {
    private final TestDataFactory dataFactory = new SimpleTestDataFactory();

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindUsersWithBirthDateBetween() {
        int startYear = 1990;
        List<User> users = createUsersBornOnePerYear(startYear, 10);
        userRepository.saveAll(users);

        LocalDate from = LocalDate.of(startYear + 2, 1, 1);
        LocalDate to = LocalDate.of(startYear + users.size() - 2, 1, 1);
        Pageable pageable = PageRequest.of(0, users.size());

        List<UserResponse> actual = userRepository.findUsersByBirthDateBetween(from, to, pageable);

        List<UserResponse> expected = filterBornBetweenAndMapToUserResponse(users, from, to);
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    private List<User> createUsersBornOnePerYear(int startYear, int count) {
        return dataFactory.users(oneUserPerYearUserSupplier(startYear))
                .limit(count)
                .toList();
    }

    private Supplier<User> oneUserPerYearUserSupplier(int startYear) {
        return new UserSupplierDecorator(UserSuppliers.usersWithoutId()) {
            int index = 0;

            @Override
            protected void decorateCreated(User user) {
                index++;
                LocalDate birthDate = LocalDate.of(startYear + index, 1, 1);
                user.setBirthDate(birthDate);
            }
        };
    }

    private Predicate<User> userIsBornBetween(LocalDate from, LocalDate to) {
        return user -> {
            LocalDate birth = user.getBirthDate();
            return !(birth.isBefore(from) || birth.isAfter(to));
        };
    }

    private List<UserResponse> filterBornBetweenAndMapToUserResponse(List<User> users, LocalDate from, LocalDate to) {
        return users.stream()
                .filter(userIsBornBetween(from, to))
                .map(UserResponse::new)
                .toList();
    }
}