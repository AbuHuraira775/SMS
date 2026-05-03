package com.ah.studentmanagement.service;

import com.ah.studentmanagement.dto.CourseDTO;
import com.ah.studentmanagement.dto.StudentDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Abu Huraira"
 **/

@Service
@Transactional
public interface StudentService {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    StudentDTO createStudent(StudentDTO studentDTO);

    Page<StudentDTO> getStudentsByActiveTrue(int page, int size);

    StudentDTO getStudentById(Long id);

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    List<StudentDTO> getAllStudents();

}
