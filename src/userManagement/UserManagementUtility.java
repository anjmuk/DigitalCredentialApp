package userManagement;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;

import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersResponse;
import com.digitalcredentials.vo.SignInVO;

import com.digitalcredentials.vo.UserVO;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class UserManagementUtility {
	// Cognito connection details for the user pools
	private final static String clientId = "1undumbal89rvfgtq4ohlmih45";
	private final static String userPoolId = "us-east-1_qTiCv0uOW";
	private final static String learnerClientId = "5ciq2esu5ed6fpbun5h7hlvjbe";
	private final static String learnerUserPoolId = "us-east-1_1wHU4KpMS";
	private final static String organizationClientId = "409kj3a818imjse9m0vr5lhu0f";
	private final static String organizationUserPoolId = "us-east-1_KptepBYLF";
	private static CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.builder()
			.region(Region.US_EAST_1).build();
	private static CognitoIdentityClient cognitoclient = CognitoIdentityClient.builder().region(Region.US_EAST_1)
			.build();

	// Sign up an university user
	public static void signUp(UserVO signUpVO) {

		AttributeType attributeType = AttributeType.builder().name("email").value(signUpVO.getEmail()).build();

		List<AttributeType> attrs = new ArrayList<>();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("address").value(signUpVO.getAddress()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("phone_number").value(signUpVO.getPhone()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("name").value(signUpVO.getName()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("custom:EIN").value(signUpVO.getEIN()).build();
		attrs.add(attributeType);
		try {

			SignUpRequest signUpRequest = SignUpRequest.builder().userAttributes(attrs).username(signUpVO.getUserId())
					.clientId(clientId).password(signUpVO.getPassword()).build();

			identityProviderClient.signUp(signUpRequest);

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			throw e;
		}
	}

	// Sign up organization user
	public static void organizationSignUp(UserVO signUpVO) {

		AttributeType attributeType = AttributeType.builder().name("email").value(signUpVO.getEmail()).build();

		List<AttributeType> attrs = new ArrayList<>();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("address").value(signUpVO.getAddress()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("phone_number").value(signUpVO.getPhone()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("name").value(signUpVO.getName()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("custom:EIN").value(signUpVO.getEIN()).build();
		attrs.add(attributeType);
		try {

			SignUpRequest signUpRequest = SignUpRequest.builder().userAttributes(attrs).username(signUpVO.getUserId())
					.clientId(organizationClientId).password(signUpVO.getPassword()).build();

			identityProviderClient.signUp(signUpRequest);

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			throw e;
		}
	}

	// Sign up a learner
	public static void learnerSignUp(UserVO signUpVO) {
		List<AttributeType> attrs = new ArrayList<>();
		AttributeType attributeType = AttributeType.builder().name("email").value(signUpVO.getEmail()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("address").value(signUpVO.getAddress()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("phone_number").value(signUpVO.getPhone()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("given_name").value(signUpVO.getGivenName()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("family_name").value(signUpVO.getLastName()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("gender").value(signUpVO.getGender()).build();
		attrs.add(attributeType);
		attributeType = AttributeType.builder().name("custom:SSN").value(signUpVO.getSSN()).build();
		attrs.add(attributeType);
		try {

			SignUpRequest signUpRequest = SignUpRequest.builder().userAttributes(attrs).username(signUpVO.getUserId())
					.clientId(learnerClientId).password(signUpVO.getPassword()).build();

			identityProviderClient.signUp(signUpRequest);

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			throw e;
		}
	}

	// University login
	public static void initiateLogin(SignInVO signInVO) {

		try {
			final Map<String, String> authParameters = new HashMap<>();
			authParameters.put("USERNAME", signInVO.getUserId());
			authParameters.put("PASSWORD", signInVO.getPassword());
			final AdminInitiateAuthRequest adminInitiateAuthRequest = AdminInitiateAuthRequest.builder()
					.clientId(clientId).authFlow("ADMIN_NO_SRP_AUTH").userPoolId(userPoolId)
					.authParameters(authParameters).build();

			AdminInitiateAuthResponse adminInitiateAuthResponse = identityProviderClient
					.adminInitiateAuth(adminInitiateAuthRequest);

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			throw e;
		}
	}

	// Organization login
	public static void organizationLogin(SignInVO signInVO) {

		try {
			final Map<String, String> authParameters = new HashMap<>();
			authParameters.put("USERNAME", signInVO.getUserId());
			authParameters.put("PASSWORD", signInVO.getPassword());
			final AdminInitiateAuthRequest adminInitiateAuthRequest = AdminInitiateAuthRequest.builder()
					.clientId(organizationClientId).authFlow("ADMIN_NO_SRP_AUTH").userPoolId(organizationUserPoolId)
					.authParameters(authParameters).build();

			AdminInitiateAuthResponse adminInitiateAuthResponse = identityProviderClient
					.adminInitiateAuth(adminInitiateAuthRequest);

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			throw e;
		}
	}

	// Learner login
	public static void initiateLearnerLogin(SignInVO signInVO) {

		try {
			final Map<String, String> authParameters = new HashMap<>();
			authParameters.put("USERNAME", signInVO.getUserId());
			authParameters.put("PASSWORD", signInVO.getPassword());
			final AdminInitiateAuthRequest adminInitiateAuthRequest = AdminInitiateAuthRequest.builder()
					.clientId(learnerClientId).authFlow("ADMIN_NO_SRP_AUTH").userPoolId(learnerUserPoolId)
					.authParameters(authParameters).build();

			AdminInitiateAuthResponse adminInitiateAuthResponse = identityProviderClient
					.adminInitiateAuth(adminInitiateAuthRequest);

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			throw e;
		}
	}

	// Get Learner user info
	public UserVO getLearnerInfo(String userId) {
		UserVO userVO = null;
		try {

			AdminGetUserRequest userRequest = AdminGetUserRequest.builder().username(userId.trim())
					.userPoolId(learnerUserPoolId).build();

			AdminGetUserResponse userResult = identityProviderClient.adminGetUser(userRequest);
			userVO = new UserVO();
			userVO.setUserId(userResult.username());

			List<AttributeType> userAttributes = userResult.userAttributes();

			ListIterator<AttributeType> it = userAttributes.listIterator();
			while (it.hasNext()) {
				AttributeType attribute = it.next();
				if (attribute.name().equalsIgnoreCase("given_name")) {
					userVO.setGivenName(attribute.value());
				}
				if (attribute.name().equalsIgnoreCase("family_name")) {
					userVO.setLastName(attribute.value());
				}
				if (attribute.name().equalsIgnoreCase("gender")) {
					userVO.setGender(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("email")) {
					userVO.setEmail(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("phone_number")) {
					userVO.setPhone(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("address")) {
					userVO.setAddress(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("custom:SSN")) {
					userVO.setSSN(attribute.value());
				}
			}

			return userVO;

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());

		}
		return userVO;
	}

	// Get university user info

	public UserVO getUniversityInfo(String userId) {
		UserVO userVO = null;
		try {

			AdminGetUserRequest userRequest = AdminGetUserRequest.builder().username(userId.trim())
					.userPoolId(userPoolId).build();

			AdminGetUserResponse userResult = identityProviderClient.adminGetUser(userRequest);
			userVO = new UserVO();
			userVO.setUserId(userResult.username());

			List<AttributeType> userAttributes = userResult.userAttributes();

			ListIterator<AttributeType> it = userAttributes.listIterator();
			while (it.hasNext()) {
				AttributeType attribute = it.next();
				if (attribute.name().equalsIgnoreCase("name")) {
					userVO.setName(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("email")) {
					userVO.setEmail(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("phone_number")) {
					userVO.setPhone(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("address")) {
					userVO.setAddress(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("custom:EIN")) {
					userVO.setEIN(attribute.value());
				}
			}

			return userVO;

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());

		}
		return userVO;
	}

	// Get organization user info
	public UserVO getOrganizationInfo(String userId) {
		UserVO userVO = null;
		try {

			AdminGetUserRequest userRequest = AdminGetUserRequest.builder().username(userId.trim())
					.userPoolId(organizationUserPoolId).build();

			AdminGetUserResponse userResult = identityProviderClient.adminGetUser(userRequest);
			userVO = new UserVO();
			userVO.setUserId(userResult.username());

			List<AttributeType> userAttributes = userResult.userAttributes();

			ListIterator<AttributeType> it = userAttributes.listIterator();
			while (it.hasNext()) {
				AttributeType attribute = it.next();
				if (attribute.name().equalsIgnoreCase("name")) {
					userVO.setName(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("email")) {
					userVO.setEmail(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("phone_number")) {
					userVO.setPhone(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("address")) {
					userVO.setAddress(attribute.value());
				} else if (attribute.name().equalsIgnoreCase("custom:EIN")) {
					userVO.setEIN(attribute.value());
				}
				userVO.setUserId(userId);
			}

			return userVO;

		} catch (CognitoIdentityProviderException e) {
			System.err.println(e.awsErrorDetails().errorMessage());

		}
		return userVO;
	}

}
