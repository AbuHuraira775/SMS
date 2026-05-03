package com.ah.studentmanagement.dto;

/**
 * @author "Abu Huraira"
 **/
public class DashboardStatsDTO {
    private long totalStudents;
    private long totalCourses;
    private long studentsEnrolledThisMonth;
    private String topPerformingCourse;

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getStudentsEnrolledThisMonth() {
        return studentsEnrolledThisMonth;
    }

    public void setStudentsEnrolledThisMonth(long studentsEnrolledThisMonth) {
        this.studentsEnrolledThisMonth = studentsEnrolledThisMonth;
    }

    public String getTopPerformingCourse() {
        return topPerformingCourse;
    }

    public void setTopPerformingCourse(String topPerformingCourse) {
        this.topPerformingCourse = topPerformingCourse;
    }
}
