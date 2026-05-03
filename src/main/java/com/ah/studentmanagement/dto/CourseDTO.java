package com.ah.studentmanagement.dto;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author "Abu Huraira"
 **/
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course name is required.")
    @Size(max=150, message="Max of 150 characters allowed")
    private String courseName;

    @Size(max=500, message="Max of 500 characters allowed")
    private String courseDescription;

    @NotBlank(message = "Course Code is required.")
    private String courseCode;

    @NotBlank(message = "Course duration is required.")
    private String duration;

    private boolean active;

    @NotNull(message = "Course fee is required.")
    private BigDecimal courseFee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(BigDecimal courseFee) {
        this.courseFee = courseFee;
    }
}
