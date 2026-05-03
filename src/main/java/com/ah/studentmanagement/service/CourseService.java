package com.ah.studentmanagement.service;

import com.ah.studentmanagement.dto.CourseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Abu Huraira"
 **/

@Service
@Transactional
public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO);

    boolean existsByCourseCode(String code);

    boolean existsByCourseCodeAndIdNot(String code, Long id);

    Page<CourseDTO> getCoursesByActiveTrue(int page,int size);

    CourseDTO getCourseById(Long id);

    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    List<CourseDTO> getAllCourses();
}
