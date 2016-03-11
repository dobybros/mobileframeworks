package chat.application;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import chat.runtime.CoreRuntime;
import chat.utils.HandlerEx;
import chat.utils.LogEx;

import com.talentframework.commandcenter.CommandCenter;

public abstract class TalentApplication extends Application {
	private static Handler handler;
	
	private static class TalentHandler extends Handler {
		private static final String TAG = TalentHandler.class.getSimpleName();

		@Override
		public void handleMessage(android.os.Message msg) {
			Object obj = msg.obj;
			if(obj instanceof HandlerEx) {
				HandlerEx h = (HandlerEx) obj;
				try {
					h.handle();
				} catch (Throwable t) {
					t.printStackTrace();
					LogEx.e(TAG, "Handle message " + h + " failed, " + t.getMessage());
				}
			}
    		super.handleMessage(msg);
    	};
	}
	@Override
	public void onCreate() {
		super.onCreate();
		CommandCenter.getInstance();
		CoreRuntime.getInstance().setApplicationContext(this);
		
		TalentApplication.handler = new TalentHandler();
	}

	public static void performOnMainThread(HandlerEx handler) {
		Message m = new Message();
		m.obj = handler;
		TalentApplication.handler.sendMessage(m);
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.e("TalentApplication", "onLowMemory");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.e("TalentApplication", "onTerminate");
	}
}
