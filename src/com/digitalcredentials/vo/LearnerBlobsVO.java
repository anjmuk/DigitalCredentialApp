package com.digitalcredentials.vo;
//Learner verification info
public class LearnerBlobsVO {
	private byte[] PIIHash;
	private byte[] encryptedPII;
	private byte[] digitalSignature;
	public byte[] getPIIHash() {
		return PIIHash;
	}
	public void setPIIHash(byte[] pIIHash) {
		PIIHash = pIIHash;
	}
	public byte[] getEncryptedPII() {
		return encryptedPII;
	}
	public void setEncryptedPII(byte[] encryptedPII) {
		this.encryptedPII = encryptedPII;
	}
	public byte[] getDigitalSignature() {
		return digitalSignature;
	}
	public void setDigitalSignature(byte[] digitalSignature) {
		this.digitalSignature = digitalSignature;
	}
	
	
}
