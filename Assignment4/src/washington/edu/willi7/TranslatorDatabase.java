package washington.edu.willi7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class TranslatorDatabase extends SQLiteOpenHelper {

	// private members
	private final static int mDatabaseVersion = 2;
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
		String create_category = "CREATE TABLE " + TranslatorColumns.CATEGORY_TABLE + " ("
				+ TranslatorColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TranslatorColumns.CATEGORY_COLUMN + " TEXT" + ");";

		String create_language = "CREATE TABLE " + TranslatorColumns.LANGUAGE_TABLE + " ("
				+ TranslatorColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TranslatorColumns.LANGUAGE_COLUMN + " TEXT" + ");";

		String create_message = "CREATE TABLE " + TranslatorColumns.MESSAGE_TABLE + " ("
				+ TranslatorColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TranslatorColumns.CATEGORY_COLUMN + " TEXT,"
				+ TranslatorColumns.MESSAGE_1_COLUMN + " TEXT,"
				+ TranslatorColumns.MESSAGE_2_COLUMN + " TEXT" + ");";

		// create the tables
		db.execSQL(create_category);
		db.execSQL(create_language);
		db.execSQL(create_message);

		Log.d("assignment4", "onCreate - adding some columns");

		// create the insert strings
		// String insert = "INSERT INTO " + mDatabaseName + " ("
		// + TranslatorColumns.CATEGORY_COLUMN + ", "
		// + TranslatorColumns.LANGUAGE_COLUMN + ", "
		// + TranslatorColumns.MESSAGE_COLUMN + ") VALUES ("
		// + Integer.toString(1) + ", " + Integer.toString(1)
		// + ", 'hello');";
		// String insert2 = "INSERT INTO " + mDatabaseName + " ("
		// + TranslatorColumns.CATEGORY_COLUMN + ", "
		// + TranslatorColumns.LANGUAGE_COLUMN + ", "
		// + TranslatorColumns.MESSAGE_COLUMN + ") VALUES ("
		// + Integer.toString(1) + ", " + Integer.toString(2)
		// + ", 'hola');";
		//
		// try using cv!
		addLanguage(db, "English");
		addLanguage(db, "Spanish");
		
		addCategory(db, "Greeting");
		addCategory(db, "Hospital");
		addCategory(db, "Resturant");
	
		addMessage(db, "Greeting", "Hello, How are you?", "Hola, Como estas?");
		addMessage(db, "Greeting", "It is nice to meet you!", "Hola, Como estas?");
		addMessage(db, "Greeting", "Where are you from?", "Hola, Como estas?");
		addMessage(db, "Greeting", "Do you know what time it is?", "Hola, Como estas?");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("assignment4", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data!!");
		db.execSQL("DROP TABLE IF EXISTS " + mDatabaseName);
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	public void addMessage(SQLiteDatabase db, String category,
			String message1, String message2) {

		Log.d("assignment4", "addMessage - adding messages");
		ContentValues cv = new ContentValues();
		cv.put(TranslatorColumns.CATEGORY_COLUMN, category);
		cv.put(TranslatorColumns.MESSAGE_1_COLUMN, message1);
		cv.put(TranslatorColumns.MESSAGE_2_COLUMN, message2);
		db.insert(TranslatorColumns.MESSAGE_TABLE, null, cv);
	}

	public void addLanguage(SQLiteDatabase db, 
			String language) {
		Log.d("assignment4", "addLanguage - adding a language");
		ContentValues cv = new ContentValues();
		cv.put(TranslatorColumns.LANGUAGE_COLUMN, language);
		db.insert(TranslatorColumns.LANGUAGE_TABLE, null, cv);
	}

	public void addCategory(SQLiteDatabase db, 
			String category) {
		Log.d("assignment4", "addCategory - adding a category");
		ContentValues cv = new ContentValues();
		cv.put(TranslatorColumns.CATEGORY_COLUMN, category);
		db.insert(TranslatorColumns.CATEGORY_TABLE, null, cv);
	}

	public Cursor queryMessagesByCategory(String category) {
		SQLiteDatabase db = this.getReadableDatabase();
		// String s = "SELECT DISTINCT " + TranslatorColumns.CATEGORY_COLUMN +
		// " FROM " + mDatabaseName
		String s = "SELECT * FROM " + TranslatorColumns.MESSAGE_TABLE + " WHERE "+ TranslatorColumns.CATEGORY_COLUMN + "=?";
		Cursor c = db.rawQuery(s, new String[] { category });
		return c;
	}

	public Cursor queryGetAllCategories() {
		SQLiteDatabase db = this.getReadableDatabase();
		String s = "SELECT " + TranslatorColumns._ID + ", " + TranslatorColumns.CATEGORY_COLUMN + " FROM " + TranslatorColumns.CATEGORY_TABLE;
		Cursor c = db.rawQuery(s, null);
		return c;
	}

	public Cursor queryGetAllLanguages() {
		SQLiteDatabase db = this.getReadableDatabase();
		String s = "SELECT " + TranslatorColumns._ID + ", " + TranslatorColumns.LANGUAGE_COLUMN + " FROM " + TranslatorColumns.LANGUAGE_TABLE;
		Cursor c = db.rawQuery(s, null);
		return c;
	}

	public String[] getColumnNames() {
		String[] c = { TranslatorColumns._ID,
				TranslatorColumns.CATEGORY_COLUMN,
				TranslatorColumns.LANGUAGE_COLUMN,
				TranslatorColumns.MESSAGE_1_COLUMN,
				TranslatorColumns.MESSAGE_2_COLUMN };
		return c;
	}

}
