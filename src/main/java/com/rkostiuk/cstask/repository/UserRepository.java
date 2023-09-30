package com.rkostiuk.cstask.repository;

import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<UserResponse> findUsersByBirthDateBetween(LocalDate from, LocalDate to, Pageable pageable);
    Optional<User> findUserByEmail(String email);
}
