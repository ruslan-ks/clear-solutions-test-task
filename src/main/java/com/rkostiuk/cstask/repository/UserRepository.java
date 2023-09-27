package com.rkostiuk.cstask.repository;

import com.rkostiuk.cstask.dto.UserWithAddressResponse;
import com.rkostiuk.cstask.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT NEW com.rkostiuk.cstask.dto.UserWithAddressResponse(u, ua)
            FROM UserAddress ua
            RIGHT JOIN ua.user u
    """)
    Page<UserWithAddressResponse> findByBirthDateBetween(LocalDate from, LocalDate to, Pageable pageable);
}
