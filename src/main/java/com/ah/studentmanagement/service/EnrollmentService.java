package com.ah.studentmanagement.service;

import com.ah.studentmanagement.dto.EnrollmentDTO;
import com.ah.studentmanagement.dto.EnrollmentSummaryDTO;
import com.ah.studentmanagement.dto.StudentDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author "Abu Huraira"
 **/


public interface EnrollmentService {

    void enrollStudentToCourse(EnrollmentDTO enrollmentDTO);

    Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size);

    List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents();

    EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId);

}
