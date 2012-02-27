package washington.edu.willi7;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Edit a Task Contents
 * 
 * This Activity will display the current task to be edited.
 * After the update is clicked the user is returned to the previous
 * list. 
 * 
 * @author martin
 *
 */
public class EditTaskActivity extends Activity implements OnClickListener {
	
	private Button mButton;
	private EditText mEditText;
	private static int mTaskID;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);	
        
        /* setup button */
        mButton = (Button)findViewById(R.id.button1);
        mButton.setOnClickListener(this);
        
        /* setup edit text */
        mEditText = (EditText)findViewById(R.id.editText1);
        
        /* get content from the bundle */
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	mTaskID = extras.getInt(Task.Tasks.ID);
        	Cursor c = managedQuery(Task.CONTENT_URI, Task.Tasks.PROJECTION, Task.Tasks.ID + " = " + Task.Tasks.ID, null, null);
        	c.moveToFirst();
        	mEditText.setText(c.getString(c.getColumnIndex(Task.Tasks.CONTENT)));
        }
    }

    /**
     * Handle update
     * 
     * Handle the update click. This will update the 
     * contents of the task in the database and
     * finish() the activity. If the EditText is empty 
     * nothing is performed.
     * 
     * @param v view associated with the button click
     */
	@Override
	public void onClick(View v) {
		
		/* get the edit text string */
		String content = mEditText.getText().toString();
		if(content.equals("") != true) {
			
			/* update the contents */
			ContentValues cv = new ContentValues();
			cv.put(Task.Tasks.CONTENT, content);
			getContentResolver().update(Task.CONTENT_URI, cv, Task.Tasks.ID + " = " + mTaskID, null);
			
			/* hide the on screen keyboard */
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

			/* we are done, finish this activity */
			finish();
		}
	}
}
