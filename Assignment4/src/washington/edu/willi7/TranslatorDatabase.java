package washington.edu.willi7;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;



public class TranslatorDatabase extends SQLiteOpenHelper {
	
	// private members
	private final static int mDatabaseVersion = 1;
	private final static String mDatabaseName = "translator";
	private Context mContext;

	public TranslatorDatabase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		
		// add user
	}

	public TranslatorDatabase(Context context) {
		super(context, mDatabaseName, null, mDatabaseVersion);
		
		// set reference to context from constructor
		mContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.d("assignment4", "onCreate - creating database");
		String create = "CREATE TABLE " + mDatabaseName + " ("
				+ TranslatorColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TranslatorColumns.CATEGORY_COLUMN + " INTEGER,"
				+ TranslatorColumns.LANGUAGE_COLUMN + " INTEGER,"
				+ TranslatorColumns.MESSAGE_COLUMN + " TEXT"
				+ ");";
		db.execSQL(create);
		
		Log.d("assignment4", "onCreate - adding some columns");
		
		String insert = "INSERT INTO " + mDatabaseName + " ("
				+ TranslatorColumns.CATEGORY_COLUMN + ", "
				+ TranslatorColumns.LANGUAGE_COLUMN + ", "
				+ TranslatorColumns.MESSAGE_COLUMN + ") VALUES ("
				+ Integer.toString(1) + ", " + Integer.toString(1) + ", 'hello');";
		String insert2 = "INSERT INTO " + mDatabaseName + " ("
				+ TranslatorColumns.CATEGORY_COLUMN + ", "
				+ TranslatorColumns.LANGUAGE_COLUMN + ", "
				+ TranslatorColumns.MESSAGE_COLUMN + ") VALUES ("
				+ Integer.toString(1) + ", " + Integer.toString(2) + ", 'hola');";
		db.execSQL(insert);
		db.execSQL(insert2);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	public Cursor queryMessages(String language) {
		Cursor c = null;
		
		return c;
	}
	
	public Cursor queryGetAllCategories() {
		Cursor c = null;
		
		return c;
	}
	
	public String[] getColumnNames() {
		String [] c = { TranslatorColumns._ID, TranslatorColumns.CATEGORY_COLUMN, TranslatorColumns.LANGUAGE_COLUMN, TranslatorColumns.MESSAGE_COLUMN };
		return c;
	}
	
	
}
