package com.talentframework.reflection;

import java.lang.reflect.Method;

public class CommandHolder {
	private Method method;

	private Class<?>[] params;

	private Class<?> returnValue;
	
	private Class<?>[] exceptions;
	
	public CommandHolder(Method method) {
		this.setMethod(method);
		
		params = method.getParameterTypes();
		
		returnValue = method.getReturnType();
		
		exceptions = method.getExceptionTypes();
	}

	public void setParams(Class<?>[] params) {
		this.params = params;
	}

	public Class<?>[] getParams() {
		return params;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}

	/**
	 * @param returnValue the returnValue to set
	 */
	public void setReturnValue(Class<?> returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * @return the returnValue
	 */
	public Class<?> getReturnValue() {
		return returnValue;
	}

	/**
	 * @param exceptions the exceptions to set
	 */
	public void setExceptions(Class<?>[] exceptions) {
		this.exceptions = exceptions;
	}

	/**
	 * @return the exceptions
	 */
	public Class<?>[] getExceptions() {
		return exceptions;
	}
	
	
}
