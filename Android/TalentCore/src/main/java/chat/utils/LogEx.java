package chat.utils;

import android.util.Log;


public class LogEx{
	private static final boolean LOG_ENABLE = true;
	public static final String PREFIX = "com.talent.chat";
	
	public static final int DEBUG = Log.DEBUG;
	public static final int ASSERT = Log.ASSERT;
	public static final int ERROR = Log.ERROR;
	public static final int INFO = Log.INFO;
	public static final int VERBOSE = Log.VERBOSE;
	public static final int WARN = Log.WARN;
	public static void d(String tag, String message) {
		if(LOG_ENABLE) {
			long curTime = System.currentTimeMillis();
			Log.d(PREFIX + tag, TimeUtil.formatLongTime(curTime) + "  "+ message);
		}
	}
	public static void i(String tag, String message) {
		if(LOG_ENABLE) {
			long curTime = System.currentTimeMillis();
			Log.i(PREFIX + tag, TimeUtil.formatLongTime(curTime) + "  "+ message);
		}
	}
	public static void e(String tag, String message) {
		if(LOG_ENABLE) {
			long curTime = System.currentTimeMillis();
			Log.e(PREFIX + tag, TimeUtil.formatLongTime(curTime) + "  "+ message);
		}
	}
	public static void v(String tag, String message) {
		if(LOG_ENABLE) {
			long curTime = System.currentTimeMillis();
			Log.v(PREFIX + tag, TimeUtil.formatLongTime(curTime) + "  "+  message);
		}
	}
	public static void w(String tag, String message) {
		if(LOG_ENABLE) {
			long curTime = System.currentTimeMillis();
			Log.w(PREFIX + tag, TimeUtil.formatLongTime(curTime) + "  "+  message);
		}
	}
	public static void wtf(String tag, String message) {
		if(LOG_ENABLE) {
			Log.wtf(PREFIX + tag, message);
		}
	}
	
}
