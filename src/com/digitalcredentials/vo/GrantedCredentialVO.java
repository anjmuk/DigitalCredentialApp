package com.digitalcredentials.vo;

import java.io.Serializable;
//Granted Certificate Details
public class GrantedCredentialVO implements Serializable{
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private static final long serialVersionUID = 1L;
	private String id;
	private String universityEIN;
	private String organizationEIN;
	private String career;
	private String subject;
	private String concentration;
	private byte[] PIIHash;
	private byte[] organizationEncryptedPII;
	private String course;
	private String credentialType;

	
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	public String getOrganizationEIN() {
		return organizationEIN;
	}
	public void setOrganizationEIN(String organizationEIN) {
		this.organizationEIN = organizationEIN;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUniversityEIN() {
		return universityEIN;
	}
	public void setUniversityEIN(String universityEIN) {
		this.universityEIN = universityEIN;
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
	public byte[] getPIIHash() {
		return PIIHash;
	}
	public void setPIIHash(byte[] pIIHash) {
		PIIHash = pIIHash;
	}
	public byte[] getOrganizationEncryptedPII() {
		return organizationEncryptedPII;
	}
	public void setOrganizationEncryptedPII(byte[] organizationEncryptedPII) {
		this.organizationEncryptedPII = organizationEncryptedPII;
	}

}
