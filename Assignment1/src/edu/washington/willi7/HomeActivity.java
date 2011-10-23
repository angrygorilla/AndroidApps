package edu.washington.willi7;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class HomeActivity extends Activity {
		
	
	private Button m_text_button;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		/** save state */
		super.onCreate(savedInstanceState);
		
		/** show the layout */
		setContentView(R.layout.main);
		
		// get the button from the layout
		m_text_button = (Button) findViewById(R.id.button1);
		
		// handle the buttons onClickListener
		
		m_text_button.setOnClickListener(new View.OnClickListener() {
			
			// handle the onClick action
			public void onClick(View v) {
				// Switch the the TextActivity
				Intent i = new Intent(HomeActivity.this, TextActivity.class);
				
				/* start the activity with the new intent data */
				startActivity(i);
			}
		});
	}
}

