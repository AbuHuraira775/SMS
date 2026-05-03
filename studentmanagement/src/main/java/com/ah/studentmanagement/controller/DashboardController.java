package com.ah.studentmanagement.controller;

import com.ah.studentmanagement.service.CourseService;
import com.ah.studentmanagement.service.DashboardService;
import com.ah.studentmanagement.service.EnrollmentService;
import com.ah.studentmanagement.service.StudentService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author "Abu Huraira"
 **/

@Controller
public class DashboardController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final DashboardService dashboardService;

    public DashboardController(StudentService studentService,
                               CourseService courseService,
                               EnrollmentService enrollmentService,
                               DashboardService dashboardService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("students", enrollmentService.getRecentlyEnrolledStudents());
        model.addAttribute("dashboardStats", dashboardService.getDashboardStats());
        return "dashboard";
    }

}
