package com.digitalcredentials.vo;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.amazonaws.services.qldb.model.ValueHolder;
//Transcript Details
public class CourseVerificationVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private LearnerPIIVO learnerPIIVO;
	private String universityEIN;
	private String universityName;
	private String career;
	private String subject;
	private String concentration;

	private String documentId;
	private String revisionBlockAddress;
	private ValueHolder digestTipAddress;
	private ByteBuffer digest;
	private String revisionBlockStranId;
	private int revisionBlockSequenceNo;
	private byte[] PIIHash;
	private byte[] encryptedPII;
	private String organizationEncryptedPII;
	private ByteBuffer digitalSgnature;
	private String PIIHashString;
	private String encryptedPIIString;

	private String digestString;
	private String digitalSignatureString;
	private String digestTipAddressString;

	private String isCourseRecord = "Y";

	private String isPIIHashMatched;

	private String isDigitalSignatureVerified;

	private String isDataVerified;

	ArrayList<CourseVO> courseList = new <CourseVO>ArrayList();

	public byte[] getEncryptedPII() {
		return encryptedPII;
	}

	public String getIsPIIHashMatched() {
		return isPIIHashMatched;
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

	public String getIsCourseRecord() {
		return isCourseRecord;
	}

	

	public String getOrganizationEncryptedPII() {
		return organizationEncryptedPII;
	}

	public void setOrganizationEncryptedPII(String organizationEncryptedPII) {
		this.organizationEncryptedPII = organizationEncryptedPII;
	}

	public void setIsCourseRecord(String isCourseRecord) {
		this.isCourseRecord = isCourseRecord;
	}

	public String getDigestString() {
		return digestString;
	}

	public void setDigestString(String digestString) {
		this.digestString = digestString;
	}

	public String getDigitalSignatureString() {
		return digitalSignatureString;
	}

	public void setDigitalSignatureString(String digitalSignatureString) {
		this.digitalSignatureString = digitalSignatureString;
	}

	public String getDigestTipAddressString() {
		return digestTipAddressString;
	}

	public void setDigestTipAddressString(String digestTipAddressString) {
		this.digestTipAddressString = digestTipAddressString;
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

	public LearnerPIIVO getLearnerPIIVO() {
		return learnerPIIVO;
	}

	public void setLearnerPIIVO(LearnerPIIVO learnerPIIVO) {
		this.learnerPIIVO = learnerPIIVO;
	}

	public ByteBuffer getDigitalSgnature() {
		return digitalSgnature;
	}

	public void setDigitalSgnature(ByteBuffer digitalSgnature) {
		this.digitalSgnature = digitalSgnature;
	}

	public void setEncryptedPII(byte[] encryptedPII) {
		this.encryptedPII = encryptedPII;
	}

	public ArrayList<CourseVO> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<CourseVO> courseList) {
		this.courseList = courseList;
	}

	public ValueHolder getDigestTipAddress() {
		return digestTipAddress;
	}

	public byte[] getPIIHash() {
		return PIIHash;
	}

	public void setPIIHash(byte[] pIIHash) {
		PIIHash = pIIHash;
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

	private byte[] revisionBlockHash;

	public void setDigestTipAddress(ValueHolder digestTipAddress) {
		this.digestTipAddress = digestTipAddress;
	}

	public byte[] getRevisionBlockHash() {
		return revisionBlockHash;
	}

	public void setRevisionBlockHash(byte[] revisionBlockHash) {
		this.revisionBlockHash = revisionBlockHash;
	}

	public int getRevisionBlockSequenceNo() {
		return revisionBlockSequenceNo;
	}

	public void setRevisionBlockSequenceNo(int revisionBlockSequenceNo) {
		this.revisionBlockSequenceNo = revisionBlockSequenceNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRevisionBlockStranId() {
		return revisionBlockStranId;
	}

	public void setRevisionBlockStranId(String revisionBlockStranId) {
		this.revisionBlockStranId = revisionBlockStranId;
	}

	public String getRevisionBlockAddress() {
		return revisionBlockAddress;
	}

	public void setRevisionBlockAddress(String revisionBlockAddress) {
		this.revisionBlockAddress = revisionBlockAddress;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public ByteBuffer getDigest() {
		return digest;
	}

	public void setDigest(ByteBuffer digest) {
		this.digest = digest;
	}

}
