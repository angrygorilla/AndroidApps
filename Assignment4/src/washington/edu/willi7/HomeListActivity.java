package washington.edu.willi7;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

/** Main activity */
public class HomeListActivity extends ListActivity {

	/** Private members */
	private SQLiteDatabase mDatabase;
	private TranslatorDatabase mTranslatorHelper;
	private CursorAdapter mCategorySource;
	private CursorAdapter mMessageSource;
	private CursorAdapter mLanguageSource;
	private Boolean mLanguageToggle = true;

	private Spinner mCategorySpinner;
	
	private OnItemSelectedListener category_listener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			// get the cursor
			Cursor c = mCategorySource.getCursor();
			// print something
			Log.d("assignment4", "OnItemSelectedListener - cursor value" + c.getString(c.getColumnIndex("category")));
			Cursor nc = mTranslatorHelper.queryMessagesByCategory(c.getString(c.getColumnIndex("category")));
			mMessageSource.changeCursor(nc);
			mMessageSource.notifyDataSetChanged();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	
	// return the textview order
	public int [] getOrder() {
		
		int [] order = null;
		if(mLanguageToggle){
			order = new int [] { android.R.id.text1, android.R.id.text2 };
		}
		else {
			order = new int [] { android.R.id.text2, android.R.id.text1 };
		}
		
		// return column id array
		return order;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	menu.add(1, 1, 1, "Switch Language");
    	return true;
    }
    
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    	//Add employee
    	case 1:
    		mLanguageToggle = !mLanguageToggle;
    		Toast.makeText(this, "Menu Item Selected!", Toast.LENGTH_SHORT).show();
    		mMessageSource = new SimpleCursorAdapter( getBaseContext(), android.R.layout.simple_list_item_2, mMessageSource.getCursor(),
    				new String[] { TranslatorColumns.MESSAGE_1_COLUMN, TranslatorColumns.MESSAGE_2_COLUMN }, getOrder());
    		setListAdapter(mMessageSource);
    		break;
    	}
    	super.onOptionsItemSelected(item);
    	return false;
    }	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// create the database
		mTranslatorHelper = new TranslatorDatabase(this);
		mDatabase = mTranslatorHelper.getWritableDatabase();

		mCategorySpinner = (Spinner) findViewById(R.id.category);
		Cursor category_query = mTranslatorHelper.queryGetAllCategories();
		mCategorySource = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, category_query,
				new String[] { "category" }, new int[] { android.R.id.text1 });
		mCategorySpinner.setAdapter(mCategorySource);
		mCategorySpinner.setOnItemSelectedListener(category_listener);

		Cursor message_query = mTranslatorHelper.queryMessagesByCategory("Hospital");
		mMessageSource = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, message_query,
				new String[] { TranslatorColumns.MESSAGE_1_COLUMN,
						TranslatorColumns.MESSAGE_2_COLUMN }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		setListAdapter(mMessageSource);
	}
	
	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
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
	protected void onDestroy() {
		super.onDestroy();

		Cursor c = null;

		// close database
		if (mDatabase != null) {
			mDatabase.close();
		}

		// close category
		c = mCategorySource.getCursor();
		if (c != null) {
			c.close();
		}

		// close language
		c = mLanguageSource.getCursor();
		if (c != null) {
			c.close();
		}
		
		// close message
		c = mMessageSource.getCursor();
		if (c != null) {
			c.close();
		}
	}
}