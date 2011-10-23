package edu.washington.willi7;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class TextActivity extends Activity{
	
	private Button m_quit_button;
	private TextView m_text_view;
	
	private String getRandomString() {
		String result = new String();
		Resources res = getResources();
		String[] string_list = res.getStringArray(R.array.string_list);
		
		Random index = new Random();
		result = string_list[index.nextInt(string_list.length)];
		
		return result;
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		/** save the state? */
		super.onCreate(savedInstanceState);
		
		/** show the layout of this activity */
		setContentView(R.layout.textlayout);
		
		/** set the TextView text */
		m_text_view = (TextView)this.findViewById(R.id.textView1);
		
		/** call the method to return a random string and set the text */
		m_text_view.setText(this.getRandomString());	
		
		/** find the quit button */
		m_quit_button = (Button)findViewById(R.id.button2);
		
		m_quit_button.setOnClickListener(new View.OnClickListener() {
			
			/** handle the onClick method */
			public void onClick(View v) {
				
				/** handle the click and call finish to "quit" the app */
				Intent i = new Intent(Intent.ACTION_MAIN);
				
				i.addCategory(Intent.CATEGORY_HOME);
				/** start the intent */
				startActivity(i);
				
				/** call finish */
				/** calling finish brings up a list of actions to complete when you
				 * click on the button..
				 */
				finish();
			}
		});
	}
}
