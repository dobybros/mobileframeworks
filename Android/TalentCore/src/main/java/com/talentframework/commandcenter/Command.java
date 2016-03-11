package com.talentframework.commandcenter;

import java.util.ArrayList;
import java.util.List;

import com.talentframework.constants.TalentConstants;
import com.talentframework.talentexception.TalentException;


public class Command extends Object{
	
	public static final int COMMAND_STATUS_UNKOWN = 0;
	public static final int COMMAND_STATUS_QUEUED = 1;
	public static final int COMMAND_STATUS_RUNNING = 2;
	public static final int COMMAND_STATUS_ENDED = 3;
	
	private int status = COMMAND_STATUS_UNKOWN;
	
	private Object[] arguments;
	
	private String method;
	
	private String moduleName;
	
	private String commandName;
	
	private List<OnCommandResultListener> listeners;
	
	private boolean enableCommandBefore;
	
	private boolean enableInterceptor;
	
	private boolean enableCommandAfter;
	
	public Command (String moduleName, String method, Object ...objects) {
		if(!Object[].class.equals(objects.getClass())) 
			objects = new Object[]{objects};
		
		this.arguments = objects;
		this.moduleName = moduleName;
		this.method = method;
		this.commandName = method;  
		this.enableCommandBefore = true;
		this.enableInterceptor = true;
		this.enableCommandAfter = true;
		
		listeners = new ArrayList<Command.OnCommandResultListener>();
	}
	
	public Object execute(){
		return CommandCenter.getInstance().executeCommandWithoutIntercept(this);
	}

	void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public void addOnCommandResultListener(OnCommandResultListener listener){
		listeners.add(listener);
	}
	
	public boolean removeOnCommandResultListener(OnCommandResultListener listener){
		return listeners.remove(listener);
	}
	
	public void clearOnCommandResultListeners(){
		listeners.clear();
	}
	
	public List<OnCommandResultListener> getOnCommandResultListeners(){
		return listeners;
	}
	
	public String toString(){
		return this.moduleName + "/" + this.commandName + "/" + this.method;
	}
	
	public interface OnCommandResultListener{ 
		public void onCommandResult(CommandResult result);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public boolean isEnableCommandBefore() {
		return enableCommandBefore;
	}

	public void setEnableCommandBefore(boolean enableCommandBefore) {
		this.enableCommandBefore = enableCommandBefore;
	}

	public boolean isEnableInterceptor() {
		return enableInterceptor;
	}

	public void setEnableInterceptor(boolean enableInterceptor) {
		this.enableInterceptor = enableInterceptor;
	}

	public boolean isEnableCommandAfter() {
		return enableCommandAfter;
	}

	public void setEnableCommandAfter(boolean enableCommandAfter) {
		this.enableCommandAfter = enableCommandAfter;
	}
	
}
