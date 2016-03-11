package chat.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import chat.utils.LogEx;

public abstract class DAOObject {

	private static final String TAG = DAOObject.class.getSimpleName();

	protected String id;
	
	protected JSONObject reserved;
	
	public abstract String getIdField();

	public abstract String getNullField();

	public ContentValues toContentValues() {
		ContentValues cvs = new ContentValues();
		cvs.put(DAO.FIELD_ID, getId());
		if(reserved != null) 
			cvs.put(DAO.FIELD_RESERVED, reserved.toString());
		return cvs;
	}
	
	public void fromCursor(Cursor cursor) {
		id = cursor.getString(cursor.getColumnIndex(DAO.FIELD_ID));
		String reservedStr = cursor.getString(cursor.getColumnIndex(DAO.FIELD_RESERVED));
		if(reservedStr != null && reservedStr.length() > 0) {
			try {
				reserved = new JSONObject(reservedStr);
			} catch (JSONException e) {
				e.printStackTrace();
				LogEx.e(TAG, "Parse reserved string " + reservedStr + " failed, " + e.getMessage());
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JSONObject getReserved() {
		return reserved;
	}

	public void setReserved(JSONObject reserved) {
		this.reserved = reserved;
	}

}