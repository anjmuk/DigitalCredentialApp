package com.digitalcredentials.vo;

import java.nio.ByteBuffer;
//Encryption Result
public class EncryptionResultVO {
private ByteBuffer encryptedPrivateKey = null;
private ByteBuffer encryptedData = null;
public ByteBuffer getEncryptedPrivateKey() {
	return encryptedPrivateKey;
}
public void setEncryptedPrivateKey(ByteBuffer encryptedPrivateKey) {
	this.encryptedPrivateKey = encryptedPrivateKey;
}
public ByteBuffer getEncryptedData() {
	return encryptedData;
}
public void setEncryptedData(ByteBuffer encryptedData) {
	this.encryptedData = encryptedData;
}
}
