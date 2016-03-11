package com.talentframework.commandcenter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ModuleManager {
	
	public static final String tag = "Module";
	
	private static ModuleManager instance;
	
	private String packageName;
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public static ModuleManager getInstance() {
		if (instance == null) {
			instance = new ModuleManager();
		}
		return instance;
	}
	
	public void init() {
		hash = new Hashtable<Class<?>, Class<?>>();
		
		try {
			parseModule("modules.cfg");
		} catch (ParserConfigurationException e) {
			System.out.println("Parse Module Error:" + e.toString());
		} catch (SAXException e) {
			System.out.println("Parse Module Error:" + e.toString());
		} catch (FactoryConfigurationError e) {
			System.out.println("Parse Module Error:" + e.toString());
		} catch (IOException e) {
			System.out.println("Parse Module Error:" + e.toString());
		}
//		hash.put(ITopicSyncModule.class, TopicSyncAdapter.class);
//		hash.put(IDataBaseModule.class, DatabaseAdapter.class);
//		hash.put(IFileModule.class, FileAdapter.class);
//		hash.put(INetModule.class, NetModuleAdapter.class);
	}
	
	Hashtable<Class<?>, Class<?>> hash;
	
	static HashMap<String, IModule> singletonList = new HashMap<String, IModule>();
	
	private ModuleManager() {
	}
	
	@SuppressWarnings("unchecked")
	public <T> T newModule(Class<T> c) {
		try {
			Class<T> cc = (Class<T>) hash.get(c);
			if(cc == null){
				throw new NullPointerException("Unknow Class Module:" + c.getName());
			}
			String className = c.getName();
			if(singletonList.containsKey(className)){
				IModule module = singletonList.get(className);
				if(module == null){
					module = (IModule) cc.newInstance();
					singletonList.put(className, module);
				}
				return (T) module;
			}
			
			T item = cc.newInstance();
			return item;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		throw new NullPointerException("Unknow Class Module:" + c.getName());
	}
	
	private void parseModule(String fname) throws ParserConfigurationException, SAXException, FactoryConfigurationError, IOException {
		
		InputStream stream = this.getClass().getResourceAsStream(fname);
		
		if (stream == null) {
			stream = this.getClass().getResourceAsStream("/config/modules.cfg");
		}
		
		SAXParser parse = SAXParserFactory.newInstance().newSAXParser();
		ModuleParser handler = new ModuleParser(this.hash);
		parse.parse(stream, handler);
		
	}
	
	public Class<?>[] getDeclaredModuleClasses(){
		Set<Class<?>> keys = hash.keySet();
		if(keys == null){
			return null;
		}
		Class<?>[] classes = new Class[keys.size()];
		keys.toArray(classes);
		return classes;
	}
	
	static class ModuleData {
		String ifName;
		String ifEntity;
		
		public void setInterface(String name) {
			ifName = name;
		}
		
		public void setEntity(String entity) {
			ifEntity = entity;
		}
		
		public void toClassTable(Hashtable<Class<?>, Class<?>> outTable) throws ClassNotFoundException {
			Class<?> ifClass = Class.forName(ifName);
			Class<?> enClass = Class.forName(ifEntity);
			outTable.put(ifClass, enClass);
		}
	}
	
	static class ModuleParser extends DefaultHandler {
		
		ModuleData data;
		
		Hashtable<Class<?>, Class<?>> outTable;
		
		boolean newModule;
		
		public ModuleParser(Hashtable<Class<?>, Class<?>> hash) {
			outTable = hash;
			newModule = false;
		}
		
	    @Override
	    public void startElement(String namespaceURI, String localName,
	              String qName, Attributes atts) throws SAXException {
	    	if(localName.trim().equals("")){
	    		localName = qName;
	    	}
	    	
	    	if (localName.equals("packageName")) {
				String packageName = atts.getValue("name");
				ModuleManager.getInstance().setPackageName(packageName);
			}
	    	
	    	if (localName.equals("module")) {
	    		newModule = true;
	    		data = new ModuleData();
	    		return;
	    	}
	    	
	    	if (!newModule) {
	    		return;
	    	}
	    	
	    	if (localName.equals("interface")) {
	    		String name = atts.getValue("name");
	    		String isSingleton = atts.getValue("singleton");
	    		if(isSingleton != null && isSingleton.equals("true") && !singletonList.containsKey(name)){
	    			singletonList.put(name, null);
	    		}
	    		data.setInterface(name);
	    	} else if (localName.equals("entity")) {
	    		String name = atts.getValue("name");
	    		data.setEntity(name);
	    	}
	    	
	    }
	    
	    @Override
	    public void endElement(String namespaceURI, String localName, String qName)
	              throws SAXException {
	    	if(localName.trim().equals("")){
	    		localName = qName;
	    	}
	    	if (newModule && localName.equals("module")) {
	    		newModule = false;
	    		try {
					data.toClassTable(outTable);
				} catch (Exception e) {
					System.out.println("Parse Module Error:" + e.toString());
				}
	    	}
	    }
	    
	    @Override
	    public void characters(char ch[], int start, int length) {
//	    	if(this.ifName){
//	    		data.setInterface(new String(ch,start,length));
//	    	}
//	    	
//	    	if (this.enName) {
//	    		data.setEntity(new String(ch,start,length));
//	    	}
	    }
	}
}
