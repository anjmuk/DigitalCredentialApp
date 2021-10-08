package com.digitalcredentials.dao;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import com.amazon.ion.*;
import com.amazon.ion.system.*;
import software.amazon.awssdk.services.qldbsession.QldbSessionClient;
import software.amazon.qldb.*;

import com.amazon.ion.system.IonSystemBuilder;
import com.amazonaws.services.qldb.AmazonQLDB;
import com.amazonaws.services.qldb.AmazonQLDBClientBuilder;
import com.amazonaws.services.qldb.model.GetDigestRequest;
import com.amazonaws.services.qldb.model.GetDigestResult;
import com.amazonaws.services.qldb.model.GetRevisionRequest;
import com.amazonaws.services.qldb.model.GetRevisionResult;
import com.amazonaws.services.qldb.model.ValueHolder;
import com.amazonaws.client.builder.AwsClientBuilder;
import software.amazon.qldb.QldbDriver;
import software.amazon.qldb.Result;

import com.digitalcredentials.vo.CourseListVO;
import com.digitalcredentials.vo.CourseVO;
import com.digitalcredentials.vo.CourseVerificationVO;
import com.digitalcredentials.vo.GrantedCredentialVO;
import com.digitalcredentials.vo.LearnerBlobsVO;
import com.digitalcredentials.vo.LearnerPIIVO;
import com.digitalcredentials.vo.ProgramRegistrationBlockVO;
import com.digitalcredentials.vo.SignedRegistrationVO;
import com.digitalcredentials.vo.UserVO;
import com.digitalcredentials.vo.VerificationVO;

public class DigitalCredentialDAO {
	public static IonSystem ionSys = IonSystemBuilder.standard().build();
	public static QldbDriver qldbDriver;
	// QLDB Ledger Name
	public static String ledgerName = "credentialDB";
	// AWS Endpoint
	public static String endpoint = "qldb.us-east-1.amazonaws.com";
	// AWS Region
	public static String region = "us-east-1";
	public static AmazonQLDB client = getClient();

	public DigitalCredentialDAO() {
		super();
		// Connect to QLDB
		System.out.println("Initializing the driver");
		qldbDriver = QldbDriver.builder().ledger(ledgerName)
				.transactionRetryPolicy(RetryPolicy.builder().maxRetries(3).build())
				.sessionClientBuilder(QldbSessionClient.builder()).build();
	}

	// Get QLDB Client
	public static AmazonQLDB getClient() {
		AmazonQLDBClientBuilder builder = AmazonQLDBClientBuilder.standard();
		if (null != endpoint && null != region) {
			builder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region));
		}
		return builder.build();
	}

	// Get QLDB Digest for the ledger
	public static GetDigestResult getDigest() {

		GetDigestRequest request = new GetDigestRequest().withName(ledgerName);
		GetDigestResult result = client.getDigest(request);
		return result;
	}

	// Add university
	public void addIssuingUniversity(UserVO userVO) {
		String universityName = userVO.getName().trim();
		String address = userVO.getAddress().trim();
		String EIN = userVO.getEIN().trim();
		String email = userVO.getEmail().trim();
		String phone = userVO.getPhone().trim();
		String userId = userVO.getUserId().trim();

		IonStruct universityRegistration = ionSys.newEmptyStruct();
		universityRegistration.put("universityName").newString(universityName);
		universityRegistration.put("address").newString(address);
		universityRegistration.put("EIN").newString(EIN);
		universityRegistration.put("email").newString(email);
		universityRegistration.put("phone").newString(phone);
		universityRegistration.put("userId").newString(userId);

		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO IssuingUniversity ?", universityRegistration);

		});

	}

	// Add Organization

	public void addOrganization(UserVO userVO) {
		String universityName = userVO.getName().trim();
		String address = userVO.getAddress().trim();
		String EIN = userVO.getEIN().trim();
		String email = userVO.getEmail().trim();
		String phone = userVO.getPhone().trim();
		String userId = userVO.getUserId().trim();

		IonStruct organizationRegistration = ionSys.newEmptyStruct();
		organizationRegistration.put("organizationName").newString(universityName);
		organizationRegistration.put("address").newString(address);
		organizationRegistration.put("EIN").newString(EIN);
		organizationRegistration.put("email").newString(email);
		organizationRegistration.put("phone").newString(phone);
		// organizationRegistration.put("userId").newString(userId);

		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO OrganizationRegistration  ?", organizationRegistration);

		});

	}

	// Create encrypted PII for an organization granting transcript access
	public void addOrganizationTranscriptPII(String id, String organizationEIN, String universityEIN,
			byte[] organizationEncryptedPII, byte[] learnerPIIHash, String career, String subject,
			String concentration) {

		IonStruct organizationCertificatePII = ionSys.newEmptyStruct();
		organizationCertificatePII.put("id").newString(id.trim());
		organizationCertificatePII.put("organizationEIN").newString(organizationEIN.trim());
		organizationCertificatePII.put("universityEIN").newString(universityEIN.trim());

		organizationCertificatePII.put("encryptedPII").newBlob(organizationEncryptedPII);
		organizationCertificatePII.put("learnerPIIHash").newBlob(learnerPIIHash);
		organizationCertificatePII.put("career").newString(career.trim());
		organizationCertificatePII.put("concentration").newString(concentration.trim());
		organizationCertificatePII.put("subject").newString(subject.trim());

		organizationCertificatePII.put("credentialType").newString("Transcript");

		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO OrganizationCertificatePII ?", organizationCertificatePII);

		});

	}

	// Create encrypted PII for an organization granting certificate access
	public void addOrganizationPII(String id, String organizationEIN, String universityEIN,
			byte[] organizationEncryptedPII, byte[] learnerPIIHash, String career, String subject,
			String concentration) {

		IonStruct organizationCertificatePII = ionSys.newEmptyStruct();
		organizationCertificatePII.put("id").newString(id.trim());
		organizationCertificatePII.put("organizationEIN").newString(organizationEIN.trim());
		organizationCertificatePII.put("universityEIN").newString(universityEIN.trim());

		organizationCertificatePII.put("encryptedPII").newBlob(organizationEncryptedPII);
		organizationCertificatePII.put("learnerPIIHash").newBlob(learnerPIIHash);
		organizationCertificatePII.put("career").newString(career.trim());
		organizationCertificatePII.put("concentration").newString(concentration.trim());
		organizationCertificatePII.put("subject").newString(subject.trim());

		organizationCertificatePII.put("credentialType").newString("Degree");

		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO OrganizationCertificatePII ?", organizationCertificatePII);

		});

	}

	// Add learner record
	public void addLearner(UserVO userVO) {

		String SSN = userVO.getSSN().trim();

		String userId = userVO.getUserId().trim();

		IonStruct learner = ionSys.newEmptyStruct();

		learner.put("ssnHash").newBlob(createHash(SSN));
		learner.put("userId").newString(userId);

		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO Learner ?", learner);

		});

	}

	// Add learner record
	public void addLearner1(UserVO userVO) {
		String firstName = userVO.getGivenName().trim();
		String lastName = userVO.getLastName().trim();
		String address = userVO.getAddress().trim();
		String SSN = userVO.getSSN().trim();
		String email = userVO.getEmail().trim();
		String phone = userVO.getPhone().trim();
		String userId = userVO.getUserId().trim();
		String learnerDOB = userVO.getLearnerDOB().trim();

		IonStruct learner = ionSys.newEmptyStruct();
		learner.put("firstName").newString(firstName);
		learner.put("lastName").newString(lastName);
		learner.put("address").newString(address);
		learner.put("SSN").newString(SSN);
		learner.put("email").newString(email);
		learner.put("phone").newString(phone);
		learner.put("learnerDOB").newString(learnerDOB);
		learner.put("ssnHash").newBlob(userVO.getSsnHash());
		learner.put("encryptedSSN").newBlob(userVO.getEncryptedSSN());

		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO Learner ?", learner);

		});

	}

	// Get Learner Record
	public LearnerPIIVO getLearnerInfo1(String SSN) {
		LearnerPIIVO learnerPIIVO = new LearnerPIIVO();

		IonStruct queryLearner = ionSys.newEmptyStruct();
		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select firstName,lastName,address,email, phone, learnerDOB from Learner where SSN = ?",
					ionSys.newString(SSN.trim()));

			IonStruct learner = (IonStruct) result.iterator().next();

			learnerPIIVO.setGivenName(((IonString) learner.get("firstName")).stringValue());
			learnerPIIVO.setLastName(((IonString) learner.get("lastName")).stringValue());
			learnerPIIVO.setAddress(((IonString) learner.get("address")).stringValue());

			learnerPIIVO.setEmail(((IonString) learner.get("email")).stringValue());
			learnerPIIVO.setPhone(((IonString) learner.get("phone")).stringValue());
			learnerPIIVO.setLearnerDOB(((IonString) learner.get("learnerDOB")).stringValue());

			learnerPIIVO.setSSN(SSN.trim());

		});
		return learnerPIIVO;
	}

	// Get Learner Record
	public LearnerPIIVO getLearnerInfo(byte[] ssnHash) {
		LearnerPIIVO learnerPIIVO = new LearnerPIIVO();
		String user = null;
		IonStruct queryLearner = ionSys.newEmptyStruct();
		qldbDriver.execute(txn -> {

			Result result = txn.execute("Select userId from Learner where ssnHash = ?", ionSys.newBlob(ssnHash));

			IonStruct learner = (IonStruct) result.iterator().next();

			learnerPIIVO.setUserId(((IonString) (learner.get("userId"))).stringValue());

		});
		return learnerPIIVO;
	}

	// Get Learner Record
	public LearnerPIIVO getLearnerInfo2(byte[] ssnHash) {
		LearnerPIIVO learnerPIIVO = new LearnerPIIVO();

		IonStruct queryLearner = ionSys.newEmptyStruct();
		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select firstName,lastName,address,email, phone, learnerDOB from Learner where ssnHash = ?",
					ionSys.newBlob(ssnHash));

			IonStruct learner = (IonStruct) result.iterator().next();

			learnerPIIVO.setGivenName(((IonString) learner.get("firstName")).stringValue());
			learnerPIIVO.setLastName(((IonString) learner.get("lastName")).stringValue());
			learnerPIIVO.setAddress(((IonString) learner.get("address")).stringValue());

			learnerPIIVO.setEmail(((IonString) learner.get("email")).stringValue());
			learnerPIIVO.setPhone(((IonString) learner.get("phone")).stringValue());
			learnerPIIVO.setLearnerDOB(((IonString) learner.get("learnerDOB")).stringValue());

		});
		return learnerPIIVO;
	}

	// Get course record
	public CourseVerificationVO getCourseListInfo(String EIN, byte[] PIIHash, String career, String subject,
			String concentration) {

		CourseVerificationVO courseVerificationVO = new CourseVerificationVO();

		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"Select * from _ql_committed_CourseListInfo AS r where r.data.EIN = ? AND r.data.PIIHash = ? AND r.data.career = ? AND r.data.subject = ? AND r.data.concentration = ? ",
					ionSys.newString(EIN), ionSys.newBlob(PIIHash), ionSys.newString(career), ionSys.newString(subject),
					ionSys.newString(concentration));

			Iterator iterator = result.iterator();
			IonStruct courseRecords = null;

			courseRecords = (IonStruct) (iterator.next());

			IonStruct data = (IonStruct) courseRecords.get("data");

			courseVerificationVO.setUniversityEIN(((IonString) data.get("EIN")).stringValue());
			courseVerificationVO.setUniversityName(((IonString) data.get("universityName")).stringValue());
			courseVerificationVO.setConcentration(((IonString) data.get("concentration")).stringValue());
			courseVerificationVO.setSubject(((IonString) data.get("subject")).stringValue());
			courseVerificationVO.setCareer(((IonString) data.get("career")).stringValue());
			courseVerificationVO.setPIIHash(((IonBlob) data.get("PIIHash")).getBytes());
			courseVerificationVO.setEncryptedPII(((IonBlob) data.get("encryptedPII")).getBytes());

			// Populate QLDB block details
			String revisionBlockAddress = courseRecords.get("blockAddress").toString();
			courseVerificationVO.setRevisionBlockAddress(revisionBlockAddress);
			courseVerificationVO.setRevisionBlockHash(((IonBlob) courseRecords.get("hash")).getBytes());
			IonStruct revisionBlockStruct = (IonStruct) courseRecords.get("blockAddress");
			courseVerificationVO.setRevisionBlockStranId(revisionBlockStruct.get("strandId").toString());
			courseVerificationVO
					.setRevisionBlockSequenceNo(Integer.parseInt(revisionBlockStruct.get("sequenceNo").toString()));
			IonStruct metadata = (IonStruct) courseRecords.get("metadata");
			String documentId = metadata.get("id").toString();
			courseVerificationVO.setDocumentId(documentId);

			IonStruct courseStructure = (IonStruct) data.get("courseStructure");
			if (courseStructure == null)
				{
				throw new NoSuchElementException();
			
				}
			IonList courseList = (IonList) courseStructure.get("courseList");

			for (int i = 0; i < courseList.size(); ++i) {
				String courseStr = ((IonValue) (courseList.get(i))).toString();
				List<String> listStrings = getTokens(courseStr.substring(2, courseStr.length() - 2), ",");

				CourseVO course = new CourseVO();

				List<String> courseValues = getTokens(listStrings.get(0), ":");
				course.setCourse(courseValues.get(1));
				List<String> startValues = getTokens(listStrings.get(1), ":");
				course.setStartDate(startValues.get(1));
				List<String> endValues = getTokens(listStrings.get(2), ":");
				course.setEndDate(endValues.get(1));
				List<String> gradevalues = getTokens(listStrings.get(3), ":");
				course.setCourseGrade(gradevalues.get(1));
				List<String> gpaValues = getTokens(listStrings.get(4), ":");
				course.setCumulativeGPA(gpaValues.get(1));

				courseVerificationVO.getCourseList().add(course);
			}
			// Get verification details for the course using document id and block address
			// for the course
			Result result1 = txn.execute(
					"Select * from Verification AS r where r.docId = ? AND r.revisionBlockAddress = ?  ",
					ionSys.newString(courseVerificationVO.getDocumentId()),
					ionSys.newString(courseVerificationVO.getRevisionBlockAddress()));

			IonStruct signedRegistrationRec = (IonStruct) result1.iterator().next();
			String tipAddress = signedRegistrationRec.get("digestTipAddress").toString();
			ValueHolder digestTipAddress = new ValueHolder().withIonText(tipAddress);
			courseVerificationVO.setDigestTipAddress(digestTipAddress);
			courseVerificationVO.setDigest(ByteBuffer.wrap(((IonBlob) signedRegistrationRec.get("digest")).getBytes()));
			courseVerificationVO.setDigitalSgnature(
					ByteBuffer.wrap(((IonBlob) signedRegistrationRec.get("universityDigitalSignature")).getBytes()));

		});
		/*
		 * qldbDriver.execute(txn -> { Result result = txn.execute(
		 * "Select * from Verification AS r where r.docId = ? AND r.revisionBlockAddress = ?  "
		 * , ionSys.newString(courseVerificationVO.getDocumentId()),
		 * ionSys.newString(courseVerificationVO.getRevisionBlockAddress()));
		 * 
		 * IonStruct signedRegistrationRec = (IonStruct) result.iterator().next();
		 * String tipAddress = signedRegistrationRec.get("digestTipAddress").toString();
		 * ValueHolder digestTipAddress = new ValueHolder().withIonText(tipAddress);
		 * courseVerificationVO.setDigestTipAddress(digestTipAddress);
		 * courseVerificationVO.setDigest(ByteBuffer.wrap(((IonBlob)
		 * signedRegistrationRec.get("digest")).getBytes()));
		 * courseVerificationVO.setDigitalSgnature( ByteBuffer.wrap(((IonBlob)
		 * signedRegistrationRec.get("universityDigitalSignature")).getBytes()));
		 * 
		 * });
		 */

		return courseVerificationVO;
	}

	// Get Registration Record
	public SignedRegistrationVO getCareerRegistration(String EIN, byte[] PIIHash, String career, String subject,
			String concentration) {
		SignedRegistrationVO signedRegistrationVO = new SignedRegistrationVO();
		VerificationVO verificationVO = new VerificationVO();

		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"Select * from _ql_committed_CareerRegistration AS r where r.data.EIN = ? AND r.data.PIIHash = ? AND r.data.career = ? AND r.data.subject = ? AND r.data.concentration = ?  ",
					ionSys.newString(EIN), ionSys.newBlob(PIIHash), ionSys.newString(career), ionSys.newString(subject),
					ionSys.newString(concentration));

			Iterator iterator = result.iterator();

			IonStruct signedRegistration = (IonStruct) (iterator.next());

			ProgramRegistrationBlockVO programRegistrationBlockVO = new ProgramRegistrationBlockVO();
			String revisionBlockAddress = signedRegistration.get("blockAddress").toString();

			verificationVO.setRevisionBlockAddress(revisionBlockAddress);
			verificationVO.setRevisionBlockHash(((IonBlob) signedRegistration.get("hash")).getBytes());
			IonStruct revisionBlockStruct = (IonStruct) signedRegistration.get("blockAddress");

			verificationVO.setRevisionBlockStranId(revisionBlockStruct.get("strandId").toString());
			verificationVO
					.setRevisionBlockSequenceNo(Integer.parseInt(revisionBlockStruct.get("sequenceNo").toString()));

			IonStruct data = (IonStruct) signedRegistration.get("data");

			programRegistrationBlockVO.setConcentration(((IonString) data.get("concentration")).stringValue());

			programRegistrationBlockVO.setPIIHash(((IonBlob) data.get("PIIHash")).getBytes());

			programRegistrationBlockVO.setCareer(((IonString) data.get("career")).stringValue());

			programRegistrationBlockVO.setConcentration(((IonString) data.get("concentration")).stringValue());
			programRegistrationBlockVO.setDegreeClass(((IonString) data.get("degreeClass")).stringValue());
			programRegistrationBlockVO.setEndDate(((IonString) data.get("endDate")).stringValue());

			programRegistrationBlockVO.setStartDate(((IonString) data.get("startDate")).stringValue());
			programRegistrationBlockVO.setGPA(((IonString) (data.get("gpa"))).stringValue());
			programRegistrationBlockVO.setProgramStatus(((IonString) data.get("programStatus")).stringValue());

			programRegistrationBlockVO.setSubject(((IonString) data.get("subject")).stringValue());
			programRegistrationBlockVO.setUniversityEIN(((IonString) data.get("EIN")).stringValue());
			programRegistrationBlockVO.setUniversityName(((IonString) data.get("universityName")).stringValue());
			programRegistrationBlockVO.setEncryptedPII(((IonBlob) data.get("encryptedPII")).getBytes());
			signedRegistrationVO.setProgramRegistrationBlockVO(programRegistrationBlockVO);
			IonStruct metadata = (IonStruct) signedRegistration.get("metadata");

			String documentId = metadata.get("id").toString();

			verificationVO.setDocumentId(documentId);
			// Get verification details using registration document id and block address
			qldbDriver.execute(txn1 -> {
				Result result1 = txn1.execute(
						"Select * from Verification AS r where r.docId = ? AND r.revisionBlockAddress = ?  ",
						ionSys.newString(verificationVO.getDocumentId()),
						ionSys.newString(verificationVO.getRevisionBlockAddress()));

				IonStruct signedRegistrationRec = (IonStruct) result1.iterator().next();
				String tipAddress = signedRegistrationRec.get("digestTipAddress").toString();
				ValueHolder digestTipAddress = new ValueHolder().withIonText(tipAddress);
				verificationVO.setDigestTipAddress(digestTipAddress);
				verificationVO.setDigest(ByteBuffer.wrap(((IonBlob) signedRegistrationRec.get("digest")).getBytes()));
				signedRegistrationVO.setDigitalSgnature(ByteBuffer
						.wrap(((IonBlob) signedRegistrationRec.get("universityDigitalSignature")).getBytes()));

			});
			signedRegistrationVO.setVerificationVO(verificationVO);

		});
		return signedRegistrationVO;
	}

	// Get all the granted certificates to an organization
	public ArrayList<GrantedCredentialVO> getOrganizationGrantedCertificates1(String organizationEIN) {

		ArrayList<GrantedCredentialVO> grantedCredentialVOList = new ArrayList<GrantedCredentialVO>();

		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"Select * from OrganizationCertificatePII AS r where r.organizationEIN = ? and r.credentialType ='Degree' ",
					ionSys.newString(organizationEIN.trim()));

			Iterator<IonValue> iterator = result.iterator();
			if (iterator.hasNext()) {
				System.out.println("ENTERING");
				IonStruct grantedCredential = (IonStruct) (iterator.next());
				GrantedCredentialVO grantedCredentialVO = new GrantedCredentialVO();

				grantedCredentialVO.setId(((IonString) grantedCredential.get("id")).stringValue());
				System.out.println("Grant idddd " + grantedCredentialVO.getId());
				grantedCredentialVO
						.setOrganizationEIN(((IonString) grantedCredential.get("organizationEIN")).stringValue());
				grantedCredentialVO
						.setUniversityEIN(((IonString) grantedCredential.get("universityEIN")).stringValue());
				grantedCredentialVO.setSubject(((IonString) grantedCredential.get("subject")).stringValue());
				grantedCredentialVO.setCareer(((IonString) grantedCredential.get("career")).stringValue());
				grantedCredentialVO
						.setConcentration(((IonString) grantedCredential.get("concentration")).stringValue());

				grantedCredentialVO
						.setCredentialType(((IonString) grantedCredential.get("credentialType")).stringValue());

				grantedCredentialVOList.add(grantedCredentialVO);

			}
		});
		return grantedCredentialVOList;
	}

	public ArrayList<GrantedCredentialVO> getOrganizationGrantedCertificates(String organizationEIN) {

		ArrayList<GrantedCredentialVO> grantedCredentialVOList = new ArrayList<GrantedCredentialVO>();

		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"Select * from history(OrganizationCertificatePII) AS r where r.data.organizationEIN = ? and r.data.credentialType ='Degree' ",
					ionSys.newString(organizationEIN.trim()));

			Iterator<IonValue> iterator = result.iterator();
			if (iterator.hasNext()) {

				IonStruct credentialRecord = (IonStruct) (iterator.next());
				IonStruct grantedCredential = (IonStruct) credentialRecord.get("data");
				GrantedCredentialVO grantedCredentialVO = new GrantedCredentialVO();

				grantedCredentialVO.setId(((IonString) grantedCredential.get("id")).stringValue());

				grantedCredentialVO
						.setOrganizationEIN(((IonString) grantedCredential.get("organizationEIN")).stringValue());
				grantedCredentialVO
						.setUniversityEIN(((IonString) grantedCredential.get("universityEIN")).stringValue());
				grantedCredentialVO.setSubject(((IonString) grantedCredential.get("subject")).stringValue());
				grantedCredentialVO.setCareer(((IonString) grantedCredential.get("career")).stringValue());
				grantedCredentialVO
						.setConcentration(((IonString) grantedCredential.get("concentration")).stringValue());

				grantedCredentialVO
						.setCredentialType(((IonString) grantedCredential.get("credentialType")).stringValue());

				grantedCredentialVOList.add(grantedCredentialVO);

			}
		});
		return grantedCredentialVOList;
	}

	// Get all the granted transcripts to an organization
	public ArrayList<GrantedCredentialVO> getOrganizationGrantedTranscripts(String organizationEIN) {

		ArrayList<GrantedCredentialVO> grantedCredentialVOList = new ArrayList<GrantedCredentialVO>();

		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"Select * from OrganizationCertificatePII AS r where r.organizationEIN = ? and r.credentialType ='Transcript'  ",
					ionSys.newString(organizationEIN));

			Iterator iterator = result.iterator();
			if (iterator.hasNext()) {

				IonStruct grantedCredential = (IonStruct) (iterator.next());
				GrantedCredentialVO grantedCredentialVO = new GrantedCredentialVO();

				grantedCredentialVO.setId(((IonString) grantedCredential.get("id")).stringValue());
				grantedCredentialVO
						.setOrganizationEIN(((IonString) grantedCredential.get("organizationEIN")).stringValue());
				grantedCredentialVO
						.setUniversityEIN(((IonString) grantedCredential.get("universityEIN")).stringValue());
				grantedCredentialVO.setSubject(((IonString) grantedCredential.get("subject")).stringValue());
				grantedCredentialVO.setCareer(((IonString) grantedCredential.get("career")).stringValue());
				grantedCredentialVO
						.setConcentration(((IonString) grantedCredential.get("concentration")).stringValue());

				grantedCredentialVO
						.setCredentialType(((IonString) grantedCredential.get("credentialType")).stringValue());

				grantedCredentialVOList.add(grantedCredentialVO);

			}
		});
		return grantedCredentialVOList;
	}

	// Get a certificated for a grant id
	public GrantedCredentialVO findGrantedCertificate(String grantId) {

		GrantedCredentialVO credentialDetails = new GrantedCredentialVO();

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select universityEIN, encryptedPII, learnerPIIHash, career,subject,concentration from OrganizationCertificatePII where id = ? ",
					ionSys.newString(grantId));
			IonStruct credentialId = (IonStruct) result.iterator().next();
			credentialDetails.setUniversityEIN(((IonString) credentialId.get("universityEIN")).stringValue());
			credentialDetails.setOrganizationEncryptedPII(((IonBlob) credentialId.get("encryptedPII")).getBytes());
			credentialDetails.setPIIHash(((IonBlob) credentialId.get("learnerPIIHash")).getBytes());
			credentialDetails.setCareer(((IonString) credentialId.get("career")).stringValue());
			credentialDetails.setConcentration(((IonString) credentialId.get("concentration")).stringValue());
			credentialDetails.setSubject(((IonString) credentialId.get("subject")).stringValue());

		});

		return credentialDetails;
	}

	// Get a transcript for a grant id
	public GrantedCredentialVO findGrantedTranscript(String grantId) {

		GrantedCredentialVO credentialDetails = new GrantedCredentialVO();

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select universityEIN, encryptedPII, learnerPIIHash, career,subject,concentration from OrganizationCertificatePII where id = ? ",
					ionSys.newString(grantId));
			IonStruct credentialId = (IonStruct) result.iterator().next();
			credentialDetails.setUniversityEIN(((IonString) credentialId.get("universityEIN")).stringValue());
			credentialDetails.setOrganizationEncryptedPII(((IonBlob) credentialId.get("encryptedPII")).getBytes());
			credentialDetails.setPIIHash(((IonBlob) credentialId.get("learnerPIIHash")).getBytes());
			credentialDetails.setCareer(((IonString) credentialId.get("career")).stringValue());
			credentialDetails.setConcentration(((IonString) credentialId.get("concentration")).stringValue());
			credentialDetails.setSubject(((IonString) credentialId.get("subject")).stringValue());

		});

		return credentialDetails;
	}

	// Get all the granted certificates to an organization
	public <List> GrantedCredentialVO findGrantedCredentials(String organizationEIN) {

		GrantedCredentialVO credentialDetails = new GrantedCredentialVO();

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select universityEIN, career,subject,concentration from OrganizationCertificatePII where organizationEIN = ? ",
					ionSys.newString(organizationEIN));

			IonStruct credentialId = (IonStruct) result.iterator().next();
			credentialDetails.setUniversityEIN(((IonString) credentialId.get("universityEIN")).stringValue());
			credentialDetails.setOrganizationEncryptedPII(((IonBlob) credentialId.get("encryptedPII")).getBytes());
			credentialDetails.setPIIHash(((IonBlob) credentialId.get("learnerPIIHash")).getBytes());
			credentialDetails.setCareer(((IonString) credentialId.get("career")).stringValue());
			credentialDetails.setConcentration(((IonString) credentialId.get("concentration")).stringValue());
			credentialDetails.setSubject(((IonString) credentialId.get("subject")).stringValue());

		});

		return credentialDetails;
	}

	// Deserialize byte array to Java object
	private static Object getObject(byte[] serializedBytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(serializedBytes);
		ObjectInput in = new ObjectInputStream(bis);
		return in.readObject();
	}

	// Get encrypted PII value for a certificate
	public LearnerBlobsVO getEncryptedPII(String EIN, String career, String subject, String concentration,
			byte[] PIIHash) {

		LearnerBlobsVO learnerBlob = new LearnerBlobsVO();

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select encryptedPII from CareerRegistration where EIN = ? AND career = ? AND subject = ? AND concentration = ? AND PIIHash = ? ",
					ionSys.newString(EIN), ionSys.newString(career), ionSys.newString(subject),
					ionSys.newString(concentration), ionSys.newBlob(PIIHash));

			IonStruct registration = (IonStruct) result.iterator().next();

			learnerBlob.setEncryptedPII(((IonBlob) registration.get("encryptedPII")).getBytes());

		});

		return learnerBlob;
	}

	// Get encrypted PII value for a transcript
	public LearnerBlobsVO getTranscriptEncryptedPII(String EIN, String career, String subject, String concentration,
			byte[] PIIHash) {

		LearnerBlobsVO learnerBlob = new LearnerBlobsVO();

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select encryptedPII from CourseListInfo where EIN = ? AND career = ? AND subject = ? AND concentration = ? AND PIIHash = ? ",
					ionSys.newString(EIN), ionSys.newString(career), ionSys.newString(subject),
					ionSys.newString(concentration), ionSys.newBlob(PIIHash));

			IonStruct registration = (IonStruct) result.iterator().next();

			learnerBlob.setEncryptedPII(((IonBlob) registration.get("encryptedPII")).getBytes());

		});

		return learnerBlob;
	}

	// Get organization encrypted PII for a certificate
	public LearnerBlobsVO getOrganizationCertificatePII(String id, String organizationEIN, String universityEIN,
			byte[] learnerPIIHash, String career, String subject, String concentration) {

		LearnerBlobsVO learnerBlob = new LearnerBlobsVO();

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select encryptedPII from OrganizationCertificatePII where organizationEIN = ? AND universityEIN = ? AND learnerPIIHash = ? AND career = ? AND subject = ? AND concentration = ?",
					ionSys.newString(organizationEIN), ionSys.newString(universityEIN), ionSys.newBlob(learnerPIIHash),
					ionSys.newString(career), ionSys.newString(subject), ionSys.newString(concentration));

			IonStruct registration = (IonStruct) result.iterator().next();

			learnerBlob.setEncryptedPII(((IonBlob) registration.get("encryptedPII")).getBytes());

		});

		return learnerBlob;
	}

	// Add program registration record
	public VerificationVO addProgramRegistration(ProgramRegistrationBlockVO programRegistrationBlockVO) {

		VerificationVO verificationVO = new VerificationVO();
		String signatureRecord;
		String universityName = programRegistrationBlockVO.getUniversityName();
		String EIN = programRegistrationBlockVO.getUniversityEIN();
		String career = programRegistrationBlockVO.getCareer().trim();
		String concentration = programRegistrationBlockVO.getConcentration();
		String programStatus = programRegistrationBlockVO.getProgramStatus();
		String startDate = programRegistrationBlockVO.getStartDate();
		String endtDate = programRegistrationBlockVO.getEndDate();
		String subject = programRegistrationBlockVO.getSubject();
		String degreeClass = programRegistrationBlockVO.getDegreeClass();
		String gpa = programRegistrationBlockVO.getGPA();
		byte[] PIIHashBytes = programRegistrationBlockVO.getPIIHash();
		byte[] encryptedPII = programRegistrationBlockVO.getEncryptedPII();

		IonStruct carrerRegistration = ionSys.newEmptyStruct();
		carrerRegistration.put("universityName").newString(universityName);
		carrerRegistration.put("EIN").newString(EIN);
		carrerRegistration.put("career").newString(career);
		carrerRegistration.put("concentration").newString(concentration);
		carrerRegistration.put("subject").newString(subject);
		carrerRegistration.put("PIIHash").newBlob(PIIHashBytes);
		carrerRegistration.put("programStatus").newString(programStatus);
		carrerRegistration.put("startDate").newString(startDate);
		carrerRegistration.put("endDate").newString(endtDate);
		carrerRegistration.put("degreeClass").newString(degreeClass);
		carrerRegistration.put("gpa").newString(gpa);

		carrerRegistration.put("encryptedPII").newBlob(encryptedPII);

		// Insert the document
		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO CareerRegistration ?", carrerRegistration);

		});
/*
		IonStruct courseRecord = ionSys.newEmptyStruct();
		courseRecord.put("universityName").newString(universityName);
		courseRecord.put("EIN").newString(EIN);
		courseRecord.put("career").newString(career);
		courseRecord.put("concentration").newString(concentration);
		courseRecord.put("subject").newString(subject);
		courseRecord.put("PIIHash").newBlob(PIIHashBytes);
		courseRecord.put("encryptedPII").newBlob(encryptedPII);
		//courseRecord.put("courseList").newEmptyList();

		// Insert the document
		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO CourseListInfo ?", courseRecord);

		});*/
		// Get digest
		GetDigestResult getDigestResult = getDigest();
		ByteBuffer digest = getDigestResult.getDigest();
		String digestTipAddress = getDigestResult.getDigestTipAddress().getIonText();
		// Store digest
		verificationVO.setDigest(digest);
		verificationVO.setDigestTipAddress(new ValueHolder().withIonText(digestTipAddress));
		// Get document id and block address of the inserted course
		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"select  r.metadata.id as docId, r.blockAddress as revisionBlockAddress, r.hash as blockHash FROM _ql_committed_CareerRegistration AS r WHERE r.data.EIN = ? and  r.data.career = ? and r.data.concentration = ? and r.data.subject = ? and r.data.PIIHash = ? ",
					ionSys.newString(EIN), ionSys.newString(career), ionSys.newString(concentration),
					ionSys.newString(subject), ionSys.newBlob(PIIHashBytes));
			// List<IonStruct> list = ScanTable.toIonStructs(result);

			IonStruct revisionData = (IonStruct) result.iterator().next();
			String documentId = revisionData.get("docId").toString();
			// String documentId = revisionData.get("docId").toString();
			IonStruct revisionBlockStruct = (IonStruct) revisionData.get("revisionBlockAddress");
			String revisionBlockAddress = revisionData.get("revisionBlockAddress").toString();
			verificationVO.setCareer(career);
			verificationVO.setConcentration(concentration);
			verificationVO.setPIIHash(PIIHashBytes);
			verificationVO.setSubject(subject);
			verificationVO.setUniversityEIN(EIN);
			verificationVO.setDocumentId(documentId);
			verificationVO.setRevisionBlockHash(((IonBlob) revisionData.get("blockHash")).getBytes());
			verificationVO.setRevisionBlockAddress(revisionBlockAddress);
			verificationVO.setRevisionBlockStranId(revisionBlockStruct.get("strandId").toString());
			verificationVO
					.setRevisionBlockSequenceNo(Integer.parseInt(revisionBlockStruct.get("sequenceNo").toString()));
		});

		return verificationVO;
	}

	// Get encrypted PII for a course
	public LearnerBlobsVO getEncryptedCoursePII(String EIN, String career, String subject, String concentration,
			byte[] PIIHash) {

		LearnerBlobsVO learnerBlob = new LearnerBlobsVO();

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select encryptedPII from CourseListInfo where EIN = ? AND career = ? AND subject = ? AND concentration = ? AND PIIHash = ?   ",
					ionSys.newString(EIN), ionSys.newString(career), ionSys.newString(subject),
					ionSys.newString(concentration), ionSys.newBlob(PIIHash));

			IonStruct registration = (IonStruct) result.iterator().next();

			learnerBlob.setEncryptedPII(((IonBlob) registration.get("encryptedPII")).getBytes());

		});

		return learnerBlob;
	}
	// Insert course record
	// Insert a course record

	public CourseVerificationVO addNewCourseList(ProgramRegistrationBlockVO programRegistrationBlockVO) {

		CourseVerificationVO courseVerificationVO = new CourseVerificationVO();

		String universityName = programRegistrationBlockVO.getUniversityName();
		String EIN = programRegistrationBlockVO.getUniversityEIN();
		String career = programRegistrationBlockVO.getCareer().trim();
		String concentration = programRegistrationBlockVO.getConcentration();
		String course = programRegistrationBlockVO.getCourse();

		String startDate = programRegistrationBlockVO.getStartDate();
		String endtDate = programRegistrationBlockVO.getEndDate();
		String subject = programRegistrationBlockVO.getSubject();

		String gpa = programRegistrationBlockVO.getGPA();

		String grade = programRegistrationBlockVO.getCourseGrade();
		byte[] PIIHashBytes = programRegistrationBlockVO.getPIIHash();
		byte[] encryptedPII = programRegistrationBlockVO.getEncryptedPII();
		CourseListVO courseListVO = new CourseListVO();
		IonStruct newCourse = ionSys.newEmptyStruct();
		newCourse.put("course").newString(course);
		newCourse.put("startDate").newString(startDate);
		newCourse.put("endDate").newString(endtDate);
		newCourse.put("grade").newString(grade);
		newCourse.put("gpa").newString(gpa);

		IonList ionlist = ionSys.newList(newCourse);
		IonStruct courseStructure = ionSys.newEmptyStruct();
		courseStructure.add("courseList").newList(ionlist);
		IonStruct courseFirstRecord = ionSys.newEmptyStruct();
		courseFirstRecord.put("EIN").newString(EIN);
		courseFirstRecord.put("universityName").newString(universityName);
		courseFirstRecord.put("career").newString(career);
		courseFirstRecord.put("concentration").newString(concentration);
		courseFirstRecord.put("subject").newString(subject);
		courseFirstRecord.put("PIIHash").newBlob(PIIHashBytes);
		courseFirstRecord.put("encryptedPII").newBlob(encryptedPII);
		// courseList.add(newCourse);
		courseFirstRecord.put("courseStructure", courseStructure);

		// Insert the document

		qldbDriver.execute(txn3 -> {

			txn3.execute("INSERT INTO CourseListInfo ?", courseFirstRecord);
		});
		// Get digest
		GetDigestResult getDigestResult = getDigest();
		ByteBuffer digest = getDigestResult.getDigest();
		String digestTipAddress = getDigestResult.getDigestTipAddress().getIonText();
		// store digest
		courseVerificationVO.setDigest(digest);
		courseVerificationVO.setDigestTipAddress(new ValueHolder().withIonText(digestTipAddress));
		// Get verification details
		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"select  r.metadata.id as docId, r.blockAddress as revisionBlockAddress, r.hash as blockHash FROM _ql_committed_CourseListInfo AS r WHERE r.data.EIN = ? and  r.data.career = ? and r.data.concentration = ? and r.data.subject = ? and r.data.PIIHash = ? ",
					ionSys.newString(EIN), ionSys.newString(career), ionSys.newString(concentration),
					ionSys.newString(subject), ionSys.newBlob(PIIHashBytes));

			IonStruct revisionData = (IonStruct) result.iterator().next();
			String documentId = revisionData.get("docId").toString();

			IonStruct revisionBlockStruct = (IonStruct) revisionData.get("revisionBlockAddress");
			String revisionBlockAddress = revisionData.get("revisionBlockAddress").toString();
			courseVerificationVO.setCareer(career);
			courseVerificationVO.setConcentration(concentration);
			courseVerificationVO.setPIIHash(PIIHashBytes);
			courseVerificationVO.setSubject(subject);

			courseVerificationVO.setUniversityEIN(EIN);
			courseVerificationVO.setDocumentId(documentId);
			courseVerificationVO.setRevisionBlockHash(((IonBlob) revisionData.get("blockHash")).getBytes());
			courseVerificationVO.setRevisionBlockAddress(revisionBlockAddress);
			courseVerificationVO.setRevisionBlockStranId(revisionBlockStruct.get("strandId").toString());
			courseVerificationVO
					.setRevisionBlockSequenceNo(Integer.parseInt(revisionBlockStruct.get("sequenceNo").toString()));
		});

		return courseVerificationVO;
	}

	// Add a new course into the transcript
	public CourseVerificationVO updateCourseList(ProgramRegistrationBlockVO programRegistrationBlockVO) {

		CourseVerificationVO courseVerificationVO = new CourseVerificationVO();

		String universityName = programRegistrationBlockVO.getUniversityName();
		String EIN = programRegistrationBlockVO.getUniversityEIN();
		String career = programRegistrationBlockVO.getCareer().trim();
		String concentration = programRegistrationBlockVO.getConcentration();
		String course = programRegistrationBlockVO.getCourse();

		String startDate = programRegistrationBlockVO.getStartDate();
		String endtDate = programRegistrationBlockVO.getEndDate();
		String subject = programRegistrationBlockVO.getSubject();

		String gpa = programRegistrationBlockVO.getGPA();

		String grade = programRegistrationBlockVO.getCourseGrade();
		byte[] PIIHashBytes = programRegistrationBlockVO.getPIIHash();
		byte[] encryptedPII = programRegistrationBlockVO.getEncryptedPII();

		CourseListVO courseListVO = new CourseListVO();
		IonStruct newCourse = ionSys.newEmptyStruct();
		newCourse.put("course").newString(course);
		newCourse.put("startDate").newString(startDate);
		newCourse.put("endDate").newString(endtDate);
		newCourse.put("grade").newString(grade);
		newCourse.put("gpa").newString(gpa);
		// Get existing transcript
		qldbDriver.execute(txn1 -> {
			Result result1 = txn1.execute(
					"Select courseStructure from CourseListInfo AS c where c.EIN = ? AND c.PIIHash = ? AND c.career = ? AND c.subject = ? AND c.concentration = ? ",
					ionSys.newString(EIN), ionSys.newBlob(PIIHashBytes), ionSys.newString(career),
					ionSys.newString(subject), ionSys.newString(concentration));

			IonStruct courseRec = (IonStruct) result1.iterator().next();
			IonStruct courseStructure = (IonStruct) courseRec.get("courseStructure");
			IonList courseList = (IonList) courseStructure.get("courseList");
			courseList.add(newCourse);
			// courseStructure.put("courseList",courseList);
			courseListVO.setCourseStructure(courseStructure);
			courseListVO.setAddedCourseList(courseList);

		});
		// Update the transcript record adding the new course
		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"UPDATE CourseListInfo as c SET c.courseStructure.courseList = ? WHERE  c.EIN = ? AND c.PIIHash = ? AND c.career = ? AND c.subject = ? AND c.concentration = ? ",
					courseListVO.getAddedCourseList(), ionSys.newString(EIN), ionSys.newBlob(PIIHashBytes),
					ionSys.newString(career), ionSys.newString(subject), ionSys.newString(concentration));

		});

		GetDigestResult getDigestResult = getDigest();
		ByteBuffer digest = getDigestResult.getDigest();
		String digestTipAddress = getDigestResult.getDigestTipAddress().getIonText();
		courseVerificationVO.setDigest(digest);
		courseVerificationVO.setDigestTipAddress(new ValueHolder().withIonText(digestTipAddress));
		// Get verification details after updation
		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"select  r.metadata.id as docId, r.blockAddress as revisionBlockAddress, r.hash as blockHash FROM _ql_committed_CourseListInfo AS r WHERE r.data.EIN = ? and  r.data.career = ? and r.data.concentration = ? and r.data.subject = ? and r.data.PIIHash = ? ",
					ionSys.newString(EIN), ionSys.newString(career), ionSys.newString(concentration),
					ionSys.newString(subject), ionSys.newBlob(PIIHashBytes));

			IonStruct revisionData = (IonStruct) result.iterator().next();
			String documentId = revisionData.get("docId").toString();

			IonStruct revisionBlockStruct = (IonStruct) revisionData.get("revisionBlockAddress");
			String revisionBlockAddress = revisionData.get("revisionBlockAddress").toString();
			courseVerificationVO.setCareer(career);
			courseVerificationVO.setConcentration(concentration);
			courseVerificationVO.setPIIHash(PIIHashBytes);
			courseVerificationVO.setSubject(subject);

			courseVerificationVO.setUniversityEIN(EIN);
			courseVerificationVO.setDocumentId(documentId);
			courseVerificationVO.setRevisionBlockHash(((IonBlob) revisionData.get("blockHash")).getBytes());
			courseVerificationVO.setRevisionBlockAddress(revisionBlockAddress);
			courseVerificationVO.setRevisionBlockStranId(revisionBlockStruct.get("strandId").toString());
			courseVerificationVO
					.setRevisionBlockSequenceNo(Integer.parseInt(revisionBlockStruct.get("sequenceNo").toString()));
		});

		return courseVerificationVO;
	}

	// Insert verification details
	public void addVerification(VerificationVO verificationVO, ByteBuffer universitySignature) {

		IonStruct carrerRegistrationSignature = ionSys.newEmptyStruct();
		carrerRegistrationSignature.put("universityDigitalSignature")
				.newBlob(convertByteBufferToByteArray(universitySignature));
		carrerRegistrationSignature.put("docId").newString(verificationVO.getDocumentId());
		carrerRegistrationSignature.put("revisionBlockAddress").newString(verificationVO.getRevisionBlockAddress());
		carrerRegistrationSignature.put("digestTipAddress")
				.newBlob(verificationVO.getDigestTipAddress().getIonText().getBytes());
		carrerRegistrationSignature.put("digest").newBlob(verificationVO.getDigest().array());
		carrerRegistrationSignature.put("blockHash").newBlob(verificationVO.getRevisionBlockHash());

		carrerRegistrationSignature.put("EIN").newString(verificationVO.getUniversityEIN());
		carrerRegistrationSignature.put("career").newString(verificationVO.getCareer());
		carrerRegistrationSignature.put("concentration").newString(verificationVO.getConcentration());
		carrerRegistrationSignature.put("subject").newString(verificationVO.getSubject());

		carrerRegistrationSignature.put("PIIHash").newBlob(verificationVO.getPIIHash());
		carrerRegistrationSignature.put("isCourseRecord").newString(verificationVO.getIsCourseRecord());

		// Insert the document
		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO Verification ?", carrerRegistrationSignature);
		});
	}

	public void addCourseVerification(CourseVerificationVO verificationVO, ByteBuffer universitySignature) {

		IonStruct carrerRegistrationSignature = ionSys.newEmptyStruct();
		carrerRegistrationSignature.put("universityDigitalSignature")
				.newBlob(convertByteBufferToByteArray(universitySignature));
		carrerRegistrationSignature.put("docId").newString(verificationVO.getDocumentId());
		carrerRegistrationSignature.put("revisionBlockAddress").newString(verificationVO.getRevisionBlockAddress());
		carrerRegistrationSignature.put("digestTipAddress")
				.newBlob(verificationVO.getDigestTipAddress().getIonText().getBytes());
		carrerRegistrationSignature.put("digest").newBlob(verificationVO.getDigest().array());
		carrerRegistrationSignature.put("blockHash").newBlob(verificationVO.getRevisionBlockHash());

		carrerRegistrationSignature.put("EIN").newString(verificationVO.getUniversityEIN());
		carrerRegistrationSignature.put("career").newString(verificationVO.getCareer());
		carrerRegistrationSignature.put("concentration").newString(verificationVO.getConcentration());
		carrerRegistrationSignature.put("subject").newString(verificationVO.getSubject());

		carrerRegistrationSignature.put("PIIHash").newBlob(verificationVO.getPIIHash());
		carrerRegistrationSignature.put("isCourseRecord").newString(verificationVO.getIsCourseRecord());

		// Insert the document
		qldbDriver.execute(txn -> {

			txn.execute("INSERT INTO Verification ?", carrerRegistrationSignature);
		});
	}

	// Update an existing verification details
	public void updateVerification(VerificationVO verificationVO, ByteBuffer universitySignature) {

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"UPDATE Verification as c SET c.universityDigitalSignature = ? ,c.docId = ? , c.revisionBlockAddress = ?,c.digestTipAddress = ?,c.digest = ?, c.blockHash =? WHERE  c.isCourseRecord= ? and c.EIN = ? AND c.PIIHash = ? AND c.career = ? AND c.subject = ? AND c.concentration = ? ",
					ionSys.newBlob(universitySignature.array()), ionSys.newString(verificationVO.getDocumentId()),
					ionSys.newString(verificationVO.getRevisionBlockAddress()),
					ionSys.newBlob(verificationVO.getDigestTipAddress().getIonText().getBytes()),
					ionSys.newBlob(verificationVO.getDigest().array()),
					ionSys.newBlob(verificationVO.getRevisionBlockHash()),
					ionSys.newString(verificationVO.getIsCourseRecord()),
					ionSys.newString(verificationVO.getUniversityEIN()), ionSys.newBlob(verificationVO.getPIIHash()),
					ionSys.newString(verificationVO.getCareer()), ionSys.newString(verificationVO.getSubject()),
					ionSys.newString(verificationVO.getConcentration()));

		});

		qldbDriver.execute(txn -> {

			Result result = txn.execute(
					"Select revisionBlockAddress from Verification c where c.isCourseRecord= ? and c.EIN = ? AND c.PIIHash = ? AND c.career = ? AND c.subject = ? AND c.concentration = ? ",
					ionSys.newString(verificationVO.getIsCourseRecord()),
					ionSys.newString(verificationVO.getUniversityEIN()), ionSys.newBlob(verificationVO.getPIIHash()),
					ionSys.newString(verificationVO.getCareer()), ionSys.newString(verificationVO.getSubject()),
					ionSys.newString(verificationVO.getConcentration()));

			IonStruct verification = (IonStruct) result.iterator().next();

			String documentId = verification.get("revisionBlockAddress").toString();

		});

	}

	// Update program registration record
	public VerificationVO updateProgramRegistration(ProgramRegistrationBlockVO programRegistrationBlockVO) {
		VerificationVO verificationVO = new VerificationVO();
		String EIN = programRegistrationBlockVO.getUniversityEIN();
		String career = programRegistrationBlockVO.getCareer().trim();
		String concentration = programRegistrationBlockVO.getConcentration();

		String endtDate = programRegistrationBlockVO.getEndDate();
		String subject = programRegistrationBlockVO.getSubject();
		String degreeClass = programRegistrationBlockVO.getDegreeClass();
		String gpa = programRegistrationBlockVO.getGPA();
		String programStatus = programRegistrationBlockVO.getProgramStatus();
		byte[] PIIHashBytes = programRegistrationBlockVO.getPIIHash();

		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"UPDATE CareerRegistration as c SET c.endDate = ? ,c.degreeClass = ? , c.gpa = ?, c.programStatus = ?  WHERE  c.EIN = ? AND c.PIIHash = ? AND c.career = ? AND c.subject = ? AND c.concentration = ? ",
					ionSys.newString(endtDate), ionSys.newString(degreeClass), ionSys.newString(gpa),
					ionSys.newString(programStatus), ionSys.newString(EIN), ionSys.newBlob(PIIHashBytes),
					ionSys.newString(career), ionSys.newString(subject), ionSys.newString(concentration));
			// List<IonStruct> list = ScanTable.toIonStructs(result);
			// Insert the document

		});

		GetDigestResult getDigestResult = getDigest();
		ByteBuffer digest = getDigestResult.getDigest();
		String digestTipAddress = getDigestResult.getDigestTipAddress().getIonText();
		verificationVO.setDigest(digest);
		verificationVO.setDigestTipAddress(new ValueHolder().withIonText(digestTipAddress));
		qldbDriver.execute(txn -> {
			Result result = txn.execute(
					"select  r.metadata.id as docId, r.blockAddress as revisionBlockAddress, r.hash as blockHash FROM _ql_committed_CareerRegistration AS r WHERE r.data.EIN = ? and  r.data.career = ? and r.data.concentration = ? and r.data.subject = ? and r.data.PIIHash = ? ",
					ionSys.newString(EIN), ionSys.newString(career), ionSys.newString(concentration),
					ionSys.newString(subject), ionSys.newBlob(PIIHashBytes));

			IonStruct revisionData = (IonStruct) result.iterator().next();
			String documentId = revisionData.get("docId").toString();

			IonStruct revisionBlockStruct = (IonStruct) revisionData.get("revisionBlockAddress");
			String revisionBlockAddress = revisionData.get("revisionBlockAddress").toString();

			verificationVO.setUniversityEIN(EIN);
			verificationVO.setCareer(career);
			verificationVO.setConcentration(concentration);
			verificationVO.setSubject(subject);
			verificationVO.setDocumentId(documentId);
			verificationVO.setRevisionBlockHash(((IonBlob) revisionData.get("blockHash")).getBytes());
			verificationVO.setRevisionBlockAddress(revisionBlockAddress);
			verificationVO.setRevisionBlockStranId(revisionBlockStruct.get("strandId").toString());
			verificationVO.setPIIHash(PIIHashBytes);
			verificationVO
					.setRevisionBlockSequenceNo(Integer.parseInt(revisionBlockStruct.get("sequenceNo").toString()));
		});

		return verificationVO;
	}

	// Convert bytebuffer to byte array
	public static byte[] convertByteBufferToByteArray(final ByteBuffer buffer) {
		byte[] arr = new byte[buffer.remaining()];
		buffer.get(arr);
		return arr;
	}

	// Get revision record details using digest-tip-address
	public GetRevisionResult getRevision(String documentId, String stranId, int sequenceNo,
			ValueHolder digestTipAddress) {

		documentId = documentId.substring(1, documentId.length() - 1);

		IonStruct ionStruct = ionSys.newEmptyStruct();

		ionStruct.add("strandId", ionSys.newString(stranId));
		ionStruct.add("sequenceNo", ionSys.newInt(sequenceNo));

		String blockAddress = "{strandId:" + stranId + ",sequenceNo:" + sequenceNo + "}";
		System.out.println("Block Address :" + blockAddress);
		GetRevisionRequest request = new GetRevisionRequest().withName(ledgerName)
				.withBlockAddress(new ValueHolder().withIonText(blockAddress)).withDocumentId(documentId)
				.withDigestTipAddress(new ValueHolder().withIonText(blockAddress));

		GetRevisionResult revisionResult = client.getRevision(request);

		return revisionResult;

	}
	// Tokenize a string using Java

	public List<String> getTokens(String str, String delimeter) {
		List<String> tokens = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(str, delimeter);
		while (tokenizer.hasMoreElements()) {
			tokens.add(tokenizer.nextToken());
		}
		return tokens;
	}

	// Create Hash value
	private byte[] createHash(String data) {
		// Create SSN Hash
		MessageDigest md;
		byte[] hashValue = null;
		try {

			md = MessageDigest.getInstance("SHA-256");
			hashValue = md.digest(data.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return hashValue;
	}
}