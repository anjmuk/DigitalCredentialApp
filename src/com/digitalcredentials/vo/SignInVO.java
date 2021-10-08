package com.digitalcredentials.vo;
// Sign in info
public class SignInVO {
	private String userId;
	private String password;
	private String name;
	private String address;
	private String EIN;
	private String SSN;
	private String email;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getUserId() {
		return userId;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
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

	public String getEIN() {
		return EIN;
	}

	public void setEIN(String eIN) {
		EIN = eIN;
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

}
