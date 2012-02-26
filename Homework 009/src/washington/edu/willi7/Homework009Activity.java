package washington.edu.willi7;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class Homework009Activity extends ListActivity implements OnClickListener {
	
	private Button mButton;
	private EditText mEditText;
	private SimpleCursorAdapter mSimpleCursorAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* get widget references */
        mButton = (Button)findViewById(R.id.button1);
        mEditText = (EditText)findViewById(R.id.editText1);
    }

    /**
     * Add a task
     * 
     * Adds the current text found in the EditText 
     * into the database using the content provider.
     * If no content is in the edit text nothing is added.
     * 
     */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
}