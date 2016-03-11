package com.talentframework.commandcenter;

/**
 * 命令观察接口 
 *  
 * @author xiangqi.li 
 */
public interface CommandObserver {      
	
    public void onRequest(Command cmd);       
    public void onResponse(Command cmd);         
}  
