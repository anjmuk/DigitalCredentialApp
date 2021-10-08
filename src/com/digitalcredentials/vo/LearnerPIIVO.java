package com.digitalcredentials.vo;

import java.io.Serializable;
//Learner PII
public class LearnerPIIVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String SSN;
	private String learnerID;
	private String givenName;
	private String lastName;
	private String address;
	private String email;
	private String phone;
	private String learnerDOB;
	private String userId;
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getLearnerID() {
		return learnerID;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setLearnerID(String learnerID) {
		this.learnerID = learnerID;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLearnerDOB() {
		return learnerDOB;
	}
	public void setLearnerDOB(String learnerDOB) {
		this.learnerDOB = learnerDOB;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
