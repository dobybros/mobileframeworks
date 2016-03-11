package com.talentframework.talentexception;


public class TalentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2226649065352482975L;
	
	static final int RANGE = 10000;
	public static final int ERRORCODE_CORE = 0 + RANGE;
	public static final int ERRORCODE_NETWORK = ERRORCODE_CORE + RANGE;
	public static final int ERRORCODE_DATA = ERRORCODE_NETWORK + RANGE;
	public static final int ERRORCODE_LOGIC = ERRORCODE_DATA + RANGE;
	public static final int ERRORCODE_UI = ERRORCODE_LOGIC + RANGE;
	
	private boolean isServerException;
	private int code;
	
	private String i18nKey;
	private Integer serverCode;
	
	public TalentException(String aName, String i18nKey){
		super(aName);
		this.i18nKey = i18nKey;
	}
	
	public TalentException(String aName, Integer serverCode){
		super(aName);
		this.serverCode = serverCode;
	}
	
	public TalentException(int code, String message) {
		super(message);
		this.code = code;
		System.out.println("code=" + code + ";message=" + message);
	}

	public TalentException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		System.out.println("code=" + code + ";message=" + message);
	}
	
	public TalentException(int code, Throwable cause) {
		super(cause);
		this.code = code;
		System.out.println("code=" + code);
	}

	public int getCode(){
		return code;
	}

	public boolean isServerException() {
		return isServerException;
	}

	public void setServerException(boolean isServerException) {
		this.isServerException = isServerException;
	}
	
	public String getI18nKey() {
		return i18nKey;
	}

	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public Integer getServerCode() {
		return serverCode;
	}

	public void setServerCode(Integer serverCode) {
		this.serverCode = serverCode;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
