package washington.edu.willi7;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Main Activity
 * 
 * This is the main activity. Allows tasks to
 * be added. Added tasks are disdplayed in a list below the 
 * edit text widget. If a list item is pressed the user
 * is presented with two actions. 1) Edit 2) Delete.
 * If Edit is pressed then the EditTask Activity is loaded. 
 * If Delete is selected then the item is removed.
 * 
 * @author martin
 */
public class Homework009Activity extends ListActivity implements OnClickListener{
	
	private Button mButton;
	private EditText mEditText;
	private SimpleCursorAdapter mSimpleCursorAdapter;
	private ListView mListView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* button */
        mButton = (Button)findViewById(R.id.button1);
        mButton.setOnClickListener(this);
       
        /* edit text */
        mEditText = (EditText)findViewById(R.id.editText1);
        
        /* content resolver */
        ContentResolver cr = getContentResolver();
        Cursor c =  cr.query(Task.CONTENT_URI, Task.Tasks.PROJECTION, null, null, null);
 
        /* setup ListView */
        String [] from = { Task.Tasks.CONTENT };
        int [] to = { android.R.id.text1 };
        mSimpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c, from, to);
        mListView = (ListView)findViewById(android.R.id.list);
        mListView.setAdapter(mSimpleCursorAdapter);
    }
    
    /**
     * Add a task
     * 
     * Adds the current text found in the EditText 
     * into the database using the content provider.
     * If no content is in the edit text nothing is added.
     */
	@Override
	public void onClick(View v) {
		String content = mEditText.getText().toString();
				
		if (content.equals("") != true) {
			ContentValues cv = new ContentValues();
			cv.put(Task.Tasks.CONTENT, content);
			this.getContentResolver().insert(Task.CONTENT_URI, cv);

			/* hide the on screen keyboard */
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
			
			/* Clear edit text */
			mEditText.setText("");
		}
	}
	
	
	/**
	 * List item clicked
	 * 
	 * If a list item is clicked then a dialog is presented
	 * with two options. 1) Edit 2) Delete.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final CharSequence[] items = { "Edit", "Delete" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose Action...");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Cursor c = mSimpleCursorAdapter.getCursor();
				int id = c.getInt(c.getColumnIndex(Task.Tasks.ID));

				switch (which) {
				case 0:
					/* edit the item that was clicked */
					Intent intent = new Intent(getBaseContext(), EditTaskActivity.class);
					intent.putExtra(Task.Tasks.ID, id);
					startActivity(intent);
					break;
				case 1:
					/* delete item */
					getContentResolver().delete(Task.CONTENT_URI,
							Task.Tasks.ID + " = " + id, null);
					break;
				}
			}
		});

		/* show actions */
		builder.create().show();
		super.onListItemClick(l, v, position, id);
	}
}