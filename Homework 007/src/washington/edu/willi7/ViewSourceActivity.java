package washington.edu.willi7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity to view the source of the URL 
 * in the text control from the main activity.
 *  
 * @author martin
 */
public class ViewSourceActivity extends Activity {
	
	private TextView mSourceTextView;
	private String mUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		/* load the layout */
		setContentView(R.layout.view_source);
		
		/* get the url from the extras */
		Intent intent = getIntent();
		mUrl = intent.getExtras().getString(ViewerConstants.INTENT_URL_STRING);
		mSourceTextView = (TextView)findViewById(R.id.textViewSource);
		
		/* create task and pass url */
		GetSourceTask task = new GetSourceTask();
		task.execute(new String [] { mUrl });
	}
	
	/**
	 * Async task that gets the source for a given URL
	 * 
	 * creates a HttpClient to use as a means to access the source of the URL passed in.
	 * Response is read via a InputStreamReader into a StringBuidler line by line.
	 * This seems more complicated than it should be.
	 * 
	 * @param url string to parse
	 * @return String of the URL source in text
	 * @throws IOException
	 * 
	 * @example http://stackoverflow.com/questions/2423498/how-to-get-the-html-source-of-a-page-from-a-html-link-in-android
	 */
	private class GetSourceTask extends AsyncTask<String, Void, String> {

		/**
		 * function to be performed in the background.
		 * Does the HTTP GET operation.
		 * 
		 * @param params string array of arguments pass to the function, only the URL
		 * @return string representation of the HTTP GET
		 */
		@Override
		protected String doInBackground(String... params) {

			String[] url = params;
			String html_source = "";
			
			/* Create the client, request, and response */
			HttpClient client = new DefaultHttpClient();
			/* only one string being passed in, take first */
			HttpGet request = new HttpGet(url[0]);

			/* execute the request, */
			try {
				HttpResponse response = client.execute(request);

				/* Stream the response if successful */
				InputStream in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				
				StringBuilder str = new StringBuilder();
				String line = null;
			
				/* loop on reader until done */
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
				
				/* close input stream */
				in.close();
				
				/* set the return string */
				html_source = str.toString();
				
			} catch (IOException e) {
				html_source = "Error!";
				e.printStackTrace();
			}
			
			/* return the source string */
			return html_source;
		}
		
		/**
		 * After the execute finishes up the text control
		 * 
		 * @param result source of the current url in a string
		 * @return void 
		 */
		@Override
		protected void onPostExecute( String result )
		{
			/* update source text control with HTTP result */
			mSourceTextView.setText(result);
		}
	}
}
