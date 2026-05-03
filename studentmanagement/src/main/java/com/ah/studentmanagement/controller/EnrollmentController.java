package com.ah.studentmanagement.controller;

import com.ah.studentmanagement.dto.EnrollmentDTO;
import com.ah.studentmanagement.dto.EnrollmentSummaryDTO;
import com.ah.studentmanagement.dto.StudentDTO;
import com.ah.studentmanagement.service.CourseService;
import com.ah.studentmanagement.service.EnrollmentService;
import com.ah.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author "Abu Huraira"
 **/

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {


    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public EnrollmentController(CourseService courseService, StudentService studentService, EnrollmentService enrollmentService){
            this.studentService = studentService;
            this.courseService = courseService;
            this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enroll")
    public String showEnrollPage(Model model){
        model.addAttribute("enrollmentDTO", new EnrollmentDTO());
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("studentList", studentService.getAllStudents());
        return "enroll-course";
    }

    @GetMapping("/list")
    public String enrollmentList(RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model ){
        Page<EnrollmentSummaryDTO> students = enrollmentService.getEnrolledStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", "student is created");
        return "enrolled-students";
    }

    @PostMapping("/enroll")
    public String enrollCourses(@Valid @ModelAttribute("enrollmentDTO") EnrollmentDTO enrollmentDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("courseList", courseService.getAllCourses());
            model.addAttribute("studentList", studentService.getAllStudents());
            return "enroll-course";
        }

        enrollmentService.enrollStudentToCourse(enrollmentDTO);
        redirectAttributes.addAttribute("message", "Student is enrolled successfully ");
        return "redirect:/enrollments/list";
    }

    @GetMapping("/details/{id}")
    public String showStudentEnrollmentDetails(@PathVariable Long id, Model model, @RequestParam(defaultValue = "enrollments") String source){
        EnrollmentSummaryDTO enrollmentSummaryDTO = enrollmentService.findEnrolledStudentCourseDetails(id);
        model.addAttribute("enrollmentSummaryDTO", enrollmentSummaryDTO);
        model.addAttribute("source", source);
        return "enrollment-details";
    }
}
