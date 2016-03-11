package chat.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.talentframework.utils.StringUtil;


public final class TimeUtil {
	public static long getLongDate(String dateStr) throws ParseException {
		if (dateStr == null) {
			return 0;
		}
		DateFormat formatter;
		String[] strs = StringUtil.splitString(dateStr, ":");
		if(strs.length == 4){
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());	
		}else{
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		}
		
	    Date date = formatter.parse(dateStr);
	    long dateInLong = date.getTime();
		return dateInLong;
	}
	public static String getDate(long dateLong) throws ParseException {
		if (dateLong == 0) {
			return "";
		}
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	    Date date = new Date(dateLong);
		return formatter.format(date);
	}
	public static String getVoiceLengthDate(Integer duration) {
		if (duration == null || duration == 0) {
			return "";
		}
	    int m = duration / 60;
	    int s = duration % 60;
//	    String mStr = m + "";
//	    String sStr = s >= 10 ? (s + "") : ("0" + s);
//	    String mStr = m + "\'";
//	    String sStr = s + "\''";
	    String mStr = m + " sec";
	    String sStr = s + " min";
	    if(m == 0) 
	    		return sStr;
	    else
	    		return mStr + sStr;
//		return  mStr + ":" + sStr;
	}
	public static String getStringDate(long dateLong) throws ParseException {
		if (dateLong == 0) {
			return "";
		}
		DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.getDefault());
	    Date date = new Date(dateLong);
		return formatter.format(date);
	}
	public static String getStringDate(long dateLong, String format) throws ParseException {
		if (dateLong == 0) {
			return "";
		}
		DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
	    Date date = new Date(dateLong);
		return formatter.format(date);
	}
	public static String foramtSimpleTimeIn24(Long time) {
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
		Calendar sessionCal = Calendar.getInstance();
		sessionCal.setTimeInMillis(time);
		return format1.format(sessionCal.getTime());
	}
	
	/**格式后只显示 小时：分钟*/
	public static String formatSimpleTime(Long time) {
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
		Calendar sessionCal = Calendar.getInstance();
		sessionCal.setTimeInMillis(time);
		String result = format1.format(sessionCal.getTime());
		
		int hour = Integer.parseInt(result.substring(0, 2));
		String minutes = result.substring(2, 5);
		if(hour < 12) {
			return Integer.toString(hour).concat(minutes) + " am";
		}
		else if(hour == 12) {
			return Integer.toString(hour).concat(minutes) + " pm";
		}
		else if(hour == 24) {
			return "00".concat(minutes) + " am";
		}
		else {
			return Integer.toString(hour - 12).concat(minutes) + " pm";
		}
		
	}
	
	public static String formatYMDTime(Long time) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Calendar sessionCal = Calendar.getInstance();
		sessionCal.setTimeInMillis(time);
		return format1.format(sessionCal.getTime());
	}
	
	public static String formatLongTime(Long time) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return format.format(time);
	}
	/**
	 * 判断两个时间是否是在同一天，true: 是同一天， false: 不在同一天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isInOneDay(Long time1, Long time2) {
		String time1Str = TimeUtil.formatYMDTime(time1);
		String time2Str = TimeUtil.formatYMDTime(time2);
		return time1Str != null ? time1Str.equals(time2Str) : false;
	}
	/**
	 * 格式化小时分钟为Long型值，默认秒为0
	 * @param hours
	 * @param minutes
	 * @return
	 */
	public static Long formatHoursAndMinutes2LongValue(int hours, int minutes) {
		Calendar c =  Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hours);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, 0);
		return c.getTimeInMillis();
	}
	
}
