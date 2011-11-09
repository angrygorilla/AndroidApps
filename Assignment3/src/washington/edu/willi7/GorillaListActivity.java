package washington.edu.willi7;

import java.io.IOException;
import java.io.InputStream;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class GorillaListActivity extends ListActivity{

	// private data
	private SQLiteDatabase mDatabase;
	private CursorAdapter mCursorAdapter;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set the layout
		setContentView(R.layout.main);
		
		// create the database with GorillHelper passing the context (this)
		GorillaDatabase gdb = new GorillaDatabase(this);
		mDatabase = gdb.getWritableDatabase();
		// query the database
		
		//Cursor c = mDatabase.query(gdb.getDatabaseName(), gdb.getListViewColumns(), "rowid _id", null, null, null, gdb.getListViewGroupBy());
		Cursor c = mDatabase.rawQuery("select rowid _id, image, name, age FROM gorilla", null);
		mCursorAdapter = new RowAdaptor(this, R.layout.row_item, c, gdb.getListViewColumns(), new int[] {R.id.imageView1, R.id.name, R.id.age});
		
		// set the list adapter
		setListAdapter(mCursorAdapter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	

	private class RowAdaptor extends SimpleCursorAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			View v = super.getView(position, convertView, parent);
			
			// get all recourse references
			TextView name = (TextView)v.findViewById(R.id.name);
			TextView age = (TextView)v.findViewById(R.id.age);
			ImageView icon = (ImageView)v.findViewById(R.id.imageView1);
			
			// get the cursor that was passed during construction
			Cursor c = getCursor();
			String n = c.getString(c.getColumnIndex("name"));
			int a = c.getInt(c.getColumnIndex("age"));
			String f = c.getString(c.getColumnIndex("image"));
			
			Log.d("Assignment3","name: " + n + " age: " + a + " image: " + f );
			
			name.setText(n);
			age.setText(Integer.toString(a));
			String file = f;
			//name.setText(c.getString(c.getColumnIndex("name")));
			//age.setText(Integer.toString(c.getInt(c.getColumnIndex("age"))));
			//String file = c.getString(c.getColumnIndex("image"));
			
			// get the bitmap from assets
			Bitmap bmap = null;
			InputStream istream = null;
			
			try {
				istream = getAssets().open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bmap = BitmapFactory.decodeStream(istream);
			
			icon.setImageBitmap(bmap);
			
			// return the newly populated view (row)
			return v;
		}

		/**
		 * RowAdapter constructor
		 * 
		 * binds the cursor to the row item
		 * 
		 * @param context
		 * @param layout
		 * @param c
		 * @param from
		 * @param to
		 */
		public RowAdaptor(Context context, int layout, Cursor c, String[] from,
				int[] to) {
			super(context, layout, c, from, to);
		}
	}
}
