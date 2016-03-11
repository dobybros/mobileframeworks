package chat.runtime;

import android.content.Context;

public abstract class CoreRuntime {
	private static CoreRuntime instance;
	
	private Context serviceContext;
	
	private Context applicationContext;
	
	
	protected CoreRuntime() {
		instance = this;
	}
	
	public static CoreRuntime getInstance() {
		return instance;
	}
	public Context getServiceContext() {
		return serviceContext;
	}
	public void setServiceContext(Context serviceContext) {
		this.serviceContext = serviceContext;
	}

	public Context getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(Context applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Context getContext() {
		if(applicationContext != null)
			return applicationContext;
		return serviceContext;
	}
	public interface DecoratorListener {
		public void found(int[] pos, int indexOfParams, String valueOfParams);
	}
	
	public String getI18nText(int key, String[] params) {
		return getI18nText(key, params, null);
	}
	public String getI18nText(int key, String[] params, DecoratorListener listener) {
		String content = getApplicationContext().getString(key);
		int pos;
		final String startTag = "#{";
		final String endTag = "}";
		while((pos = content.indexOf(startTag)) >= 0) {
			int endPos = content.indexOf(endTag, pos + startTag.length());
			if(endPos == -1)
				break;
			String number = content.substring(pos + startTag.length(), endPos);
			int index = -1;
			try {
				index = Integer.parseInt(number);
			} catch (Exception e) {
				continue;
			}
			if(index < 0 && index >= params.length)
				continue;
			String value = params[index];
			int[] positions = new int[2];
			positions[0] = pos;
			positions[1] = value.length();
			if(listener != null) {
				listener.found(positions, index, value);
			}
			content = content.substring(0, pos).concat(value).concat(content.substring(endPos + endTag.length()));
		}
		return content;
	}
}
