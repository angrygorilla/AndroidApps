package washington.edu.willi7;

import java.io.IOException;
import java.io.InputStream;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GorillaListActivity extends ListActivity {

	// private data
	private SQLiteDatabase mDatabase;
	private CursorAdapter mCursorAdapter;
	private Dialog mSplashDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// show the splash screen
		this.showSplashScreen();

		// set the layout
		setContentView(R.layout.main);

		// create the database with GorillHelper passing the context (this)
		GorillaDatabase gdb = new GorillaDatabase(this);
		mDatabase = gdb.getWritableDatabase();
		// query the database

		// Cursor c = mDatabase.query(gdb.getDatabaseName(),
		// gdb.getListViewColumns(), "rowid _id", null, null, null,
		// gdb.getListViewGroupBy());
		Cursor c = mDatabase.rawQuery(
				"select rowid _id, image, name, age FROM gorilla", null);
		mCursorAdapter = new RowAdaptor(this, R.layout.row_item, c,
				gdb.getListViewColumns(), new int[] { R.id.imageView1,
						R.id.name, R.id.age });

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
			TextView name = (TextView) v.findViewById(R.id.name);
			TextView age = (TextView) v.findViewById(R.id.age);
			ImageView icon = (ImageView) v.findViewById(R.id.imageView1);

			// get the cursor that was passed during construction
			Cursor c = getCursor();
			String n = c.getString(c.getColumnIndex("name"));
			int a = c.getInt(c.getColumnIndex("age"));
			String f = c.getString(c.getColumnIndex("image"));

			Log.d("Assignment3", "name: " + n + " age: " + a + " image: " + f);

			name.setText(n);
			age.setText(Integer.toString(a));
			String file = f;
			// name.setText(c.getString(c.getColumnIndex("name")));
			// age.setText(Integer.toString(c.getInt(c.getColumnIndex("age"))));
			// String file = c.getString(c.getColumnIndex("image"));

			// get the bitmap from assets
			Bitmap bmap = null;
			InputStream istream = null;

			try {
				istream = getAssets().open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// BitmapFactory bmf = new BitmapFactory();

			bmap = BitmapFactory.decodeStream(istream);
			Bitmap bMapScaled = Bitmap.createScaledBitmap(bmap, 100, 100, true);

			icon.setImageBitmap(bMapScaled);

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

	// splash screen methods

	@Override
	public Object onRetainNonConfigurationInstance() {
		MyStateSaver data = new MyStateSaver();
		// Save your important data here

		if (mSplashDialog != null) {
			data.showSplashScreen = true;
			removeSplashScreen();
		}
		return data;
	}

	/**
	 * Removes the Dialog that displays the splash screen
	 */
	protected void removeSplashScreen() {
		if (mSplashDialog != null) {
			mSplashDialog.dismiss();
			mSplashDialog = null;
		}
	}

	/**
	 * Shows the splash screen over the full Activity
	 */
	protected void showSplashScreen() {
		mSplashDialog = new Dialog(this, R.style.GorillaSplashScreen);
		mSplashDialog.setContentView(R.layout.gorilla_splashsreen);
		mSplashDialog.setCancelable(false);
		mSplashDialog.show();

		// Set Runnable to remove splash screen just in case
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				removeSplashScreen();
			}
		}, 3000);
	}

	/**
	 * Simple class for storing important data across config changes
	 */
	private class MyStateSaver {
		public boolean showSplashScreen = false;
		// Your other important fields here
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

//		Toast.makeText(this, "You have selected: " + position,
	//			Toast.LENGTH_SHORT).show();

		// get the data to pass
		Cursor c = this.mCursorAdapter.getCursor();
		String[]  selection_args = { c.getString(c.getColumnIndex("name")) };
		String sql = "select weight, description from gorilla where name=?";
		Cursor more = this.mDatabase.rawQuery(sql, selection_args);
		if( more != null ){
			more.moveToFirst();
		}
		
		// create the new intent
		Intent intent = new Intent(this.getBaseContext(),
				GorillaDetailActivity.class);

		// put the extra data
		intent.putExtra("name", c.getString(c.getColumnIndex("name")));
		intent.putExtra("age", c.getInt(c.getColumnIndex("age")));
		intent.putExtra("image", c.getString(c.getColumnIndex("image")));
		
		// put extra data from other query
		intent.putExtra("description",more.getString(more.getColumnIndex("description")));
		intent.putExtra("weight",more.getInt(more.getColumnIndex("weight")));
		
		// close
		more.close();

		// start the activity
		this.startActivity(intent);
	}

}
