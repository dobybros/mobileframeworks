package com.talentframework.tools;

import java.util.Collection;
import java.util.HashMap;

public class TreeNode<K, V> {
	private HashMap<K, TreeNode<K, V>> childrenTreeNodeMap;
	private HashMap<K, V> kvMap;
	
	public TreeNode() {
		childrenTreeNodeMap = new HashMap<K, TreeNode<K,V>>();
		kvMap = new HashMap<K, V>();
	}
	
	public V get(K key) {
		return kvMap.get(key);
	}
	
	public V put(K key, V value) {
		return kvMap.put(key, value);
	}
	
	public V delete(K key) {
		return kvMap.remove(key);
	}
	
	public Collection<V> values() {
		return kvMap.values();
	}
	
	public TreeNode<K, V> getChildren(K key) {
		return getChildren(key, false);
	}
	public TreeNode<K, V> getChildren(K key, boolean create) {
		TreeNode<K, V> treeNode = childrenTreeNodeMap.get(key);
		if(create && treeNode == null) {
			synchronized (this) {
				treeNode = childrenTreeNodeMap.get(key);
				if(treeNode == null) {
					treeNode = new TreeNode<K, V>();
					childrenTreeNodeMap.put(key, treeNode);
				}
			}
		}
		return treeNode;
	}
}
