package com.ah.studentmanagement.controller;

import com.ah.studentmanagement.dto.CourseDTO;
import com.ah.studentmanagement.dto.StudentDTO;
import com.ah.studentmanagement.service.StudentService;
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
@RequestMapping("/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/new")
    public String showCreateStudent(Model model){
        model.addAttribute("studentDTO", new StudentDTO());
        return "add-student";
    }

    @GetMapping("/list")
    public String listStudents(RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model ){
        Page<StudentDTO> students = studentService.getStudentsByActiveTrue(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", "student is created");
        return "students";
    }

    @PostMapping("/save")
    public String createStudent(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "add-student";
        }

        if(studentService.existsByEmailIgnoreCase(studentDTO.getEmail())){
            bindingResult.rejectValue("email",null,"Email must be unique");
            return "add-student";
        }
        studentService.createStudent(studentDTO);
        redirectAttributes.addAttribute("message", "Student is created successfully");
        return "redirect:/students/list";
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id, Model model){
        StudentDTO studentDTO = studentService.getStudentById(id);
        model.addAttribute("student", studentDTO);
        return "view-student";
    }

    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model){
        StudentDTO studentDTO = studentService.getStudentById(id);
        model.addAttribute("studentDTO", studentDTO);
        return "edit-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
                               @PathVariable Long id,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "edit-student";
        }

        if(studentService.existsByEmailIgnoreCaseAndIdNot(studentDTO.getEmail(), id)){
            bindingResult.rejectValue("email",null,"Email must be unique");
            return "edit-student";
        }

        studentService.updateStudent(id, studentDTO);
        redirectAttributes.addAttribute("message", "Student is updated successfully");
        return "redirect:/students/list";
    }

}

























