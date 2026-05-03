package com.ah.studentmanagement.controller;

import com.ah.studentmanagement.dto.CourseDTO;
import com.ah.studentmanagement.service.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/course")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping("/new")
    public String showCreateCourse(Model model){
        log.info("GET /course/new - showing create course page");
        model.addAttribute("courseDTO", new CourseDTO());
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model ){
        log.info("GET /course/list - showing course list page");
        Page<CourseDTO> courses =  courseService.getCoursesByActiveTrue(page, size);
        model.addAttribute("courses", courses);
        return "course";
    }

    @PostMapping
    public String createCourse(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        log.info("POST /course - create course request received");


        if(bindingResult.hasErrors()){
            log.error("POST /course - create course failed due to validation");
            return "add-course";
        }

        if(courseService.existsByCourseCode(courseDTO.getCourseCode())){
            log.error("POST /course - Course code already exist : {}", courseDTO.getCourseCode());
            bindingResult.rejectValue("courseCode",null,"Code must be unique");
            return "add-course";
        }

        courseService.createCourse(courseDTO);
        redirectAttributes.addAttribute("message", "Course is created successfully");
        log.info("POST /course - course created successfully.");
//        return "course";
        return "redirect:/course/list";

    };

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model){
        CourseDTO courseDTO = courseService.getCourseById(id);
        model.addAttribute("course", courseDTO);
        return "view-course";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model){
        CourseDTO courseDTO = courseService.getCourseById(id);
        model.addAttribute("courseDTO", courseDTO);
        return "edit-course";
    }

    @PostMapping("/{id}/update")
    public String updateCourse(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                               @PathVariable Long id,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            log.error("POST /course/{id}/update - update course failed due to validation. {}", id);
            return "edit-course";
        }

        if(courseService.existsByCourseCodeAndIdNot(courseDTO.getCourseCode(), id)){
            log.error("POST /course/{id}/update - Course code already exist : {}", courseDTO.getCourseCode());
            bindingResult.rejectValue("courseCode",null,"Code must be unique");
            return "edit-course";
        }
        courseService.updateCourse(id, courseDTO);
        redirectAttributes.addAttribute("message", "Course is updated successfully");
        log.info("POST /course/{id}/update - course updated successfully.");
//        return "course";
        return "redirect:/course/list";
    }
}

























