package chat.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import chat.runtime.CoreRuntime;

/**
 * @author Administrator
 *
 */
public class DbHelper extends SQLiteOpenHelper {
//	private final static String dbName = "miss";
	private final static int dbVersion = 1;
//	private static DbHelper instance = new DbHelper();

//	public static DbHelper getInstance ()	{
//		return instance;
//	}

	public DbHelper(String dbName) {
		super((Context) CoreRuntime.getInstance().getContext(), dbName, null, dbVersion);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
	
//		createTable
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
