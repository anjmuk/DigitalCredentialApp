package com.digitalcredentials.vo;

import java.io.Serializable;
import java.nio.ByteBuffer;

import com.amazonaws.services.qldb.model.ValueHolder;
//Verification details
public class VerificationVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String universityEIN;
	private String universityName;
	private String career;
	private String subject;
	private String concentration;
	private String course;
	private String documentId;
	private String revisionBlockAddress;
	private ValueHolder digestTipAddress;
	private ByteBuffer digest;
	private String revisionBlockStranId;
	private int revisionBlockSequenceNo;
	private byte[] PIIHash;
	private String isCourseRecord;

	public ValueHolder getDigestTipAddress() {
		return digestTipAddress;
	}

	public String getIsCourseRecord() {
		return isCourseRecord;
	}

	public void setIsCourseRecord(String isCourseRecord) {
		this.isCourseRecord = isCourseRecord;
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

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
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
