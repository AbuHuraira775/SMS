package com.ah.studentmanagement.repository;

import com.ah.studentmanagement.dto.CourseDTO;
import com.ah.studentmanagement.entity.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author "Abu Huraira"
 **/

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {

        boolean existsByCourseCodeIgnoreCase(String code);

        Page<Courses> findByActiveTrue(Pageable pageable);

        boolean existsByCourseCodeIgnoreCaseAndIdNot(String code, Long id);

        List<Courses> findByActiveTrue(Sort sort);
}
