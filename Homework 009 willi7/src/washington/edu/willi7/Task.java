package washington.edu.willi7;

import android.net.Uri;
import android.provider.BaseColumns;

public class Task implements BaseColumns {
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ TaskContentProvider.AUTHORITY + "/tasks");
//	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aad.app.c25.books";

	/**
	 * Tasks Structure
	 * 
	 * Contains the table name, column names, 
	 * and projection (all columns)
	 * 
	 * @author martin
	 */
	public static final class Tasks{
		public static final String TABLE_NAME = "task";
		public static final String ID = BaseColumns._ID;
		public static final String CONTENT = "content";
		public static final String CREATED = "created_on";
		public static final String[] PROJECTION = new String[] {
		/* 0 */Task.Tasks.ID,
		/* 1 */Task.Tasks.CONTENT,
		/* 2 */Task.Tasks.CREATED};
	}
}
