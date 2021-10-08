package com.digitalcredentials.vo;

import java.io.Serializable;
// Certificate info
public class CompleteRegistrationVO implements Serializable {
	private LearnerPIIVO learnerPIIVO;
	private ProgramRegistrationBlockVO programRegistrationBlockVO;

	private String PIIHashString;
	private String encryptedPIIString;
	private String isPIIHashMatched;
	private String digest;
	private String digitalSignatureString;
	private String isDigitalSignatureVerified;
	private String documentId;
	private String revisionBlockAddress;
	private String digestTipAddress;
	private String isDataVerified;
	private String updatedEndDate;
	private String updatedDegreeClass;
	private String updatedProgramStatus;
	private String updatedGPA;
	public String getIsPIIHashMatched() {
		return isPIIHashMatched;
	}

	public String getUpdatedGPA() {
		return updatedGPA;
	}

	public void setUpdatedGPA(String updatedGPA) {
		this.updatedGPA = updatedGPA;
	}

	public String getUpdatedEndDate() {
		return updatedEndDate;
	}

	public void setUpdatedEndDate(String updatedEndDate) {
		this.updatedEndDate = updatedEndDate;
	}

	public String getUpdatedDegreeClass() {
		return updatedDegreeClass;
	}

	public void setUpdatedDegreeClass(String updatedDegreeClass) {
		this.updatedDegreeClass = updatedDegreeClass;
	}

	public String getUpdatedProgramStatus() {
		return updatedProgramStatus;
	}

	public void setUpdatedProgramStatus(String updatedProgramStatus) {
		this.updatedProgramStatus = updatedProgramStatus;
	}

	public void setIsPIIHashMatched(String isPIIHashMatched) {
		this.isPIIHashMatched = isPIIHashMatched;
	}

	public String getIsDigitalSignatureVerified() {
		return isDigitalSignatureVerified;
	}

	public void setIsDigitalSignatureVerified(String isDigitalSignatureVerified) {
		this.isDigitalSignatureVerified = isDigitalSignatureVerified;
	}

	public String getIsDataVerified() {
		return isDataVerified;
	}

	public void setIsDataVerified(String isDataVerified) {
		this.isDataVerified = isDataVerified;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getRevisionBlockAddress() {
		return revisionBlockAddress;
	}

	public void setRevisionBlockAddress(String revisionBlockAddress) {
		this.revisionBlockAddress = revisionBlockAddress;
	}

	public String getDigestTipAddress() {
		return digestTipAddress;
	}

	public void setDigestTipAddress(String digestTipAddress) {
		this.digestTipAddress = digestTipAddress;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public LearnerPIIVO getLearnerPIIVO() {
		return learnerPIIVO;
	}

	public void setLearnerPIIVO(LearnerPIIVO learnerPIIVO) {
		this.learnerPIIVO = learnerPIIVO;
	}

	public ProgramRegistrationBlockVO getProgramRegistrationBlockVO() {
		return programRegistrationBlockVO;
	}

	public void setProgramRegistrationBlockVO(ProgramRegistrationBlockVO programRegistrationBlockVO) {
		this.programRegistrationBlockVO = programRegistrationBlockVO;
	}

	public String getPIIHashString() {
		return PIIHashString;
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

	public String getDigitalSignatureString() {
		return digitalSignatureString;
	}

	public void setDigitalSignatureString(String digitalSignatureString) {
		this.digitalSignatureString = digitalSignatureString;
	}

}
