package com.ah.studentmanagement.service.imp;

import com.ah.studentmanagement.dto.DashboardStatsDTO;
import com.ah.studentmanagement.repository.CourseRepository;
import com.ah.studentmanagement.repository.EnrollmentRepository;
import com.ah.studentmanagement.repository.StudentRepository;
import com.ah.studentmanagement.service.DashboardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author "Abu Huraira"
 **/

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper mapper;

    public DashboardServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.mapper = mapper;
    }


    @Override
    public DashboardStatsDTO getDashboardStats() {
        long totalStudents = studentRepository.count();
        long totalCourses = courseRepository.count();
        String topPerformingCourse = topPerformingCourse();

        YearMonth currentDate = YearMonth.now();
        LocalDateTime startDate = currentDate.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentDate.atEndOfMonth().atTime(LocalTime.MAX);

        long studentsEnrolledThisMonth = enrollmentRepository.countDistinctStudentByEnrollDateBetween(startDate, endDate);
        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();
        dashboardStatsDTO.setTotalStudents(totalStudents);
        dashboardStatsDTO.setTotalCourses(totalCourses);
        dashboardStatsDTO.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDTO.setStudentsEnrolledThisMonth(studentsEnrolledThisMonth);
        return dashboardStatsDTO;
    }

    private String topPerformingCourse(){
        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(e-> e.getCourse().getCourseCode(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
}
}
