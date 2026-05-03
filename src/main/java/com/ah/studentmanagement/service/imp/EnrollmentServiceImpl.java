package com.ah.studentmanagement.service.imp;

import com.ah.studentmanagement.dto.CourseDTO;
import com.ah.studentmanagement.dto.EnrollmentDTO;
import com.ah.studentmanagement.dto.EnrollmentSummaryDTO;
import com.ah.studentmanagement.entity.Courses;
import com.ah.studentmanagement.entity.Enrollment;
import com.ah.studentmanagement.entity.Students;
import com.ah.studentmanagement.repository.CourseRepository;
import com.ah.studentmanagement.repository.EnrollmentRepository;
import com.ah.studentmanagement.repository.StudentRepository;
import com.ah.studentmanagement.service.CourseService;
import com.ah.studentmanagement.service.EnrollmentService;
import com.ah.studentmanagement.service.StudentService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author "Abu Huraira"
 **/

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper mapper;

    public EnrollmentServiceImpl(CourseRepository courseRepository,
                                 StudentRepository studentRepository,
                                 EnrollmentRepository enrollmentRepository,
                                 ModelMapper mapper){
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public void enrollStudentToCourse(EnrollmentDTO enrollmentDTO) {
        Students students = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(()-> new RuntimeException("Student not found"));

        for(Long courseId: enrollmentDTO.getCourseIds()){
            Courses course = courseRepository.findById(courseId)
                    .orElseThrow(()-> new RuntimeException("Course not found"));

            if(enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(), courseId)){
                continue;
            }
            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(course);
            enrollment.setStudent(students);

            students.getEnrollments().add(enrollment);
            course.getEnrollments().add(enrollment);
            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    public Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return studentRepository.findEnrolledStudents(pageRequest)
                .map(student-> {
                   EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                   dto.setStudentId(student.getId());
                   dto.setStudentName(student.getFirstName()+" "+student.getLastName());
                   dto.setEmail(student.getEmail());
                   dto.setCourseCount(student.getEnrollments().size());
                   BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getCourseFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                   dto.setTotalFee(totalFee);
                   return dto;
                });
    }

    @Override
    public EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId) {
        return studentRepository.findEnrolledStudentCourseDetails(studentId)
                .map(student -> {
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setId(student.getId());
                    dto.setStudentName(student.getFirstName()+" "+student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getCourseFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    List<CourseDTO> coursesList = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse())
                            .map(course -> mapper.map(course, CourseDTO.class))
                            .collect(Collectors.toList());

                    dto.setCourseList(coursesList);
                    return dto;
                })
                .orElseThrow(()-> new RuntimeException("Student not found"));
    }

    @Override
    public List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents() {
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "id"));
        return studentRepository.findEnrolledStudents(pageRequest)
                .map(student-> {
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName()+" "+student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getCourseFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);
                    return dto;
                })
                .getContent();
    }

}
