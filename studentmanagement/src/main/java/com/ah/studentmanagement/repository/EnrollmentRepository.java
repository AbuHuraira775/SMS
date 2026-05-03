package com.ah.studentmanagement.repository;

import com.ah.studentmanagement.dto.DashboardStatsDTO;
import com.ah.studentmanagement.dto.EnrollmentDTO;
import com.ah.studentmanagement.dto.EnrollmentSummaryDTO;
import com.ah.studentmanagement.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

/**
 * @author "Abu Huraira"
 **/
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("""
            SELECT COUNT (DISTINCT e.student.id)
            FROM Enrollment e
            WHERE e.enrollDate BETWEEN :startDate AND :endDate
            """)
    long countDistinctStudentByEnrollDateBetween(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
