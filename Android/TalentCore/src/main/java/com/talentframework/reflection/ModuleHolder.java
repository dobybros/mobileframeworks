package com.talentframework.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.talentframework.constants.TalentConstants;
import com.talentframework.talentexception.TalentException;
import com.talentframework.utils.ReflectionUtil;
import com.talentframework.utils.StringUtil;


public class ModuleHolder {
	private static final Logger logger = Logger.getLogger("ModuleHolder");
	private Class<?> moduleClass;
	
	private Map<String, CommandHolder> commandMap;
	
	public ModuleHolder(Class<?> sInterface) throws TalentException{
		this.moduleClass = sInterface;
		
		generateMethodHolders(sInterface);
	}
	
	private void generateMethodHolders(Class<?> clazz) throws TalentException{
		commandMap = new HashMap<String, CommandHolder>();
		Method[] methods = ReflectionUtil.getInterfaceMethods(clazz);
		System.out.println("<<=======================" + moduleClass.getName());
		for(Method method : methods){
			addMethod(method, new CommandHolder(method));
		}
		System.out.println("=======================>>" + moduleClass.getName());
	}

	private void addMethod(Method m, CommandHolder cmd) throws TalentException{
		String method = ReflectionUtil.generateMethodKey(m);
		System.out.println(StringUtil.getClassLastName(moduleClass) + "/" + method);
		if(!commandMap.containsKey(method)){
			commandMap.put(method, cmd);
		}else{
			String errorStr = m.getName() + " is duplicated! CommandCenter don't support overload!";
			System.out.println(errorStr);
			throw new TalentException(TalentConstants.CC_MODULE_IS_DUPLICATED.getCode(), errorStr);
		}
	}
	
	/**
	 * @param commandMap the commandMap to set
	 */
	public void setCommandMap(Map<String, CommandHolder> commandMap) {
		this.commandMap = commandMap;
	}

	/**
	 * @return the commandMap
	 */
	public Map<String, CommandHolder> getCommandMap() {
		return commandMap;
	}

	/**
	 * @return the moduleClass
	 */
	public Class<?> getModuleClass() {
		return moduleClass;
	}
}
