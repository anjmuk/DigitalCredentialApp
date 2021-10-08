package com.digitalcredentials.vo;

import java.io.Serializable;
//Learner unmodifiable PII
public class LearnerIdVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String SSN;
	
	private String givenName;
	private String lastName;
	
	private String learnerDOB;

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
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
