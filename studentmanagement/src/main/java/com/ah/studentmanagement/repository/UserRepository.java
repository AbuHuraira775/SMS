package com.ah.studentmanagement.repository;

import com.ah.studentmanagement.dto.StudentDTO;
import com.ah.studentmanagement.entity.Students;
import com.ah.studentmanagement.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author "Abu Huraira"
 **/

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);

    Optional<Users> findByUsername(String username);

    Page<Students> findByActiveTrue(Pageable pageable);
}
