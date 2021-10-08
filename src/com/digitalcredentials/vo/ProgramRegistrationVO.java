package com.digitalcredentials.vo;

import java.io.Serializable;
//Registration details
public class ProgramRegistrationVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String universityUserId;
	private String universityEIN;
	private String universityName;
	private String career;
	private String subject;
	private String concentration;
	private String startDate;
	private String endDate;
	private String programStatus;
	private String GPA;
	private String learnerId;
	private String SSN;
	private String course;
	private String courseGrade;
	private byte[] PIIHash;

	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCourseGrade() {
		return courseGrade;
	}
	public void setCourseGrade(String courseGrade) {
		this.courseGrade = courseGrade;
	}
	public byte[] getPIIHash() {
		return PIIHash;
	}
	public void setPIIHash(byte[] pIIHash) {
		PIIHash = pIIHash;
	}
	public String getUniversityUserId() {
		return universityUserId;
	}
	public void setUniversityUserId(String universityUserId) {
		this.universityUserId = universityUserId;
	}
	
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getLearnerId() {
		return learnerId;
	}
	public void setLearnerId(String learnerId) {
		this.learnerId = learnerId;
	}
	public String getUniversityEIN() {
		return universityEIN;
	}
	public void setUniversityEIN(String universityEIN) {
		this.universityEIN = universityEIN;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getConcentration() {
		return concentration;
	}
	public void setConcentration(String concentration) {
		this.concentration = concentration;
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
	public String getProgramStatus() {
		return programStatus;
	}
	public void setProgramStatus(String programStatus) {
		this.programStatus = programStatus;
	}
	
	public String getGPA() {
		return GPA;
	}
	public void setGPA(String gPA) {
		GPA = gPA;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}