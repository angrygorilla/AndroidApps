package washington.edu.willi7;

import android.content.Context;
import android.content.res.Resources;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class GorillaDatabase extends SQLiteOpenHelper {

	
	// static column names
	private final static String NAME_COLUMN = "name";
	private final static String AGE_COLUMN = "age";
	private final static String WEIGHT_COLUMN = "weight";
	private final static String IMAGE_COLUMN = "image";
	private final static String DESCRIPTION_COLUMN = "description";
	
	// private members
	private final static int mDatabaseVersion = 1;
	private final static String mDatabaseName = "gorilla";
	private Context mContext;
	
	/** 
	 * The create table string must create the following columns:
	 * 
	 * name varchar (text) 	- name of the gorilla, max length?
	 * age  integer (int) 	- age of the gorilla
	 * weight float (float) - weight of the gorilla, atleast 3-4 digits
	 * image varchar (text) - path or name of image to use, max length?
	 * descriptions varchar (text) - description about the gorilla.
	 * 
	 */
	private final static String mCreateDatabase = 
			"CREATE TABLE " + mDatabaseName + "( " +				
					NAME_COLUMN + " TEXT UNIQUE," + 
					AGE_COLUMN + " INTEGER," +
					WEIGHT_COLUMN + " INTEGER NOT NULL," +
					IMAGE_COLUMN + " TEXT NOT NULL," +
					DESCRIPTION_COLUMN + " TEXT NOT NULL" + ");";
	
	/**
	 * Constructor
	 * 
	 * The database name and version used are the static values internal
	 * to the class. The parent method is called and the context refernce 
	 * is set.
	 *  
	 * @param context
	 */
	public GorillaDatabase(Context context) {
		super(context, mDatabaseName, null, mDatabaseVersion);
		
		// get a reference to the class that created the GorillaDatabase object
		mContext = context;
		
	}
	
	public GorillaDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		
		// can create CursorFactory that returns a different database based off
		// the name passed in
	}

	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(mCreateDatabase);
		Log.d("Assignment3","onCreate: " + mCreateDatabase);
		
		// Get the Resources from the context
		Resources res = mContext.getResources();
		
		// Get the default column values from the strings file
		String[] name = res.getStringArray(R.array.g_name);
		String[] image = res.getStringArray(R.array.g_image);
		String[] description = res.getStringArray(R.array.g_description);
		int[] age = res.getIntArray(R.array.g_age);
		int[] weight = res.getIntArray(R.array.g_weight);		
		
		for(int i = 0; i < name.length; i++) {
			
			String insert = 
					"INSERT INTO " + GorillaDatabase.mDatabaseName + " (" + 
			GorillaDatabase.NAME_COLUMN + ", " + GorillaDatabase.IMAGE_COLUMN + ", " + GorillaDatabase.DESCRIPTION_COLUMN + ", " + GorillaDatabase.AGE_COLUMN + ", " + GorillaDatabase.WEIGHT_COLUMN + ") " +
							
					" VALUES ('" + name[i] + "', '" + image[i] + "', \"" + description[i] + "\", " + age[i] + ", " + weight[i] + ");";
			// insert the values
			db.execSQL(insert);	
			
			Log.d("Assignment3","onCreate: " + insert );		}	
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.d("Assignment3", "onUpgrade: oldversion: " + oldVersion + " newversion: " + newVersion );
		db.execSQL("DROP TABLE IF exists gorilla");
		
		// create the database
		this.onCreate(db);
	}

	
	public String[] getListViewColumns() {
		String[] c = { GorillaDatabase.IMAGE_COLUMN, GorillaDatabase.NAME_COLUMN, GorillaDatabase.AGE_COLUMN };
		return c;
	}
	
	public String getDatabaseName() {
		return GorillaDatabase.mDatabaseName;
	}
	
	public String getListViewGroupBy() {
		return GorillaDatabase.NAME_COLUMN;
	}
}
