package com.digitalcredentials.vo;

import java.io.Serializable;
// Registration Details with verification info
public class ProgramRegistrationBlockVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String universityEIN;
	private String universityName;
	private String career;
	private String subject;
	private String concentration;
	private String startDate;
	private String endDate;
	private String programStatus;
	private String degreeClass;
	private String GPA;
	private String course;
	private String courseGrade;
	private byte[] PIIHash;
	private byte[] encryptedPII;
	private String PIIHashString;
	private String encryptedPIIString;
	private String userId;
	
	public String getPIIHashString() {
		return PIIHashString;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public void setPIIHashString(String pIIHashString) {
		PIIHashString = pIIHashString;
	}

	public String getEncryptedPIIString() {
		return encryptedPIIString;
	}

	public void setEncryptedPIIString(String encryptedPIIString) {
		this.encryptedPIIString = encryptedPIIString;
	}

	public byte[] getEncryptedPII() {
		return encryptedPII;
	}

	public void setEncryptedPII(byte[] encryptedPII) {
		this.encryptedPII = encryptedPII;
	}
	
	public String getGPA() {
		return GPA;
	}

	public void setGPA(String gPA) {
		GPA = gPA;
	}

	public String getDegreeClass() {
		return degreeClass;
	}

	public void setDegreeClass(String degreeClass) {
		this.degreeClass = degreeClass;
	}

	public byte[] getPIIHash() {
		return PIIHash;
	}

	public void setPIIHash(byte[] pIIHash) {
		PIIHash = pIIHash;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
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



}