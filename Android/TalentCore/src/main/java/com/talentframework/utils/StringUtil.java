package com.talentframework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Vector;

/**
 * StringUtil provides the sets of widely used toolkit to process the text contents.
 * 
 * @author Aplomb
 */
public class StringUtil {
	protected static final int BOARD_WIDTH = 2;

	public static final char CR = '\r';

	public static final char BR = '\n';

	public static final String BR_DELIMITER = String.valueOf(CR)
			+ String.valueOf(BR);
 
	/**
	 * Split the string content by using the given delimiter
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static String[] splitString(String str, String c) {
		boolean isEnd = false;
		Vector<String> v = new Vector<String>();
		while (!isEnd) {
			int pos = str.indexOf(c);
			if (pos == -1) {
				pos = str.length();
				isEnd = true;
				if (pos == 0) {
					break;
				}
			}
			String event = str.substring(0, pos);
			v.addElement(event);
			if (!isEnd) {
				str = str.substring(pos + c.length());
			}
		}
		String[] array = new String[v.size()];
		v.copyInto(array);
		return array;
	}

	/**
	 * Replace a target string by using a replacement within the given string.
	 * 
	 * @param str
	 *            given string.
	 * @param target
	 *            target string, which will be replaced by others.
	 * @param replacement
	 *            replacement
	 * @return
	 */
	public static String replaceString(String str, String target,
			String replacement) {
		int index = str.indexOf(target);
		if (index != -1) {
			String datafront = str.substring(0, index);
			String datatail = str.substring(index + target.length(), str
					.length());
			str = datafront + replacement + datatail;
		}
		return str;
	}

	public static String insert(String source, String str, int pos){
		if(pos < 0 || pos > source.length()){
			throw new IllegalArgumentException("parameter is illegal!");
		}
		if(source == null || str == null){
			throw new IllegalArgumentException("parameter is null!");
		}
		return source.substring(0, pos).concat(str.concat(source.substring(pos)));
	}
	
	public static String replace(String source, String str, int pos, int length){
		if(pos < 0 || pos > source.length()){
			throw new IllegalArgumentException("parameter is illegal!");
		}
		if(source == null || str == null){
			throw new IllegalArgumentException("parameter is null!");
		}
		return source.substring(0, pos - length).concat(str.concat(source.substring(pos)));
	}
	
	public static String[] getCleanedPhoneNumber(String[] msisdns){
		ArrayList<String> list = new ArrayList<String>();
		for(String msisdn : msisdns){
			if(msisdn.length() == 11){
				list.add(msisdn);
			}else{
				msisdn = msisdn.replace("-", "");
				if(msisdn.length() == 11){
					list.add(msisdn);
				}
			}
		}
		String[] strs = new String[list.size()];
		list.toArray(strs);
		return strs;
	}
	
	public static String[][] getCleanedPhoneNumber(String[] msisdns, String[] nicknames){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> nicks = new ArrayList<String>();
		for(int i = 0; i < msisdns.length; i++){
			String msisdn = msisdns[i];
			if(msisdn.length() == 11){
				list.add(msisdn);
				nicks.add(nicknames[i]);
			}else{
				msisdn = msisdn.replace("-", "");
				if(msisdn.length() == 11){
					list.add(msisdn);
					nicks.add(nicknames[i]);
				}
			}
		}
		String[][] allStrs = new String[2][];
		String[] strs = new String[list.size()];
		list.toArray(strs);
		allStrs[0] = strs;
		
		String[] strs1 = new String[nicks.size()];
		nicks.toArray(strs1);
		allStrs[1] = strs1;
		return allStrs;
	}
	
	public static String getEncodedFileName(String name){
		String str = null;
		
		try {
			str = URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(str == null){
			str = name;
		}
		return str;
	}
	
	private static String[] suffixs = new String[]{
			"jpg",
			"jpeg",
			"png",
			"gif",
			"bmp",
			"tif",
	};
	public static boolean isImageUrl(String url){
		for (int i = 0; i < suffixs.length; i++) {
			if (url.toLowerCase().endsWith(suffixs[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static String getClassLastName(Class<?> clazz){
		String str = clazz.getName();
		return str.substring(str.lastIndexOf(".") + 1);
	}
	
	public static String nowTime() {
		long tm = System.currentTimeMillis();
		return "" + tm;
	}
	
	public static boolean isEmpty(String str) {
		if (str != null && str.length() >= 0 && !"".equals(str)) {
			return false;
		}
		return true;
	}
}
