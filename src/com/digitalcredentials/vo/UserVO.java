package com.digitalcredentials.vo;
//User details
public class UserVO {
	private String name;
	private String givenName;
	private String lastName;
	private String address;
	private String EIN;
	private String SSN;
	private String email;
	private String userId;
	private String password;
	private String phone;
	private String gender;
	private String learnerDOB;
	private byte[] ssnHash;
	private byte[] encryptedSSN;


	public byte[] getSsnHash() {
		return ssnHash;
	}

	public void setSsnHash(byte[] ssnHash) {
		this.ssnHash = ssnHash;
	}

	public byte[] getEncryptedSSN() {
		return encryptedSSN;
	}

	public String getLearnerDOB() {
		return learnerDOB;
	}

	public void setLearnerDOB(String learnerDOB) {
		this.learnerDOB = learnerDOB;
	}

	public void setEncryptedSSN(byte[] encryptedSSN) {
		this.encryptedSSN = encryptedSSN;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public void setLatName(String lastName) {
		this.lastName = lastName;
	}

	public String getSSN() {
		return SSN;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getName() {
		return name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEIN() {
		return EIN;
	}

	public void setEIN(String eIN) {
		EIN = eIN;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
