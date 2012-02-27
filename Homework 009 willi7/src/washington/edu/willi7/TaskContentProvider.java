package washington.edu.willi7;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class TaskContentProvider extends ContentProvider {
	public static final String AUTHORITY = "washington.edu.willi7.TaskContentProvider";

	TaskDatabaseHelper mSQLHelper;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mSQLHelper.getWritableDatabase();
		int count;
		count = db.delete(Task.Tasks.TABLE_NAME, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub

		SQLiteDatabase db = mSQLHelper.getWritableDatabase();
		long rowID = db.insert(Task.Tasks.TABLE_NAME, null, values);
		if (rowID > 0) {
			Uri noteUri = ContentUris.withAppendedId(Task.CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		} else {
			Log.e("hw09", "insert() Error inserting task");
		}

		/* insert failed */
		return null;
	}

	@Override
	public boolean onCreate() {
		// create a database helper
		mSQLHelper = new TaskDatabaseHelper(getContext(),
				TaskDatabaseHelper.DB_NAME, null, TaskDatabaseHelper.VERSION);
		return (mSQLHelper == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(Task.Tasks.TABLE_NAME);
		SQLiteDatabase db = mSQLHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = mSQLHelper.getWritableDatabase();
		int count;
		count = db.update(Task.Tasks.TABLE_NAME, values, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
