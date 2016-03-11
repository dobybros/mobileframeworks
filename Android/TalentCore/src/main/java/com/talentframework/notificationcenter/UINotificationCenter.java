package com.talentframework.notificationcenter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import chat.utils.LogEx;

import com.talentframework.tools.TreeNode;

public class UINotificationCenter {
	private static final String TAG = UINotificationCenter.class.getSimpleName();
	
	private TreeNode<String, UINotificationObserver> tree = new TreeNode<String, UINotificationObserver>();
	
	private static UINotificationCenter instance = new UINotificationCenter();
	
//	private LinkedBlockingQueue<UINotification> notificationQueue = new LinkedBlockingQueue<UINotification>();
	
	private UINotification currentNotification = null;
	
	public static UINotificationCenter getInstance() {
		return instance;
	}
	
	public abstract class UINotificationObserver {
		private String id;
		private String[] filters;
		
		public UINotificationObserver() {
			this.id = UUID.randomUUID().toString();
		}

		public UINotificationObserver(String id) {
			this.id = id;
		}

		public abstract void notificationReceived(UINotification notification);

		public String getId() {
			return id;
		}

		public String[] getFilters() {
			return filters;
		}

		public void setFilters(String[] filters) {
			this.filters = filters;
		}
	}

	private HashMap<UINotificationObserver, HashSet<UINotification>> pendingNotificationsWhileRegister = new HashMap<>();

	public void registerNotificationObserver(UINotificationObserver observer, String ...filters) {
		HashSet<UINotification> pendingNotifications = new HashSet<>();
		if(currentNotification != null)
			pendingNotifications.add(currentNotification);
		pendingNotificationsWhileRegister.put(observer, pendingNotifications);
		synchronized (this) {
			try {
				TreeNode<String, UINotificationObserver> tree = this.tree;
				if(filters != null && filters.length > 0) {
					for(String filter : filters) {
						TreeNode<String, UINotificationObserver> theTree = tree.getChildren(filter, true);
						tree = theTree;
					}
				}
				observer.setFilters(filters);
				tree.put(observer.getId(), observer);

				if(!pendingNotifications.isEmpty()) {
					for(UINotification notification : pendingNotifications) {
						String[] theFilters = notification.getKeys();
						boolean hit = true;
						for(int i = 0; i < filters.length; i++) {
							String filter = filters[i];
							if(i >= theFilters.length || !filter.equals(theFilters[i])) {
								hit = false;
								break;
							}
						}
						if(hit)
							handleNotification(observer, notification);
					}
				}
			} finally {
				pendingNotificationsWhileRegister.remove(observer);
			}
		}
	}

	public synchronized void unregisterNotificationObserver(UINotificationObserver observer) {
		String[] filters = observer.getFilters();
		TreeNode<String, UINotificationObserver> tree = this.tree;
		if(filters != null && filters.length > 0) {
			for(String filter : filters) {
				TreeNode<String, UINotificationObserver> theTree = tree.getChildren(filter, true);
				tree = theTree;
			}
		}
		UINotificationObserver deletedObserver = tree.delete(observer.getId());
		LogEx.i(TAG, "Notification observer " + deletedObserver + " is unregistered by id " + observer.getId());
	}

	/*
	private class Worker implements Runnable {
		private boolean isStarted = true;
		@Override
		public void run() {
			while(isStarted) {
				try {
					UINotification notification = null;
					try {
						notification = notificationQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(notification != null) {
						
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
		
	}*/
	
	public synchronized void sendNotification(UINotification notification) {
		Collection<HashSet<UINotification>> pendingNotificationList = pendingNotificationsWhileRegister.values();
		for(HashSet<UINotification> pendingNotifications : pendingNotificationList) {
			pendingNotifications.add(notification);
		}

		currentNotification = notification;
		try {
			String[] filters = notification.getKeys();
			handleCallback(tree, notification);
			if(filters != null && filters.length > 0) {
				TreeNode<String, UINotificationObserver> theTree = tree;
				for(String filter : filters) {
					TreeNode<String, UINotificationObserver> children = theTree.getChildren(filter);
					if(children != null) {
						handleCallback(children, notification);
						theTree = children;
					} else 
						break;
				}
			}
		} finally {
			currentNotification = null;
		}
	}
	
	private void handleCallback(TreeNode<String, UINotificationObserver> tree, UINotification notification){
		Collection<UINotificationObserver> observers = tree.values();
		HashSet<UINotificationObserver> set = new HashSet<UINotificationObserver>(observers);
		for(UINotificationObserver observer : set) {
			handleNotification(observer, notification);
		}
	}
	
	private void handleNotification(UINotificationObserver observer, UINotification notification) {
		if(!notification.isHandled(observer)) {
			try {
				observer.notificationReceived(notification);
				notification.addHandledObserver(observer);
			} catch (Exception e) {
				e.printStackTrace();
				LogEx.e(TAG, "Notification " + notification + " received failed, " + e.getMessage());
			}
		}
	}
}
