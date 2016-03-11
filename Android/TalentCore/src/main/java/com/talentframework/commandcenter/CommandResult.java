package com.talentframework.commandcenter;

import com.talentframework.talentexception.TalentException;

public class CommandResult{
	public enum ResultType {
		Success, Failed,
	}

	private ResultType resultType;

	private Object returnObject;

	private TalentException exception;
	
	private Command command;

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public TalentException getException() {
		return exception;
	}

	public void setException(TalentException exception) {
		this.exception = exception;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String toString() {
		return "";// "CommandResult: resultType=" + resultType + "; uri=" +
					// command.getUri() + "; cmdId=" + command.getId();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
