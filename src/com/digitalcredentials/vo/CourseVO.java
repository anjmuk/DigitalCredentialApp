package com.digitalcredentials.vo;

import java.io.Serializable;
//Course info
public class CourseVO implements Serializable {
	private String course;
	private String startDate;
	private String endDate;
	private String cumulativeGPA;
	private String courseGrade;
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCumulativeGPA() {
		return cumulativeGPA;
	}
	public void setCumulativeGPA(String cumulativeGPA) {
		this.cumulativeGPA = cumulativeGPA;
	}
	public String getCourseGrade() {
		return courseGrade;
	}
	public void setCourseGrade(String courseGrade) {
		this.courseGrade = courseGrade;
	}

}
