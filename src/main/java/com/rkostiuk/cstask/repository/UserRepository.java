package com.rkostiuk.cstask.repository;

import com.rkostiuk.cstask.dto.response.UserResponse;
import com.rkostiuk.cstask.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("""
//            select new com.rkostiuk.cstask.dto.response.UserAddressResponse(u, a)
//            from Address a
//            right join a.user u
//    """)
    Page<UserResponse> findUsersByBirthDateBetween(LocalDate from, LocalDate to, Pageable pageable);

    Optional<User> findUserByEmail(String email);
}
