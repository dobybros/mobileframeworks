package chat.storage;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.util.Log;

import chat.dao.DAOFactory;
import chat.dao.DbHelper;
import chat.utils.LogEx;

public class StorageFactory {
	public static final String VISITOR_DBNAME = "visitor"; 
	private static final String TAG = StorageFactory.class.getSimpleName();
	private String dbName;
	private DbHelper dbHelper;
	private List<Class<? extends SQLStorage>> storageList ;
	
	private static StorageFactory instance = new StorageFactory();
	
	public static StorageFactory getInstance() {
		return instance;
	}
	
	private StorageFactory() {}
	
	public synchronized void init(String dbName) {
		ConcurrentHashMap<Class<? extends SQLStorage>, SQLStorage> newStorageMap = new ConcurrentHashMap<Class<? extends SQLStorage>, SQLStorage>();
		this.dbName = dbName;
		if(dbHelper != null) {
			dbHelper.close();
		}
		dbHelper = new DbHelper(dbName);
		
		if(storageList != null && !storageList.isEmpty()) {
			DAOFactory.getInstance().init(dbHelper);
			for(Class<? extends SQLStorage> daoClass : storageList) {
				try {
					SQLStorage sqlStorage = daoClass.newInstance();
					try {
						sqlStorage.init();
					} catch (Throwable e) {
						e.printStackTrace();
						LogEx.e(TAG, "Init storage " + sqlStorage + " failed, " + e.getMessage());
					}
					newStorageMap.put(daoClass, sqlStorage);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} 
			}
			ConcurrentHashMap<Class<? extends SQLStorage>, SQLStorage> oldStorageMap = storageMap;
			storageMap = newStorageMap;
			if(oldStorageMap != null) 
				oldStorageMap.clear();
		}
	}
	
	private ConcurrentHashMap<Class<? extends SQLStorage>, SQLStorage> storageMap;
	
	public synchronized SQLStorage getStorage(Class<? extends SQLStorage> storageClass) {
		Log.e("storageMapSize", Integer.toString(storageMap.size()));
		return storageMap.get(storageClass);
	}
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public List<Class<? extends SQLStorage>> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<Class<? extends SQLStorage>> storageList) {
		this.storageList = storageList;
	}
}
