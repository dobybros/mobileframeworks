package chat.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import chat.constants.CoreConstants;
import chat.utils.LogEx;

import com.talentframework.talentexception.TalentException;

public abstract class DAO<T extends DAOObject> {
	private static final String TAG = "DAO";
	
	public static final String FIELD_ID = "ID";
	
	public static final String FIELD_RESERVED = "RESERVED";
	
	private String tableName;
	private SQLiteDatabase db;
	
	private static Object lock = new Object();
	
	public DAO() {
//		db = DbHelper.getInstance().getWritableDatabase();
	}
	
	public void setWritableDatabase(DbHelper dbHelper) {
		db = dbHelper.getWritableDatabase();
	}
	
	public abstract void init() throws TalentException;
	
	public abstract Class<T> getDAOObjectClass();
	
	public long add(T data) throws TalentException{
		synchronized (lock) {
			return db.replace(tableName, data.getNullField(), data.toContentValues());
		}
	}

	public long replace(T data) throws TalentException{
		synchronized (lock) {
			return db.replace(tableName, data.getNullField(), data.toContentValues());
		}
	}
	
	public int update(T data) throws TalentException{
		if(data.getId() == null)
			throw new TalentException(CoreConstants.ERROR_ID_IS_NULL, "Id can not be null while update");
		synchronized (lock) {
			return db.update(tableName, data.toContentValues(), data.getIdField() + "=?", new String[]{data.getId()});
		}
	}

	public int updateBySQL(String sql, String[] paramenters,
			T daoObject) throws TalentException{
		synchronized (lock) {
			return db.update(tableName, daoObject.toContentValues(), sql, paramenters);
		}
	}

	public int delete(T data) throws TalentException{
		if(data.getId() == null)
			throw new TalentException(CoreConstants.ERROR_ID_IS_NULL, "Id can not be null while delete");
		synchronized (lock) {
			return db.delete(tableName, data.getIdField() + "=?", new String[]{data.getId()});
		}
	}

	public int deleteBySQL(String sql, String[] parameters) throws TalentException{
		synchronized (lock) {
			return db.delete(tableName, sql, parameters);
		}
	}

	public void reset() throws TalentException{
		synchronized (lock) {
			db.execSQL("delete from " + tableName);
			init();
		}
	}
	
	public void execSQL(String sql) throws TalentException {
		synchronized (lock) {
			db.execSQL(sql);
		}
	}

	public T query(T data) throws TalentException {
		Class<T> daoObjectClass = getDAOObjectClass();
		if(daoObjectClass == null) 
			throw new TalentException(CoreConstants.ERROR_DB_FAILED, "No DAOObject class specified in " + getClass());
		Cursor cursor = db.rawQuery("select * from " + tableName + " WHERE " + data.getIdField() + "=?", new String[]{data.getId()});
		try {
			if (cursor.moveToNext()) {
				T daoObj;
				try {
					daoObj = (T) daoObjectClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
					throw new TalentException(CoreConstants.ERROR_DB_FAILED, "Initialize DAOObject class failed, " + e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new TalentException(CoreConstants.ERROR_DB_FAILED, "Initialize DAOObject class failed, " + e.getMessage());
				}
				daoObj.fromCursor(cursor);
				return daoObj;
			}
			return null;
		} finally {
			cursor.close();
		}
	}

	public List<T> queryBySQL(String sql, String[] parameters)
			throws TalentException {
		return queryBySQL(sql, parameters, null);
	}

	public int countBySQL(String sql, String[] parameters) throws TalentException{
		int count = 0;
		Class<T> daoObjectClass = getDAOObjectClass();
		if(daoObjectClass == null)
			throw new TalentException(CoreConstants.ERROR_DB_FAILED, "No DAOObject class specified in " + getClass());
		Cursor cursor = db.rawQuery(sql, parameters);
		if (cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		return count;
	}
	
	public List<T> queryBySQL(String sql, String[] parameters, DAOIterator<T> iterator)
			throws TalentException {
		LogEx.d(TAG, "queryBySQL " + sql);
		Class<T> daoObjectClass = getDAOObjectClass();
		if(daoObjectClass == null) 
			throw new TalentException(CoreConstants.ERROR_DB_FAILED, "No DAOObject class specified in " + getClass());
		Cursor cursor = db.rawQuery(sql, parameters); 
		try {
			List<T> list = new ArrayList<T>();
			if(cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					T daoObj;
					try {
						daoObj = (T) daoObjectClass.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
						throw new TalentException(CoreConstants.ERROR_DB_FAILED, "Initialize DAOObject class failed, " + e.getMessage());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new TalentException(CoreConstants.ERROR_DB_FAILED, "Initialize DAOObject class failed, " + e.getMessage());
					}
					daoObj.fromCursor(cursor);
					list.add(daoObj);
					if(iterator != null) {
						try {
							if(!iterator.iterator(daoObj))
								break;
						} catch (Throwable t) {
							LogEx.e(TAG, "Iterator daoObject " + daoObj + " failed, " + t.getMessage());
						}
					}
				}
			}
			return list;
		} finally {
			cursor.close();
		}
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public interface DAOIterator<T extends DAOObject> {
		public boolean iterator(T daoObject);
	}
}
