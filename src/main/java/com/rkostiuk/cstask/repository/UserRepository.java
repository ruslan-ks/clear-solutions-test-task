package com.rkostiuk.cstask.repository;

import com.rkostiuk.cstask.dto.response.UserAddressResponse;
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
            select new com.rkostiuk.cstask.dto.response.UserAddressResponse(u, a)
            from Address a
            right join a.user u
    """)
    Page<UserAddressResponse> findByBirthDateBetween(LocalDate from, LocalDate to, Pageable pageable);
}
