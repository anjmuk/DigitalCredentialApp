package com.digitalcredentials.app;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.qldb.model.GetRevisionResult;
import com.amazonaws.services.qldb.model.ValueHolder;
import com.digitalcredentials.dao.DigitalCredentialDAO;
import com.digitalcredentials.vo.CompleteRegistrationVO;
import com.digitalcredentials.vo.CourseVerificationVO;
import com.digitalcredentials.vo.LearnerBlobsVO;
import com.digitalcredentials.vo.LearnerIdVO;
import com.digitalcredentials.vo.ProgramRegistrationVO;
import com.digitalcredentials.vo.SearchLearnerRecordsVO;
import com.digitalcredentials.vo.SignedRegistrationVO;
import com.digitalcredentials.vo.SignInVO;
import com.digitalcredentials.vo.UserVO;
import com.digitalcredentials.vo.VerificationVO;
import com.digitalcredentials.vo.GrantedCredentialVO;

import awscode.Verifier;

import com.digitalcredentials.vo.EnumerationVO.ProgramStatus;
import com.digitalcredentials.vo.LearnerPIIVO;
import com.digitalcredentials.vo.ProgramRegistrationBlockVO;

import security.SecurityUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import userManagement.UserManagementUtility;

@Controller
@SessionAttributes("name")
public class DigitalCredentialController {
	public static final String EMPTY_VALUE = " ";

	// List of careers
	@ModelAttribute("careerList")
	public List<String> getCareerList() {
		List<String> careerList = new ArrayList<String>();
		careerList.add("Graduate");
		careerList.add("UnderGraduate");
		careerList.add("Certificate");

		return careerList;
	}

	// List of subjects
	@ModelAttribute("subjectList")
	public List<String> getSubjectList() {
		List<String> subjectList = new ArrayList<String>();
		subjectList.add("Computer Science");
		subjectList.add("Management Information Systems");
		subjectList.add("Cyber Security");

		return subjectList;
	}

	// Degree Status
	@ModelAttribute("degreeClassList")
	public List<String> getDegreeClassList() {
		List<String> degreeClassList = new ArrayList<String>();
		degreeClassList.add("First Class Honors");
		degreeClassList.add("First Class");
		degreeClassList.add("Second Class");
		degreeClassList.add("Incomplete");
		degreeClassList.add("Failed");

		return degreeClassList;
	}

	// List of courses
	@ModelAttribute("courseList")
	public List<String> getCourseList() {
		List<String> courseList = new ArrayList<String>();
		courseList.add("Software Design");
		courseList.add("Distributed Systems Security");
		courseList.add("Programming Language");
		courseList.add("Advanced Programming Language");
		courseList.add("Advanced Operating Systems");
		courseList.add("Database Management Systems");
		courseList.add("Advanced Database Management Systems");

		return courseList;
	}

	// List of grades
	@ModelAttribute("gradeList")
	public List<String> getGradeList() {
		List<String> gradeList = new ArrayList<String>();
		gradeList.add("A+");
		gradeList.add("A");
		gradeList.add("A-");
		gradeList.add("B+");
		gradeList.add("B");
		gradeList.add("B-");
		gradeList.add("C+");
		gradeList.add("C");
		gradeList.add("C-");
		gradeList.add("F");

		return gradeList;
	}

	// List of program status
	@ModelAttribute("programStatusList")
	public List<String> getProgramStatusList() {
		List<String> programStatusList = new ArrayList<String>();
		programStatusList.add("Registered");
		programStatusList.add("Completed");
		return programStatusList;
	}

	// List of concentrations
	@ModelAttribute("concentrationList")
	public List<String> getConcentrationList() {
		List<String> concentrationList = new ArrayList<String>();
		concentrationList.add("No Concentration");
		concentrationList.add("Database");
		concentrationList.add("Artificial Intelligence");
		concentrationList.add("Software Engineering");
		concentrationList.add("Network Technologies");
		concentrationList.add("Infomation Assurance");
		return concentrationList;
	}

	// Default Home page
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
	}

	// Default Home page
	@RequestMapping(value = "/DigitalCredential", method = RequestMethod.GET)
	public ModelAndView gohome(ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
	}

	// University Home Page
	@RequestMapping(value = "/universityHome", method = RequestMethod.GET)

	public ModelAndView showUniversityHome(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("universityHome");

		try {

			String userId = request.getParameter("userId");

			SignInVO signInVO = new SignInVO();
			signInVO.setUserId(userId);

			modelAndView.addObject("userDetails", signInVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Learner Home Page
	@RequestMapping(value = "/learnerHome", method = RequestMethod.GET)

	public ModelAndView showLearnerHome(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("learnerHome");
		try {

			String userId = request.getParameter("userId");

			SignInVO signInVO = new SignInVO();
			signInVO.setUserId(userId);
			modelAndView.addObject("learnerDetails", signInVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Organization Home Page
	@RequestMapping(value = "/organizationHome", method = RequestMethod.GET)

	public ModelAndView showOrganizationHome(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("organizationHome");
		try {

			String userId = request.getParameter("userId");

			SignInVO signInVO = new SignInVO();
			signInVO.setUserId(userId);
			modelAndView.addObject("userDetails", signInVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Show University Sign Up
	@RequestMapping(value = "/showUniversitySignUp", method = RequestMethod.GET)
	public String showUniversitySignUpPage(Model model) throws Exception {

		UserVO userVO = new UserVO();
		model.addAttribute("signUpDetails", userVO);

		return "showUniversitySignUp";
	}

	/* Sign up an university, create signing keys, create university record */
	@RequestMapping(value = "/universitySignUp", method = RequestMethod.POST)

	public ModelAndView universitySignUp(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("universitySignIn");
		try {

			UserVO userVO = new UserVO();
			String name = request.getParameter("name");
			String userId = request.getParameter("userId");
			String address = request.getParameter("address");
			String EIN = request.getParameter("EIN");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String password = request.getParameter("password").trim();
			userVO.setAddress(address);
			userVO.setEIN(EIN);
			userVO.setEmail(email);
			userVO.setName(name);
			userVO.setPassword(password);
			userVO.setUserId(userId);
			userVO.setPhone(phone);
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			// Create university user in Cognito
			UserManagementUtility.signUp(userVO);
			SecurityUtility securityUtility = new SecurityUtility();
			// Create University Signing key pair using KMS
			securityUtility.createSignKeyPair(EIN, name);
			SignInVO signInVO = new SignInVO();
			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			// Create University record in QLDB
			digitalCredentialDAO.addIssuingUniversity(userVO);
			modelAndView.addObject("signInDetails", signInVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Show University Sign In
	@RequestMapping(value = "/showUniversitySignIn", method = RequestMethod.GET)
	public ModelAndView showUniversitySignIn(ModelMap model) {
		SignInVO signInVO = new SignInVO();
		model.addAttribute("signInDetails", signInVO);
		ModelAndView modelAndView = new ModelAndView("universitySignIn");
		return modelAndView;
	}

	// University Sign in
	@RequestMapping(value = "/universitySignIn", method = RequestMethod.POST)

	public ModelAndView universitySignIn(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("universityHome");
		try {

			SignInVO signInVO = new SignInVO();
			String userId = request.getParameter("userId");
			String password = request.getParameter("password").trim();

			signInVO.setPassword(password);
			signInVO.setUserId(userId);

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserManagementUtility.initiateLogin(signInVO);

			UserVO userVO = userManagementUtility.getUniversityInfo(signInVO.getUserId());

			modelAndView.addObject("userDetails", userVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Show Organization Sign Up
	@RequestMapping(value = "/showOrganizationSignUp", method = RequestMethod.GET)
	public String showOrganizationSignUpPage(Model model) throws Exception {

		UserVO userVO = new UserVO();
		model.addAttribute("signUpDetails", userVO);

		return "showOrganizationSignUp";
	}

	/*
	 * Sign up an organization, create security public/private key pair, create
	 * organization record
	 */
	@RequestMapping(value = "/organizationSignUp", method = RequestMethod.POST)

	public ModelAndView organizationSignUp(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("organizationSignIn");
		try {

			UserVO userVO = new UserVO();
			String name = request.getParameter("name");
			String userId = request.getParameter("userId");
			String address = request.getParameter("address");
			String EIN = request.getParameter("EIN");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String password = request.getParameter("password").trim();
			userVO.setAddress(address);
			userVO.setEIN(EIN);
			userVO.setEmail(email);
			userVO.setName(name);
			userVO.setPassword(password);
			userVO.setUserId(userId);
			userVO.setPhone(phone);
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserManagementUtility.organizationSignUp(userVO);
			SecurityUtility securityUtility = new SecurityUtility();

			securityUtility.createEncryptionKeys(EIN, name);

			SignInVO signInVO = new SignInVO();
			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			digitalCredentialDAO.addOrganization(userVO);
			modelAndView.addObject("signInDetails", signInVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Show Organization Sign In
	@RequestMapping(value = "/showOrganizationSignIn", method = RequestMethod.GET)
	public ModelAndView showOrganizationSignIn(ModelMap model) {
		SignInVO signInVO = new SignInVO();
		model.addAttribute("signInDetails", signInVO);
		ModelAndView modelAndView = new ModelAndView("organizationSignIn");
		return modelAndView;
	}

	// Organization Sign In
	@RequestMapping(value = "/organizationSignIn", method = RequestMethod.POST)

	public ModelAndView organizationSignIn(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("organizationHome");
		try {

			SignInVO signInVO = new SignInVO();
			String userId = request.getParameter("userId");
			String password = request.getParameter("password").trim();

			signInVO.setPassword(password);
			signInVO.setUserId(userId);

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserManagementUtility.organizationLogin(signInVO);

			UserVO userVO = userManagementUtility.getOrganizationInfo(signInVO.getUserId());
			userVO.setUserId(userId);
			modelAndView.addObject("userDetails", userVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Show Learner Sign Up
	@RequestMapping(value = "/showLearnerSignUp", method = RequestMethod.GET)
	public String showLearnerSignUpPage(Model model) throws Exception {
		UserVO userVO = new UserVO();
		model.addAttribute("signUpDetails", userVO);
		return "showLearnerSignUp";
	}

	// Sign up a learner, create public-private key pair , create learner record
	@RequestMapping(value = "/learnerSignUp", method = RequestMethod.POST)

	public ModelAndView learnerSignUp(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("learnerSignIn");
		try {
			UserVO userVO = new UserVO();
			String givenName = request.getParameter("givenName");
			String lastName = request.getParameter("lastName");
			String userId = request.getParameter("userId");
			String address = request.getParameter("address");
			String SSN = request.getParameter("SSN");
			String phone = request.getParameter("phone");
			String gender = request.getParameter("gender");
			String email = request.getParameter("email");
			String password = request.getParameter("password").trim();
			String learnerDOB = request.getParameter("learnerDOB");
			userVO.setAddress(address);
			userVO.setSSN(SSN);
			userVO.setEmail(email);
			userVO.setGivenName(givenName);
			userVO.setLatName(lastName);
			userVO.setPassword(password);
			userVO.setUserId(userId);
			userVO.setPhone(phone);
			userVO.setGender(gender);
			userVO.setLearnerDOB(learnerDOB);

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserManagementUtility.learnerSignUp(userVO);
			SecurityUtility securityUtility = new SecurityUtility();
			securityUtility.createEncryptionKeys(SSN.substring(6), givenName + " " + lastName);

			// Create SSN hash
			byte[] ssnHash = securityUtility.createHash(SSN);
			userVO.setSsnHash(ssnHash);

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			digitalCredentialDAO.addLearner(userVO);
			SignInVO signInVO = new SignInVO();

			modelAndView.addObject("signInDetails", signInVO);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Show learner Sign In
	@RequestMapping(value = "/showLearnerSignIn", method = RequestMethod.GET)
	public ModelAndView showLearnerSignIn(ModelMap model) {
		SignInVO signInVO = new SignInVO();
		model.addAttribute("signInDetails", signInVO);
		ModelAndView modelAndView = new ModelAndView("learnerSignIn");
		return modelAndView;
	}

	// Learner sign in
	@RequestMapping(value = "/learnerSignIn", method = RequestMethod.POST)

	public ModelAndView learnerSignIn(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("learnerHome");
		try {
			SignInVO signInVO = new SignInVO();
			String userId = request.getParameter("userId");
			String password = request.getParameter("password").trim();
			signInVO.setPassword(password);
			signInVO.setUserId(userId);
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserManagementUtility.initiateLearnerLogin(signInVO);

			UserVO userVO = userManagementUtility.getLearnerInfo(signInVO.getUserId());

			modelAndView.addObject("learnerDetails", userVO);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Show Add Program Registration
	@RequestMapping(value = "/showProgramRegistration", method = RequestMethod.GET)
	public ModelAndView showProgramRegistration(@ModelAttribute("userDetails") UserVO userDetails) {
		ProgramRegistrationVO programDetails = new ProgramRegistrationVO();
		// Default Value for country dropdown

		UserManagementUtility userManagementUtility = new UserManagementUtility();
		UserVO userVO = userManagementUtility.getUniversityInfo(userDetails.getUserId());
		ModelAndView modelAndView = new ModelAndView("showRegistration");
		ProgramRegistrationVO programRegistrationVO = new ProgramRegistrationVO();
		programRegistrationVO.setUniversityUserId(userVO.getUserId());
		programRegistrationVO.setUniversityEIN(userVO.getEIN());
		programRegistrationVO.setUniversityName(userVO.getName());
		modelAndView.addObject("programDetails", programRegistrationVO);
		return modelAndView;
	}

	// Show Add course
	@RequestMapping(value = "/showAddCourse", method = RequestMethod.GET)
	public ModelAndView showAddCourse(@ModelAttribute("userDetails") UserVO userDetails) {
		ProgramRegistrationVO programDetails = new ProgramRegistrationVO();
		// Default Value for country dropdown

		UserManagementUtility userManagementUtility = new UserManagementUtility();
		UserVO userVO = userManagementUtility.getUniversityInfo(userDetails.getUserId());
		ModelAndView modelAndView = new ModelAndView("addCourse");
		ProgramRegistrationVO programRegistrationVO = new ProgramRegistrationVO();
		programRegistrationVO.setUniversityUserId(userVO.getUserId());
		programRegistrationVO.setUniversityEIN(userVO.getEIN());
		programRegistrationVO.setUniversityName(userVO.getName());
		modelAndView.addObject("programDetails", programRegistrationVO);
		return modelAndView;
	}

	// Show program registration search
	@RequestMapping(value = "/showRegistrationSearch", method = RequestMethod.GET)
	public ModelAndView showRegistrationSearch(@ModelAttribute("userDetails") UserVO userDetails) {
		ProgramRegistrationVO programDetails = new ProgramRegistrationVO();
		// Default Value for country dropdown

		UserManagementUtility userManagementUtility = new UserManagementUtility();
		UserVO userVO = userManagementUtility.getUniversityInfo(userDetails.getUserId());
		ModelAndView modelAndView = new ModelAndView("searchRegistration");
		ProgramRegistrationVO programRegistrationVO = new ProgramRegistrationVO();
		programRegistrationVO.setUniversityUserId(userVO.getUserId());
		programRegistrationVO.setUniversityEIN(userVO.getEIN());
		programRegistrationVO.setUniversityName(userVO.getName());
		modelAndView.addObject("programDetails", programRegistrationVO);
		return modelAndView;
	}

	// Register program
	@RequestMapping(value = "/registerProgram", method = RequestMethod.POST)

	public ModelAndView registerProgram(@ModelAttribute("programDetails") ProgramRegistrationVO programDetails) {
		ModelAndView modelAndView = new ModelAndView("universityHome");
		try {

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();

			String learnerSSN = programDetails.getSSN();
			String learnerId = programDetails.getLearnerId();

			ProgramRegistrationBlockVO programRegistrationBlockVO = new ProgramRegistrationBlockVO();
			programRegistrationBlockVO.setUniversityEIN(programDetails.getUniversityEIN());
			programRegistrationBlockVO.setUniversityName(programDetails.getUniversityName());
			programRegistrationBlockVO.setCareer(programDetails.getCareer());
			programRegistrationBlockVO.setConcentration(programDetails.getConcentration());
			programRegistrationBlockVO.setSubject(programDetails.getSubject());
			programRegistrationBlockVO.setStartDate(programDetails.getStartDate());
			programRegistrationBlockVO.setProgramStatus(programDetails.getProgramStatus());

			// Populate Empty values for registration
			programRegistrationBlockVO.setEndDate(EMPTY_VALUE);
			programRegistrationBlockVO.setDegreeClass(EMPTY_VALUE);
			programRegistrationBlockVO.setGPA(EMPTY_VALUE);

			
			LearnerPIIVO learnerPIIVO = digitalCredentialDAO.getLearnerInfo(createHash(learnerSSN));
			UserVO learnerUserVO=userManagementUtility.getLearnerInfo(learnerPIIVO.getUserId());
			learnerPIIVO=populateLearnerPII(learnerUserVO);
			// Add learnerId into PII
			learnerPIIVO.setLearnerID(learnerId.trim());

			// Encrypt PII with learner public key
			
			byte[] learnerPIIBytes = serialize(learnerPIIVO);
			ByteBuffer learnerPIIBuffer = ByteBuffer.wrap(learnerPIIBytes);
			ByteBuffer encryptedPII = securityUtility.encryptData(learnerSSN.substring(6), learnerPIIBuffer);

			byte[] encryptedPIINBytes = encryptedPII.array();

			// Create Learner PII Hash
			byte[] PIIHash = getLearnerPIIHash(learnerPIIVO);

			programRegistrationBlockVO.setProgramStatus(ProgramStatus.Registered.name());
			programRegistrationBlockVO.setPIIHash(PIIHash);
			SignedRegistrationVO signedRegistrationVO = new SignedRegistrationVO();
			programRegistrationBlockVO.setEncryptedPII(encryptedPIINBytes);
			VerificationVO verificationVO = digitalCredentialDAO.addProgramRegistration(programRegistrationBlockVO);

			ByteBuffer digitalSignature = securityUtility.sign(programDetails.getUniversityEIN(),
					verificationVO.getDigest());
			verificationVO.setIsCourseRecord("N");
			digitalCredentialDAO.addVerification(verificationVO, digitalSignature);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Update program registration
	@RequestMapping(value = "/updateRegistration", method = RequestMethod.POST)

	public ModelAndView updateRegistration(
			@ModelAttribute("programDetails") ProgramRegistrationBlockVO programDetails) {
		ModelAndView modelAndView = new ModelAndView("universityHome");
		try {

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();

			byte[] PIIHash = Base64.getDecoder().decode(programDetails.getPIIHashString().trim());

			byte[] encryptedPII = Base64.getDecoder().decode(programDetails.getEncryptedPIIString().trim());
			programDetails.setPIIHash(PIIHash);
			programDetails.setEncryptedPII(encryptedPII);
			VerificationVO verificationVO = digitalCredentialDAO.updateProgramRegistration(programDetails);

			SecurityUtility securityUtility = new SecurityUtility();
			ByteBuffer digitalSignature = securityUtility.sign(programDetails.getUniversityEIN(),
					verificationVO.getDigest());
			verificationVO.setIsCourseRecord("N");
			verificationVO.setPIIHash(PIIHash);
			digitalCredentialDAO.updateVerification(verificationVO, digitalSignature);
			SignInVO signInVO = new SignInVO();
			String userId = programDetails.getUserId();
			signInVO.setUserId(userId);
			modelAndView.addObject("userDetails", signInVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Search a program registration for updating completion status
	@RequestMapping(value = "/searchProgram", method = RequestMethod.POST)

	public ModelAndView searchProgram(@ModelAttribute("programDetails") ProgramRegistrationVO programDetails) {
		ModelAndView modelAndView = new ModelAndView("updateRegistration");
		try {

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			String learnerSSN = programDetails.getSSN();
			String learnerId = programDetails.getLearnerId();

			LearnerPIIVO learnerPIIVO = digitalCredentialDAO.getLearnerInfo(createHash(learnerSSN));
			UserVO learnerUserVO=userManagementUtility.getLearnerInfo(learnerPIIVO.getUserId());
			learnerPIIVO=populateLearnerPII(learnerUserVO);
			// Add learnerId into PII
			learnerPIIVO.setLearnerID(learnerId.trim());

			// Encrypt PII with learner public key
			byte[] learnerPIIBytes = serialize(learnerPIIVO);
			// Create Learner PII Hash
			byte[] PIIHash = getLearnerPIIHash(learnerPIIVO);
			programDetails.setPIIHash(PIIHash);

			SignedRegistrationVO signedRegistrationVO = digitalCredentialDAO.getCareerRegistration(
					programDetails.getUniversityEIN(), PIIHash, programDetails.getCareer(), programDetails.getSubject(),
					programDetails.getConcentration());

			SignInVO signInVO = new SignInVO();
			signInVO.setUserId(programDetails.getUniversityUserId());
			byte[] PIIHashBytes = signedRegistrationVO.getProgramRegistrationBlockVO().getPIIHash();
			byte[] encryptedPIIBytes = signedRegistrationVO.getProgramRegistrationBlockVO().getEncryptedPII();

			String PIIHashString = Base64.getEncoder().withoutPadding().encodeToString(PIIHashBytes);
			String encryptedPIIString = Base64.getEncoder().withoutPadding().encodeToString(encryptedPIIBytes);
			ProgramRegistrationBlockVO programRegistrationBlockVO = signedRegistrationVO
					.getProgramRegistrationBlockVO();
			programRegistrationBlockVO.setPIIHashString(PIIHashString);
			programRegistrationBlockVO.setEncryptedPIIString(encryptedPIIString);

			programRegistrationBlockVO.setUserId(programDetails.getUniversityUserId());
			modelAndView.addObject("programRegistrationBlockVO", programRegistrationBlockVO);
			modelAndView.addObject("universityDetails", signInVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Add course completion for learner
	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)

	public ModelAndView addCourse(@ModelAttribute("programDetails") ProgramRegistrationVO programDetails) {
		ModelAndView modelAndView = new ModelAndView("universityHome");
		try {

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			String learnerSSN = programDetails.getSSN();
			String learnerId = programDetails.getLearnerId();

			ProgramRegistrationBlockVO programRegistrationBlockVO = new ProgramRegistrationBlockVO();
			programRegistrationBlockVO.setUniversityEIN(programDetails.getUniversityEIN());
			programRegistrationBlockVO.setUniversityName(programDetails.getUniversityName());
			programRegistrationBlockVO.setCareer(programDetails.getCareer());
			programRegistrationBlockVO.setConcentration(programDetails.getConcentration());
			programRegistrationBlockVO.setSubject(programDetails.getSubject());
			programRegistrationBlockVO.setStartDate(programDetails.getStartDate());
			programRegistrationBlockVO.setEndDate(programDetails.getEndDate());
			programRegistrationBlockVO.setEndDate(programDetails.getEndDate());
			programRegistrationBlockVO.setGPA(programDetails.getGPA());
			programRegistrationBlockVO.setCourseGrade(programDetails.getCourseGrade());
			programRegistrationBlockVO.setCourse(programDetails.getCourse());

			LearnerPIIVO learnerPIIVO = digitalCredentialDAO.getLearnerInfo(createHash(learnerSSN));
			UserVO learnerUserVO=userManagementUtility.getLearnerInfo(learnerPIIVO.getUserId());
			learnerPIIVO=populateLearnerPII(learnerUserVO);
			learnerPIIVO.setLearnerID(learnerId.trim());
		

			// Encrypt PII with learner public key
			byte[] learnerPIIBytes = serialize(learnerPIIVO);
			ByteBuffer learnerPIIBuffer = ByteBuffer.wrap(learnerPIIBytes);
			ByteBuffer encryptedPII = securityUtility.encryptData(learnerSSN.substring(6), learnerPIIBuffer);

			byte[] encryptedPIINBytes = encryptedPII.array();

			// Create Learner PII Hash
			byte[] PIIHash = getLearnerPIIHash(learnerPIIVO);
			
			programRegistrationBlockVO.setPIIHash(PIIHash);

			SignedRegistrationVO signedRegistrationVO = new SignedRegistrationVO();
			programRegistrationBlockVO.setEncryptedPII(encryptedPIINBytes);
			CourseVerificationVO courseVerificationVO = null;
			try {
				courseVerificationVO = digitalCredentialDAO.getCourseListInfo(programDetails.getUniversityEIN(),
						PIIHash, programDetails.getCareer(), programDetails.getSubject(),
						programDetails.getConcentration());

				courseVerificationVO = digitalCredentialDAO.updateCourseList(programRegistrationBlockVO);

			} catch (NoSuchElementException ex) {
				courseVerificationVO = digitalCredentialDAO.addNewCourseList(programRegistrationBlockVO);

			}

			ByteBuffer digitalSignature = securityUtility.sign(programDetails.getUniversityEIN(),
					courseVerificationVO.getDigest());

			digitalCredentialDAO.addCourseVerification(courseVerificationVO, digitalSignature);
			SignInVO signInVO = new SignInVO();
			String userId = programDetails.getUniversityUserId();
			signInVO.setUserId(userId);
			modelAndView.addObject("userDetails", signInVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Show grant transcript access screen
	@RequestMapping(value = "/showGrantTranscriptAccess", method = RequestMethod.GET)
	public ModelAndView showGrantTranscriptAccess(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("grantTranscriptAccess");
		try {
			String userId = request.getParameter("userId");

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			SearchLearnerRecordsVO searchLearnerRecordsVO = new SearchLearnerRecordsVO();

			searchLearnerRecordsVO.setUserId(userId);

			modelAndView.addObject("searchLearnerRecords", searchLearnerRecordsVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	/*
	 * Grant Certificate Access to an organization encrypting Learner PII with
	 * organization public key
	 */
	@RequestMapping(value = "/createCertificateAccess", method = RequestMethod.POST)

	public ModelAndView createCertificateAccess(
			@ModelAttribute("searchLearnerRecords") SearchLearnerRecordsVO searchLearnerRecords) {
		ModelAndView modelAndView = new ModelAndView("learnerHome");

		try {
			String userId = searchLearnerRecords.getUserId();
			String universityEIN = searchLearnerRecords.getUniversityEIN();
			String organizationEIN = searchLearnerRecords.getOrganizationEIN();
			String career = searchLearnerRecords.getCareer();
			String subject = searchLearnerRecords.getSubject();
			String concentration = searchLearnerRecords.getConcentration();
			String learnerId = searchLearnerRecords.getLearnerId();

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			// Get Learner PII from the Learner table

			LearnerPIIVO learnerPII = digitalCredentialDAO.getLearnerInfo(createHash(userVO.getSSN()));
			UserVO learnerUserVO=userManagementUtility.getLearnerInfo(learnerPII.getUserId());
			learnerPII=populateLearnerPII(learnerUserVO);
			// Add learnerId into PII
			learnerPII.setLearnerID(learnerId.trim());
			// Construct Learner PIIHash
			byte[] learnerPIIBytes = serialize(learnerPII);
			byte[] PIIHash = getLearnerPIIHash(learnerPII);
			String PIIHashString = Base64.getEncoder().withoutPadding().encodeToString(PIIHash);

			LearnerBlobsVO learnerBlob = digitalCredentialDAO.getEncryptedPII(universityEIN, career, subject,
					concentration, PIIHash);

			ByteBuffer learnerPIIBuffer = ByteBuffer.wrap(learnerBlob.getEncryptedPII());
			// Decrypt Learner PII

			ByteBuffer decryptedLearnerPII = securityUtility.decryptData(userVO.getSSN().substring(6),
					learnerPIIBuffer);

			// Encrypt learner PII with the organization public key

			ByteBuffer organizationEncryptedPII = securityUtility.encryptData(organizationEIN, decryptedLearnerPII);

			// Insert Organization-encrypted PII record in QLDB

			String id = organizationEIN + System.currentTimeMillis();
			byte[] idBytes = id.getBytes(StandardCharsets.UTF_8);
			String idString = Base64.getEncoder().withoutPadding().encodeToString(idBytes);
			digitalCredentialDAO.addOrganizationPII(idString, organizationEIN, universityEIN,
					organizationEncryptedPII.array(), PIIHash, career, subject, concentration);

			modelAndView.addObject("learnerDetails", userVO);

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
		return modelAndView;
	}

	/*
	 * Grant Transcript Access to an organization encrypting Learner PII with
	 * Organization public key
	 */
	@RequestMapping(value = "/createTranscriptAccess", method = RequestMethod.POST)

	public ModelAndView createTranscriptAccess(
			@ModelAttribute("searchLearnerRecords") SearchLearnerRecordsVO searchLearnerRecords) {
		ModelAndView modelAndView = new ModelAndView("learnerHome");

		try {
			String userId = searchLearnerRecords.getUserId();
			String universityEIN = searchLearnerRecords.getUniversityEIN();
			String organizationEIN = searchLearnerRecords.getOrganizationEIN();
			String career = searchLearnerRecords.getCareer();
			String subject = searchLearnerRecords.getSubject();
			String concentration = searchLearnerRecords.getConcentration();
			String learnerId = searchLearnerRecords.getLearnerId();

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			// Get Learner PII from the Learner table

		
			LearnerPIIVO learnerPII = digitalCredentialDAO.getLearnerInfo(createHash(userVO.getSSN()));
			UserVO learnerUserVO=userManagementUtility.getLearnerInfo(learnerPII.getUserId());
			learnerPII=populateLearnerPII(learnerUserVO);
			// Add learnerId into PII
			learnerPII.setLearnerID(learnerId.trim());
		

			// Construct Learner PIIHash
			byte[] learnerPIIBytes = serialize(learnerPII);
			byte[] PIIHash = getLearnerPIIHash(learnerPII);
			String PIIHashString = Base64.getEncoder().withoutPadding().encodeToString(PIIHash);

			LearnerBlobsVO learnerBlob = digitalCredentialDAO.getTranscriptEncryptedPII(universityEIN, career, subject,
					concentration, PIIHash);

			ByteBuffer learnerPIIBuffer = ByteBuffer.wrap(learnerBlob.getEncryptedPII());
			// Decrypt Learner PII

			ByteBuffer decryptedLearnerPII = securityUtility.decryptData(userVO.getSSN().substring(6),
					learnerPIIBuffer);

			// Encrypt learner PII with the organization public key

			ByteBuffer organizationEncryptedPII = securityUtility.encryptData(organizationEIN, decryptedLearnerPII);

			// Insert Organization-encrypted PII record in QLDB

			String id = organizationEIN + System.currentTimeMillis();
			byte[] idBytes = id.getBytes(StandardCharsets.UTF_8);
			String idString = Base64.getEncoder().withoutPadding().encodeToString(idBytes);
			digitalCredentialDAO.addOrganizationTranscriptPII(idString, organizationEIN, universityEIN,
					organizationEncryptedPII.array(), PIIHash, career, subject, concentration);

			modelAndView.addObject("learnerDetails", userVO);

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
		return modelAndView;
	}

	// Search Learner Certificate
	@RequestMapping(value = "/searchLearnerCredential", method = RequestMethod.GET)
	public ModelAndView searchLearnerCredential(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("showCredentialSearch");
		try {
			String userId = request.getParameter("userId");

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			SearchLearnerRecordsVO searchLearnerRecordsVO = new SearchLearnerRecordsVO();

			searchLearnerRecordsVO.setUserId(userId);

			modelAndView.addObject("searchLearnerRecords", searchLearnerRecordsVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Show screen to search granted certificates for an organization
	@RequestMapping(value = "/searchGrantedCertificate", method = RequestMethod.GET)
	public ModelAndView searchGrantedCertificate(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("findGrantedCertificate");
		try {
			String userId = request.getParameter("userId");

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getOrganizationInfo(userId);
			SearchLearnerRecordsVO searchLearnerRecordsVO = new SearchLearnerRecordsVO();

			searchLearnerRecordsVO.setUserId(userId);

			modelAndView.addObject("searchLearnerRecords", searchLearnerRecordsVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Find all granted certificates for an organization
	@RequestMapping(value = "/viewGrantedCredentials", method = RequestMethod.GET)
	public ModelAndView viewGrantedCredentials(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("grantedCertificateList");
		try {
			String userId = request.getParameter("userId");

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getOrganizationInfo(userId);
			SearchLearnerRecordsVO searchLearnerRecordsVO = new SearchLearnerRecordsVO();
			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();

			ArrayList<GrantedCredentialVO> grantList = digitalCredentialDAO
					.getOrganizationGrantedCertificates(userVO.getEIN());

			searchLearnerRecordsVO.setUserId(userId);
			modelAndView.addObject("userDetails", userVO);
			modelAndView.addObject("grantList", grantList);

			modelAndView.addObject("searchLearnerRecords", searchLearnerRecordsVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Show screen to search granted transcripts for an organization
	@RequestMapping(value = "/viewGrantedTranscripts", method = RequestMethod.GET)
	public ModelAndView viewGrantedTranscripts(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("grantedTranscriptList");
		try {
			String userId = request.getParameter("userId");

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getOrganizationInfo(userId);
			SearchLearnerRecordsVO searchLearnerRecordsVO = new SearchLearnerRecordsVO();
			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();

			ArrayList<GrantedCredentialVO> grantList = digitalCredentialDAO
					.getOrganizationGrantedTranscripts(userVO.getEIN());
			searchLearnerRecordsVO.setUserId(userId);
			modelAndView.addObject("userDetails", userVO);
			modelAndView.addObject("grantList", grantList);

			modelAndView.addObject("searchLearnerRecords", searchLearnerRecordsVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Find a granted certificate using grant-id for an organization
	@RequestMapping(value = "/findCertificate", method = RequestMethod.POST)

	public ModelAndView findCertificate(
			@ModelAttribute("searchLearnerRecords") SearchLearnerRecordsVO searchLearnerRecords) {
		ModelAndView modelAndView = new ModelAndView("viewCompleteRegistration");
		CompleteRegistrationVO completeRegistrationVO = new CompleteRegistrationVO();
		try {
			String userId = searchLearnerRecords.getUserId();
			String grantId = searchLearnerRecords.getGrantId();

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getOrganizationInfo(userId);
			// Get Credential details from OrganizationCertificatePII table

			GrantedCredentialVO credentialGrantVO = digitalCredentialDAO.findGrantedCertificate(grantId.trim());

			// 1. Decrypt Organizational encrypted PII using organizational key
			byte[] organizationalEncryptedPII = credentialGrantVO.getOrganizationEncryptedPII();
			ByteBuffer organizationalEncryptedPIIBuffer = ByteBuffer.wrap(organizationalEncryptedPII);
			completeRegistrationVO.setEncryptedPIIString(
					Base64.getEncoder().withoutPadding().encodeToString(organizationalEncryptedPII));

			ByteBuffer decryptedPII = securityUtility.decryptData(userVO.getEIN(), organizationalEncryptedPIIBuffer);
			LearnerPIIVO learnerPIIVO = (LearnerPIIVO) getObject(decryptedPII.array());
			completeRegistrationVO.setLearnerPIIVO(learnerPIIVO);
			byte[] learnerPIIBytes = serialize(learnerPIIVO);

			// 2. Create Learner PII Hash from the decrypted PII
			byte[] organizationalPIIHash = getLearnerPIIHash(learnerPIIVO);

			String PIIHashString1 = Base64.getEncoder().withoutPadding().encodeToString(organizationalPIIHash);

			// 3. Retrieve original Career Registration details and block metadata
			SignedRegistrationVO signedRegistrationVO = digitalCredentialDAO.getCareerRegistration(
					credentialGrantVO.getUniversityEIN(), organizationalPIIHash, credentialGrantVO.getCareer(),
					credentialGrantVO.getSubject(), credentialGrantVO.getConcentration());

			ProgramRegistrationBlockVO programRegistrationBlockVO = signedRegistrationVO
					.getProgramRegistrationBlockVO();
			completeRegistrationVO.setProgramRegistrationBlockVO(programRegistrationBlockVO);
			// 4. Get Original PII Hash value
			byte[] blockPIIHash = programRegistrationBlockVO.getPIIHash();
			completeRegistrationVO.setPIIHashString(Base64.getEncoder().withoutPadding().encodeToString(blockPIIHash));

			// 5. Compare Original-PII-Hash-value with the
			// organization-decrypted-PII-Hash-value
			boolean isPIIHashMatch = Arrays.equals(organizationalPIIHash, blockPIIHash);

			if (isPIIHashMatch == true) {
				completeRegistrationVO.setIsPIIHashMatched("Yes");
			} else {
				completeRegistrationVO.setIsPIIHashMatched("No");
			}

			// 6. Get University Digital Signature computed on the Digest
			ByteBuffer digitalSignature = signedRegistrationVO.getDigitalSgnature();
			completeRegistrationVO.setDigitalSignatureString(
					Base64.getEncoder().withoutPadding().encodeToString(digitalSignature.array()));
			// 7. Get Block Digest Value
			ByteBuffer digest = signedRegistrationVO.getVerificationVO().getDigest();
			completeRegistrationVO.setDigest(Base64.getEncoder().withoutPadding().encodeToString(digest.array()));
			// 8. Verify University Signature using block digest
			int universityEIN = Integer.parseInt(credentialGrantVO.getUniversityEIN());

			boolean isSignatureVerified = securityUtility.verifySign(universityEIN, digest, digitalSignature);

			if (isSignatureVerified == true) {
				completeRegistrationVO.setIsDigitalSignatureVerified("Yes");
			} else {
				completeRegistrationVO.setIsDigitalSignatureVerified("No");
			}

			completeRegistrationVO
					.setDigestTipAddress(signedRegistrationVO.getVerificationVO().getDigestTipAddress().getIonText());
			completeRegistrationVO.setDocumentId(signedRegistrationVO.getVerificationVO().getDocumentId());
			SignInVO signInVO = new SignInVO();
			completeRegistrationVO
					.setRevisionBlockAddress(signedRegistrationVO.getVerificationVO().getRevisionBlockAddress());
			GetRevisionResult revisionData = digitalCredentialDAO.getRevision(
					signedRegistrationVO.getVerificationVO().getDocumentId(),
					signedRegistrationVO.getVerificationVO().getRevisionBlockStranId(),
					signedRegistrationVO.getVerificationVO().getRevisionBlockSequenceNo(),
					signedRegistrationVO.getVerificationVO().getDigestTipAddress());

			boolean isBlockHashMatch = Verifier.verify(signedRegistrationVO.getVerificationVO().getRevisionBlockHash(),
					signedRegistrationVO.getVerificationVO().getDigest().array(), revisionData.getProof().getIonText());

			if (isBlockHashMatch == true) {
				completeRegistrationVO.setIsDataVerified("Yes");
			} else {
				completeRegistrationVO.setIsDataVerified("No");
			}
			signInVO.setUserId(userId);
			modelAndView.addObject("completeRegistrationDetails", completeRegistrationVO);
			modelAndView.addObject("userDetails", userVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Find a granted transcript using grant-id for an organization
	@RequestMapping(value = "/findTranscript", method = RequestMethod.POST)

	public ModelAndView findTranscript(
			@ModelAttribute("searchLearnerRecords") SearchLearnerRecordsVO searchLearnerRecords) {
		ModelAndView modelAndView = new ModelAndView("viewCompleteTranscript");

		try {
			String userId = searchLearnerRecords.getUserId();
			String grantId = searchLearnerRecords.getGrantId();

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getOrganizationInfo(userId);
			// Get Credential details from OrganizationCertificatePII table

			GrantedCredentialVO credentialGrantVO = digitalCredentialDAO.findGrantedTranscript(grantId.trim());
			CourseVerificationVO courseVerificationVO = digitalCredentialDAO.getCourseListInfo(
					credentialGrantVO.getUniversityEIN(), credentialGrantVO.getPIIHash(), credentialGrantVO.getCareer(),
					credentialGrantVO.getSubject(), credentialGrantVO.getConcentration());
			byte[] organizationalEncryptedPII = credentialGrantVO.getOrganizationEncryptedPII();
			courseVerificationVO.setOrganizationEncryptedPII(
					Base64.getEncoder().withoutPadding().encodeToString(organizationalEncryptedPII));

			// 1. Decrypt Organizational encrypted PII using organizational key

			ByteBuffer organizationalEncryptedPIIBuffer = ByteBuffer.wrap(organizationalEncryptedPII);

			ByteBuffer decryptedPII = securityUtility.decryptData(userVO.getEIN(), organizationalEncryptedPIIBuffer);
			LearnerPIIVO learnerPIIVO = (LearnerPIIVO) getObject(decryptedPII.array());
			courseVerificationVO.setLearnerPIIVO(learnerPIIVO);
			byte[] learnerPIIBytes = serialize(learnerPIIVO);

			// 2. Create Learner PII Hash from the decrypted PII
			byte[] organizationalPIIHash = getLearnerPIIHash(learnerPIIVO);

			String PIIHashString1 = Base64.getEncoder().withoutPadding().encodeToString(organizationalPIIHash);

			// 3. Retrieve original Career Registration details and block metadata

			// 4. Get Original PII Hash value
			byte[] blockPIIHash = courseVerificationVO.getPIIHash();
			courseVerificationVO.setPIIHashString(Base64.getEncoder().withoutPadding().encodeToString(blockPIIHash));

			// 5. Compare Original-PII-Hash-value with the
			// organization-decrypted-PII-Hash-value
			boolean isPIIHashMatch = Arrays.equals(organizationalPIIHash, blockPIIHash);

			if (isPIIHashMatch == true) {
				courseVerificationVO.setIsPIIHashMatched("Yes");
			} else {
				courseVerificationVO.setIsPIIHashMatched("No");
			}

			// 6. Get University Digital Signature computed on the Digest
			ByteBuffer digitalSignature = courseVerificationVO.getDigitalSgnature();
			courseVerificationVO.setDigitalSignatureString(
					Base64.getEncoder().withoutPadding().encodeToString(digitalSignature.array()));
			// 7. Get Block Digest Value
			ByteBuffer digest = courseVerificationVO.getDigest();
			courseVerificationVO.setDigestString(Base64.getEncoder().withoutPadding().encodeToString(digest.array()));
			// 8. Verify University Signature using block digest
			int universityEIN = Integer.parseInt(credentialGrantVO.getUniversityEIN());

			boolean isSignatureVerified = securityUtility.verifySign(universityEIN, digest, digitalSignature);

			if (isSignatureVerified == true) {
				courseVerificationVO.setIsDigitalSignatureVerified("Yes");
			} else {
				courseVerificationVO.setIsDigitalSignatureVerified("No");
			}

			courseVerificationVO.setDigestTipAddressString(courseVerificationVO.getDigestTipAddress().getIonText());
			courseVerificationVO.setDocumentId(courseVerificationVO.getDocumentId());
			SignInVO signInVO = new SignInVO();
			courseVerificationVO.setRevisionBlockAddress(courseVerificationVO.getRevisionBlockAddress());
			GetRevisionResult revisionData = digitalCredentialDAO.getRevision(courseVerificationVO.getDocumentId(),
					courseVerificationVO.getRevisionBlockStranId(), courseVerificationVO.getRevisionBlockSequenceNo(),
					courseVerificationVO.getDigestTipAddress());

			boolean isBlockHashMatch = Verifier.verify(courseVerificationVO.getRevisionBlockHash(),
					courseVerificationVO.getDigest().array(), revisionData.getProof().getIonText());

			if (isBlockHashMatch == true) {
				courseVerificationVO.setIsDataVerified("Yes");
			} else {
				courseVerificationVO.setIsDataVerified("No");
			}
			signInVO.setUserId(userId);
			modelAndView.addObject("courseDetails", courseVerificationVO);
			modelAndView.addObject("userDetails", userVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}

	}

	// Search Course for granting access to an organization

	@RequestMapping(value = "/searchCourse", method = RequestMethod.GET)
	public ModelAndView searchCourse(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("showCourseSearch");
		try {
			String userId = request.getParameter("userId");

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			SearchLearnerRecordsVO searchLearnerRecordsVO = new SearchLearnerRecordsVO();

			searchLearnerRecordsVO.setUserId(userId);

			modelAndView.addObject("searchLearnerRecords", searchLearnerRecordsVO);
			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Learner Grants certificate access to organizations
	@RequestMapping(value = "/showGrantCertificateAccess", method = RequestMethod.GET)
	public ModelAndView showGrantCertificateAccess(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("grantCertificateAccess");
		try {
			String userId = request.getParameter("userId");

			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			SearchLearnerRecordsVO searchLearnerRecordsVO = new SearchLearnerRecordsVO();

			searchLearnerRecordsVO.setUserId(userId);

			modelAndView.addObject("searchLearnerRecords", searchLearnerRecordsVO);

			return modelAndView;

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
	}

	// Show Learner program registration details
	@RequestMapping(value = "/findLearnerCredential", method = RequestMethod.POST)

	public ModelAndView findRegistration(
			@ModelAttribute("searchLearnerRecords") SearchLearnerRecordsVO searchLearnerRecords) {
		ModelAndView modelAndView = new ModelAndView("viewLearnerRegistration");
		// ModelAndView modelAndView = new ModelAndView("changeRegistration");
		try {
			String userId = searchLearnerRecords.getUserId();
			String EIN = searchLearnerRecords.getUniversityEIN();
			String career = searchLearnerRecords.getCareer();
			String subject = searchLearnerRecords.getSubject();
			String concentration = searchLearnerRecords.getConcentration();
			String learnerId = searchLearnerRecords.getLearnerId();

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			// Get Learner PII from the Learner table

			LearnerPIIVO learnerPII = digitalCredentialDAO.getLearnerInfo(createHash(userVO.getSSN()));
			UserVO learnerUserVO=userManagementUtility.getLearnerInfo(learnerPII.getUserId());
			learnerPII=populateLearnerPII(learnerUserVO);
			// Add learnerId into PII
			learnerPII.setLearnerID(learnerId.trim());

			// Construct Learner PIIHash
			byte[] learnerPIIBytes = serialize(learnerPII);
			byte[] PIIHash = getLearnerPIIHash(learnerPII);
			String PIIHashString1 = Base64.getEncoder().withoutPadding().encodeToString(PIIHash);

			LearnerBlobsVO learnerBlob = digitalCredentialDAO.getEncryptedPII(EIN, career, subject, concentration,
					PIIHash);

			ByteBuffer learnerPIIBuffer = ByteBuffer.wrap(learnerBlob.getEncryptedPII());
			// Decrypt PII

			ByteBuffer decryptedPII = securityUtility.decryptData(userVO.getSSN().substring(6), learnerPIIBuffer);
			LearnerPIIVO learnerPIIVO = (LearnerPIIVO) getObject(decryptedPII.array());
			// learnerPIIVO.setSSN(userVO.getSSN());
			String learnerSSN = learnerPIIVO.getSSN();
			String learnerEmail = learnerPIIVO.getEmail();

			// SignedRegistrationVO signedRegistrationVO =
			// digitalCredentialDAO.getCareerRegistration(EIN, PIIHash, career,
			// subject, concentration);

			SignedRegistrationVO signedRegistrationVO = digitalCredentialDAO.getCareerRegistration(EIN, PIIHash, career,
					subject, concentration);

			CompleteRegistrationVO completeRegistrationVO = new CompleteRegistrationVO();
			completeRegistrationVO.setLearnerPIIVO(learnerPIIVO);
			completeRegistrationVO.setProgramRegistrationBlockVO(signedRegistrationVO.getProgramRegistrationBlockVO());
			String PIIHashString = Base64.getEncoder().withoutPadding()
					.encodeToString(signedRegistrationVO.getProgramRegistrationBlockVO().getPIIHash());
			String encryptedPIIString = Base64.getEncoder().withoutPadding()
					.encodeToString(learnerBlob.getEncryptedPII());
			String digitalSignatureString = Base64.getEncoder().withoutPadding()
					.encodeToString(convertByteBufferToByteArray(signedRegistrationVO.getDigitalSgnature()));
			String digestString = Base64.getEncoder().withoutPadding()
					.encodeToString(convertByteBufferToByteArray(signedRegistrationVO.getVerificationVO().getDigest()));
			ValueHolder digestTipAddress = signedRegistrationVO.getVerificationVO().getDigestTipAddress();
			String documentId = signedRegistrationVO.getVerificationVO().getDocumentId();
			String revisionBlockAddress = signedRegistrationVO.getVerificationVO().getRevisionBlockAddress();
			completeRegistrationVO.setPIIHashString(PIIHashString);
			completeRegistrationVO.setEncryptedPIIString(encryptedPIIString);
			completeRegistrationVO.setDigitalSignatureString(digitalSignatureString);
			completeRegistrationVO.setDigest(digestString);
			completeRegistrationVO.setDigestTipAddress(digestTipAddress.getIonText());
			completeRegistrationVO.setDocumentId(documentId);
			completeRegistrationVO.setRevisionBlockAddress(revisionBlockAddress);

			modelAndView.addObject("completeRegistrationDetails", completeRegistrationVO);
			SignInVO signInVO = new SignInVO();
			signInVO.setUserId(userId);
			modelAndView.addObject("learnerDetails", userVO);

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();

			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
		return modelAndView;
	}

	// Show Learner a course details
	@RequestMapping(value = "/findLearnerCourse", method = RequestMethod.POST)

	public ModelAndView findLearnerCourse(
			@ModelAttribute("searchLearnerRecords") SearchLearnerRecordsVO searchLearnerRecords) {
		ModelAndView modelAndView = new ModelAndView("viewLearnerCourse");
		// ModelAndView modelAndView = new ModelAndView("changeRegistration");
		try {
			String userId = searchLearnerRecords.getUserId();
			String EIN = searchLearnerRecords.getUniversityEIN();
			String career = searchLearnerRecords.getCareer();
			String subject = searchLearnerRecords.getSubject();

			String concentration = searchLearnerRecords.getConcentration();
			String learnerId = searchLearnerRecords.getLearnerId();

			DigitalCredentialDAO digitalCredentialDAO = new DigitalCredentialDAO();
			SecurityUtility securityUtility = new SecurityUtility();
			UserManagementUtility userManagementUtility = new UserManagementUtility();
			UserVO userVO = userManagementUtility.getLearnerInfo(userId);
			// Get Learner PII from the Learner table

			
			LearnerPIIVO learnerPII = digitalCredentialDAO.getLearnerInfo(createHash(userVO.getSSN()));
			UserVO learnerUserVO=userManagementUtility.getLearnerInfo(learnerPII.getUserId());
			learnerPII=populateLearnerPII(learnerUserVO);
			// Add learnerId into PII
			learnerPII.setLearnerID(learnerId.trim());
		
			
			// Construct Learner PIIHash
			byte[] learnerPIIBytes = serialize(learnerPII);
			byte[] PIIHash = getLearnerPIIHash(learnerPII);
			String PIIHashString1 = Base64.getEncoder().withoutPadding().encodeToString(PIIHash);

			LearnerBlobsVO learnerBlob = digitalCredentialDAO.getEncryptedCoursePII(EIN, career, subject, concentration,
					PIIHash);

			ByteBuffer learnerPIIBuffer = ByteBuffer.wrap(learnerBlob.getEncryptedPII());
			// Decrypt PII

			ByteBuffer decryptedPII = securityUtility.decryptData(userVO.getSSN().substring(6), learnerPIIBuffer);
			LearnerPIIVO learnerPIIVO = (LearnerPIIVO) getObject(decryptedPII.array());
			// learnerPIIVO.setSSN(userVO.getSSN());
			String learnerSSN = learnerPIIVO.getSSN();
			String learnerEmail = learnerPIIVO.getEmail();

			CourseVerificationVO courseVerificationVO = digitalCredentialDAO.getCourseListInfo(EIN, PIIHash, career,
					subject, concentration);

			courseVerificationVO.setLearnerPIIVO(learnerPIIVO);

			String PIIHashString = Base64.getEncoder().withoutPadding()
					.encodeToString(courseVerificationVO.getPIIHash());
			String encryptedPIIString = Base64.getEncoder().withoutPadding()
					.encodeToString(courseVerificationVO.getEncryptedPII());
			String digitalSignatureString = Base64.getEncoder().withoutPadding()
					.encodeToString(convertByteBufferToByteArray(courseVerificationVO.getDigitalSgnature()));
			String digestString = Base64.getEncoder().withoutPadding()
					.encodeToString(convertByteBufferToByteArray(courseVerificationVO.getDigest()));
			ValueHolder digestTipAddress = courseVerificationVO.getDigestTipAddress();
			String documentId = courseVerificationVO.getDocumentId();
			String revisionBlockAddress = courseVerificationVO.getRevisionBlockAddress();
			courseVerificationVO.setPIIHashString(PIIHashString);
			courseVerificationVO.setEncryptedPIIString(encryptedPIIString);
			courseVerificationVO.setDigitalSignatureString(digitalSignatureString);
			courseVerificationVO.setDigestString(digestString);
			courseVerificationVO.setDigestTipAddressString(digestTipAddress.getIonText());
			courseVerificationVO.setDocumentId(documentId);
			courseVerificationVO.setRevisionBlockAddress(revisionBlockAddress);

			modelAndView.addObject("courseDetails", courseVerificationVO);
			SignInVO signInVO = new SignInVO();
			signInVO.setUserId(userId);
			modelAndView.addObject("learnerDetails", userVO);

		} catch (Exception e) {

			e.printStackTrace();
			String errorMsg = e.getMessage();

			modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMsg", errorMsg);

			return modelAndView;
		}
		return modelAndView;
	}

	// Create Message Hash
	public byte[] createDataHash(byte[] data) throws NoSuchAlgorithmException {

		MessageDigest md;
		byte[] hashValue = null;
		try {

			md = MessageDigest.getInstance("SHA-256");
			hashValue = md.digest(data);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
			throw e1;
		}
		return hashValue;
	}

	// Serialize Java Object
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		os.flush();
		out.close();
		return out.toByteArray();
	}

	// De-serialize Java object
	private static Object getObject(byte[] serializedBytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(serializedBytes);
		ObjectInput in = new ObjectInputStream(bis);
		return in.readObject();
	}

	public static byte[] convertByteBufferToByteArray(final ByteBuffer buffer) {
		byte[] arr = new byte[buffer.remaining()];
		buffer.get(arr);
		return arr;
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
	private byte[] getLearnerPIIHash(LearnerPIIVO learnerPIIVO) throws NoSuchAlgorithmException, IOException {
		LearnerIdVO learnerIdVO = new LearnerIdVO();
		learnerIdVO.setGivenName(learnerPIIVO.getGivenName());
		learnerIdVO.setLastName(learnerPIIVO.getLastName());
		learnerIdVO.setLearnerDOB(learnerPIIVO.getLearnerDOB());
		learnerIdVO.setSSN(learnerPIIVO.getSSN());
		byte[] learnerIdBytes = serialize(learnerIdVO);

		byte[] PIIHash = createDataHash(learnerIdBytes);
		return PIIHash;
	}
	private LearnerPIIVO populateLearnerPII(UserVO learnerUserVO) {
		LearnerPIIVO learnerPIIVO= new LearnerPIIVO();
		learnerPIIVO.setAddress(learnerUserVO.getAddress());
		learnerPIIVO.setEmail(learnerUserVO.getEmail());
		learnerPIIVO.setGivenName(learnerUserVO.getGivenName());
		learnerPIIVO.setLastName(learnerUserVO.getLastName());
		learnerPIIVO.setLearnerDOB(learnerUserVO.getLearnerDOB());
		learnerPIIVO.setPhone(learnerUserVO.getPhone());
		learnerPIIVO.setSSN(learnerUserVO.getSSN());
		learnerPIIVO.setUserId(learnerUserVO.getUserId());
		
		return learnerPIIVO;
		
		
		
		
	}
}
