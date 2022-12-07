package com.malli.common;

public enum ToastrSuccessMessage {
	
	SAVING_SUCCESS("S0001","saved successfully"),
	SUCCESSFULLY_DELETED("S0002","Deleted Succesfully"),
	ASSIGNED_SUCCESSFULLY("S0003","Assigned Successfully"),
	SUCCESSFULLY_SENT_SMS("S0004","Successfully sent SMS"),
	SUCCESSFULLY_TO_SEND_OTP("S0005","Successfully sent OTP"),
	OTP_VERIFIED_SUCCESSFULLY("S0006","OTP verified successfully") 

	;

	private String message;
	private String code;

	ToastrSuccessMessage(String code, String message) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
