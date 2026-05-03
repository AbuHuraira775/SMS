package com.ah.studentmanagement.service.imp;

import com.ah.studentmanagement.controller.CourseController;
import com.ah.studentmanagement.dto.CourseDTO;
import com.ah.studentmanagement.entity.Courses;
import com.ah.studentmanagement.repository.CourseRepository;
import com.ah.studentmanagement.service.CourseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author "Abu Huraira"
 **/

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper mapper){
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        log.info("<serviceImpl> creating course with code : {}", courseDTO.getCourseCode());
        Courses courses = mapper.map(courseDTO, Courses.class);
        courseRepository.save(courses);
        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existsByCourseCode(String code) {
        log.info("<serviceImpl> checking if code exists: {}", code);
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public Page<CourseDTO> getCoursesByActiveTrue(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return courseRepository.findByActiveTrue(pageRequest)
                .map(course -> mapper.map(course,CourseDTO.class));
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Courses course = courseRepository.findById(id).orElseThrow(()-> new RuntimeException("No course found"));
        return mapper.map(course,CourseDTO.class);
        }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Courses course = courseRepository.findById(id).orElseThrow(()-> new RuntimeException("No course found"));
        mapper.map(courseDTO, course);
        courseRepository.save(course);
        return mapper.map(course, CourseDTO.class);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findByActiveTrue(Sort.by("courseName")).stream()
                .map( course -> mapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCourseCodeAndIdNot(String code, Long id) {
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code, id);
    }
}
