package com.talentframework.notificationcenter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.talentframework.notificationcenter.UINotificationCenter.UINotificationObserver;

//AddTopicEvent_chatGroupId_messageId 从通道收到消息后发出的Notification。 
//
//AddTopicEvent_* 会话列表收消息使用
//
//AddTopicEvent_chatGroupId_* 某一消息列表收消息使用
// new NotificationEx("AddTopicEvent", "chatGroupId", "messageId");
public class UINotification {
	private String[] keys;
	private HashMap<String, Object> parameters;
	private Set<UINotificationObserver> handledObservers = new HashSet<UINotificationObserver>();
	
	public UINotification(String ...key) {
		if(key != null && !String[].class.equals(key.getClass()))
			key = new String[]{};
		this.keys = key;
	}
	
	public void addHandledObserver(UINotificationObserver observer) {
		handledObservers.add(observer);
	}
	
	public boolean isHandled(UINotificationObserver observer) {
		return handledObservers.contains(observer);
	}
	
	public void reset() {
		handledObservers.clear();
	}
	
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public HashMap<String, Object> getParameters() {
		return parameters;
	}
	
	public void put(String key, Object value) {
		if(parameters == null)
			parameters = new HashMap<String, Object>();
		parameters.put(key, value);
	}
	
	public Object get(String key) {
		if(parameters != null)
			return parameters.get(key);
		return null;
	}
	
}
