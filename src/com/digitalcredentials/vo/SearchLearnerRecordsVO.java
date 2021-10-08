package com.digitalcredentials.vo;

import java.io.Serializable;
//Learner record search
public class SearchLearnerRecordsVO implements Serializable {

	private static final long serialVersionUID = 1L;


	private String universityEIN;
	private String organizationEIN;
	private String userId;
	private String userName;
	private String career;
	private String subject;
	private String concentration;
	private String course;
	private String learnerId;
	private String grantId;
	
	public String getUniversityEIN() {
		return universityEIN;
	}
	public String getCourse() {
		return course;
	}
	
	
	public String getGrantId() {
		return grantId;
	}
	public void setGrantId(String grantId) {
		this.grantId = grantId;
	}
	public String getOrganizationEIN() {
		return organizationEIN;
	}
	public void setOrganizationEIN(String organizationEIN) {
		this.organizationEIN = organizationEIN;
	}
	public String getLearnerId() {
		return learnerId;
	}
	public void setLearnerId(String learnerId) {
		this.learnerId = learnerId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public void setUniversityEIN(String universityEIN) {
		this.universityEIN = universityEIN;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
		
}
