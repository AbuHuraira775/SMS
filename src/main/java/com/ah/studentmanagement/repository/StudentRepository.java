package com.ah.studentmanagement.repository;

import com.ah.studentmanagement.dto.EnrollmentSummaryDTO;
import com.ah.studentmanagement.dto.StudentDTO;
import com.ah.studentmanagement.entity.Courses;
import com.ah.studentmanagement.entity.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author "Abu Huraira"
 **/

@Repository
public interface StudentRepository extends JpaRepository<Students, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Page<Students> findByActiveTrue(Pageable pageable);

    boolean existsByEmailIgnoreCaseAndIdNot(String code, Long id);

    List<Students> findByActiveTrue();

    @EntityGraph(attributePaths = {"enrollments", "enrollments.course"})
    @Query(value = """                      
			select distinct s
			from Students s
			join s.enrollments e
		""",
            countQuery = """
					select count(distinct s)
					from Students s
					join s.enrollments e
				""")
    Page<Students> findEnrolledStudents(Pageable pageable);

    @Query( value = """                      
		    Select s
		    from Students s
		    join fetch s.enrollments e
		    join fetch e.course c
		    where s.id = :id
		    """)
    Optional<Students> findEnrolledStudentCourseDetails(@Param("id") Long id);


}
