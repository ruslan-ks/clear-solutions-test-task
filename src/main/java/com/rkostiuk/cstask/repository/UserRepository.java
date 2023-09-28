package com.rkostiuk.cstask.repository;

import com.rkostiuk.cstask.dto.UserAddressResponse;
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
            SELECT NEW com.rkostiuk.cstask.dto.UserAddressResponse(u, a)
            FROM Address a
            RIGHT JOIN a.user u
    """)
    Page<UserAddressResponse> findByBirthDateBetween(LocalDate from, LocalDate to, Pageable pageable);
}
