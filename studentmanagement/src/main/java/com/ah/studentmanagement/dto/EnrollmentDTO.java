package com.ah.studentmanagement.dto;

import com.ah.studentmanagement.entity.Students;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author "Abu Huraira"
 **/
public class EnrollmentDTO {

    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotEmpty(message = "Select at least one course")
    private List<Long> courseIds;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
