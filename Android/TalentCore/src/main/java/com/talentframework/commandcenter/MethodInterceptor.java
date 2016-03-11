/**
 * 
 */
package com.talentframework.commandcenter;

/**
 * @author lixinqiu
 *
 */
public interface MethodInterceptor {
	
	public Object methodInvocation(Command command);
}
