package chat.storage;

import com.talentframework.talentexception.TalentException;

import chat.dao.DAO;
import chat.dao.DAOFactory;
import chat.dao.DAOObject;

public class SQLStorage {
	public DAO<? extends DAOObject> getDAO(Class<? extends DAO<? extends DAOObject>> daoClass) throws TalentException {
		DAO<? extends DAOObject> dao = DAOFactory.getInstance().getDAO(daoClass);
		if(dao == null) 
			throw new TalentException(434, "DAO " + daoClass + " can not be initialized");
		return dao;
	}
	
	public void init() throws Throwable {}
}	
