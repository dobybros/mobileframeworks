package com.talentframework.constants;

import com.talentframework.talentexception.TalentException;


public enum TalentConstants {
	CC_COMMAND_NOT_FOUND(1), 
	CC_COMMAND_HASNT_STARTUP(2), 
	CC_COMMAND_URI_IS_MALFORMED(3), 
	CC_COMMAND_ILLEGAL_PARAM(4),
	CC_MODULE_IS_DUPLICATED(5),
	CC_COMMAND_ILLEGAL_ACCESS(6),
	CC_COMMAND_EXCEPTION_OCCURED(7),
	CC_COMMAND_UNKNOWN_ERROR(8),
	CC_NOTIFICATIONEX_UNKNOWN_ERROR(9),
	;

	private int code;

	TalentConstants(int code) {
		this.code = TalentException.ERRORCODE_CORE + code;
	}

	public int getCode() {
		return code;
	}
}
