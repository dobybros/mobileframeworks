package chat.dao;

import java.util.concurrent.ConcurrentHashMap;

import com.talentframework.talentexception.TalentException;

public class DAOFactory {
	private DbHelper dbHelper;
	
	private static DAOFactory instance = new DAOFactory();
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
	private DAOFactory() {}
	
	public synchronized void init(DbHelper dbHelper) {
		daoMap.clear();
		this.dbHelper = dbHelper;
	}
	
	private ConcurrentHashMap<Class<? extends DAO<? extends DAOObject>>, DAO<? extends DAOObject>> daoMap = new ConcurrentHashMap<Class<? extends DAO<? extends DAOObject>>, DAO<? extends DAOObject>>();
	
	public synchronized DAO<? extends DAOObject> getDAO(Class<? extends DAO<? extends DAOObject>> daoClass) {
		if(daoClass != null) {
			DAO<? extends DAOObject> dao = daoMap.get(daoClass);
			if(dao == null) {
				try {
					dao = daoClass.newInstance();
					dao.setWritableDatabase(dbHelper);
					//FIXME this dao.init() might encounter concurrent bug. there might be many daos, but only one dao might be used after init. 
					dao.init();
					daoMap.putIfAbsent(daoClass, dao);
				} catch (InstantiationException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				} catch (TalentException e) {
					e.printStackTrace();
					return null;
				}
				dao = daoMap.get(daoClass);
			}
			return dao;
		}
		return null;
	}
	
	public synchronized void removeDAO(Class<? extends DAO<? extends DAOObject>> daoClass) {
		DAO<? extends DAOObject> dao = daoMap.get(daoClass);
		if(dao != null) {
			try {
				dao.reset();
			} catch (TalentException e) {
				e.printStackTrace();
			}
			daoMap.remove(daoClass);
		}
	}
	
	public synchronized void removeDAO(DAO<? extends DAOObject> dao) {
		if(dao != null && daoMap.containsValue(dao)) {
			try {
				dao.reset();
			} catch (TalentException e) {
				e.printStackTrace();
			}
			daoMap.remove(dao.getClass());
		}
	}
}
