package washington.edu.willi7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabaseHelper extends SQLiteOpenHelper {
	
	private final static String DB_NAME = "task_list";
	private final static int VERSION = 1;
	
	private final static String TABLE_NAME = Task.Tasks.TABLE_NAME;
	private final static String TABLE_ROW_ID = Task.Tasks.ID;
	private final static String TABLE_ROW_CONTENT = Task.Tasks.CONTENT;
	private final static String TABLE_ROW_CREATED = Task.Tasks.CREATED;

	/** 
	 * default constructor
	 * 
	 * Required constructor to extend SQLiteOpenHelper
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public TaskDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/**
	 * create database
	 * 
	 * Create the database setting the columns with the correct
	 * types.
	 * 
	 * @param db database to add tables too.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableQueryString = 
				"CREATE TABLE " + 
				TABLE_NAME + " (" + 
				TABLE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				TABLE_ROW_CONTENT + " TEXT, " + 
				TABLE_ROW_CREATED + " TEXT default datetime('now')" + ");";

		db.execSQL(createTableQueryString);
	}
	
	/**
	 * upgrade database
	 * 
	 * if there is a newer version of the database
	 * it will be dropped then recreated with newer
	 * schema.
	 * 
	 * @param oldVersion old version value of database (current)
	 * @param newVersion new version value of database (soontobe)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		/* drop the table if upgrading */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        
        /* create the new database */
        onCreate(db);
	}
}
