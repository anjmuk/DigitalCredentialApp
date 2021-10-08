package security;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.amazonaws.services.kms.model.CreateAliasRequest;
import com.amazonaws.services.kms.model.CreateAliasResult;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.CreateKeyRequest;
import com.amazonaws.services.kms.model.CreateKeyResult;
import com.amazonaws.services.kms.model.CustomerMasterKeySpec;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.services.kms.model.EncryptionAlgorithmSpec;
import com.amazonaws.services.kms.model.KeyUsageType;
import com.amazonaws.services.kms.model.MessageType;
import com.amazonaws.services.kms.model.PutKeyPolicyRequest;
import com.amazonaws.services.kms.model.SignRequest;
import com.amazonaws.services.kms.model.SignResult;
import com.amazonaws.services.kms.model.VerifyRequest;
import com.amazonaws.services.kms.model.VerifyResult;

public class SecurityUtility {
	public static AWSKMS kmsClient = AWSKMSClientBuilder.standard().build();

	// Create a signing key pair for the university
	public void createSignKeyPair(String keyCreatorId, String keyCreatorName) {

		String assymKeyPairDesc = "Encryption Asymmetric Pair for " + keyCreatorName;
		String assymSignPairAlias = "alias/" + "SIGN_PAIR" + "_" + keyCreatorId;
		// Create CMK
		try {

			CreateKeyRequest req = new CreateKeyRequest().withDescription(assymKeyPairDesc);
			req.setKeyUsage(KeyUsageType.SIGN_VERIFY);
			CustomerMasterKeySpec customerMasterKeySpec = CustomerMasterKeySpec.ECC_NIST_P521;
			req.setCustomerMasterKeySpec(customerMasterKeySpec);
			CreateKeyResult result = kmsClient.createKey(req);
			String assymKeyARN = result.getKeyMetadata().getArn();
			// Create an alias for a CMK
			CreateAliasRequest createAliasRequest = new CreateAliasRequest().withAliasName(assymSignPairAlias)
					.withTargetKeyId(assymKeyARN);

			kmsClient.createAlias(createAliasRequest);
			// Set a key policy for a CMK
			String policyName = "default";
			String policy = "{" + "  \"Version\": \"2012-10-17\"," + "  \"Statement\": [{"
					+ "    \"Sid\": \"Enable IAM User Permissions\"," + "    \"Effect\": \"Allow\","
					+ "    \"Principal\": {\"AWS\": \"arn:aws:iam::964602999966:root\"}," + "    \"Action\": ["
					+ "      \"kms:*\"" + "    ]," + "    \"Resource\": \"*\"" + "  }]" + "}";

			PutKeyPolicyRequest policyRequest = new PutKeyPolicyRequest().withKeyId(assymKeyARN).withPolicy(policy)
					.withPolicyName(policyName);
			kmsClient.putKeyPolicy(policyRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

	}

	// Create public-private key pair
	public void createEncryptionKeys(String keyCreatorId, String keyCreatorName) {

		String assymKeyPairDesc = "Encryption Asymmetric Pair for " + keyCreatorName;
		String assymKeyPairAlias = "alias/" + "ENCRYPT_ASYM_PAIR_" + keyCreatorId;
		// Create CMK
		CreateKeyRequest req = new CreateKeyRequest().withDescription(assymKeyPairDesc)
				.withCustomerMasterKeySpec(CustomerMasterKeySpec.RSA_4096).withKeyUsage(KeyUsageType.ENCRYPT_DECRYPT);

		CreateKeyResult result = kmsClient.createKey(req);
		String cmkKeyARN = result.getKeyMetadata().getKeyId();

		System.out.println("CMK ARN " + cmkKeyARN);
		// Create an alias for a CMK
		CreateAliasRequest createAliasRequest = new CreateAliasRequest().withAliasName(assymKeyPairAlias)
				.withTargetKeyId(cmkKeyARN);

		CreateAliasResult cmkAliasResult = kmsClient.createAlias(createAliasRequest);
		System.out.println("CMK ALIAS CREATED " + cmkAliasResult.toString());
		// Set a key policy for a CMK
		String policyName = "default";
		String policy = "{" + "  \"Version\": \"2012-10-17\"," + "  \"Statement\": [{"
				+ "    \"Sid\": \"Enable IAM User Permissions\"," + "    \"Effect\": \"Allow\","
				+ "    \"Principal\": {\"AWS\": \"arn:aws:iam::964602999966:root\"}," + "    \"Action\": ["
				+ "      \"kms:*\"" + "    ]," + "    \"Resource\": \"*\"" + "  }]" + "}";

		PutKeyPolicyRequest policyRequest = new PutKeyPolicyRequest().withKeyId(cmkKeyARN).withPolicy(policy)
				.withPolicyName(policyName);
		kmsClient.putKeyPolicy(policyRequest);

	}

	// Encrypt data
	public ByteBuffer encryptData(String keyCreatorId, ByteBuffer plaintext) {

		String assymKeyPairAlias = "alias/" + "ENCRYPT_ASYM_PAIR_" + keyCreatorId;

		// ByteBuffer encryptedPrivateKey = keyResult.getPrivateKeyCiphertextBlob();
		EncryptRequest encryptRequest = new EncryptRequest()
				.withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256).withKeyId(assymKeyPairAlias)
				.withPlaintext(plaintext);
		EncryptResult encryptResult = kmsClient.encrypt(encryptRequest);

		ByteBuffer encryptedData = encryptResult.getCiphertextBlob();

		return encryptedData;
	}

	// Decrypt data
	public ByteBuffer decryptData(String keyCreatorId, ByteBuffer ciphertextBlob) {

		String assymKeyPairAlias = "alias/" + "ENCRYPT_ASYM_PAIR_" + keyCreatorId;

		DecryptRequest decryptRequest = new DecryptRequest()
				.withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256).withKeyId(assymKeyPairAlias)
				.withCiphertextBlob(ciphertextBlob);

		DecryptResult decryptResult = kmsClient.decrypt(decryptRequest);
		ByteBuffer decryptedData = decryptResult.getPlaintext();

		return decryptedData;
	}

	// Sign a byte stream
	public ByteBuffer sign(String keyCreatorId, ByteBuffer messageBuffer) {

		AWSKMS kmsClient = AWSKMSClientBuilder.standard().build();

		String assymSignPairAlias = "alias/" + "SIGN_PAIR" + "_" + keyCreatorId;

		System.out.println("Sign key");
		SignRequest signRequest = new SignRequest().withKeyId(assymSignPairAlias).withMessage(messageBuffer)
				.withMessageType(MessageType.RAW).withSigningAlgorithm("ECDSA_SHA_512");
		SignResult signResult = kmsClient.sign(signRequest);
		ByteBuffer signature = signResult.getSignature();
		return signature;
	}

	// Verify signature
	public Boolean verifySign(int keyCreatorId, ByteBuffer digest, ByteBuffer signature) {

		AWSKMS kmsClient = AWSKMSClientBuilder.standard().build();
		String assymSignPairAlias = "alias/" + "SIGN_PAIR" + "_" + keyCreatorId;
		System.out.println("alias " + assymSignPairAlias);
		VerifyRequest verifyRequest = new VerifyRequest().withKeyId(assymSignPairAlias).withMessageType(MessageType.RAW)
				.withMessage(digest).withSigningAlgorithm("ECDSA_SHA_512").withSignature(signature);

		VerifyResult verifyResult = kmsClient.verify(verifyRequest);
		return verifyResult.getSignatureValid();
	}

	// Create Hash value
	public byte[] createHash(String data) {
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