package com.digitalcredentials.vo;

import java.io.Serializable;
import java.nio.ByteBuffer;
//Registration with verification
public class SignedRegistrationVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private ProgramRegistrationBlockVO programRegistrationBlockVO ;
	private VerificationVO VerificationVO;
	private ByteBuffer digitalSgnature;
	public ProgramRegistrationBlockVO getProgramRegistrationBlockVO() {
		return programRegistrationBlockVO;
	}
	public void setProgramRegistrationBlockVO(ProgramRegistrationBlockVO programRegistrationBlockVO) {
		this.programRegistrationBlockVO = programRegistrationBlockVO;
	}
	public VerificationVO getVerificationVO() {
		return VerificationVO;
	}
	public void setVerificationVO(VerificationVO verificationVO) {
		VerificationVO = verificationVO;
	}
	public ByteBuffer getDigitalSgnature() {
		return digitalSgnature;
	}
	public void setDigitalSgnature(ByteBuffer digitalSgnature) {
		this.digitalSgnature = digitalSgnature;
	}
	
}
