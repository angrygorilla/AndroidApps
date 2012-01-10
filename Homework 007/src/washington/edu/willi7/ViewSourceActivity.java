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
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class ViewSourceActivity extends Activity {
	
	private TextView mSourceTextView;
	private String mUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		/* load the layout */
		setContentView(R.layout.view_source);
		
		Intent intent = getIntent();
		mUrl = intent.getExtras().getString(ViewerConstants.INTENT_URL_STRING);
		
		mSourceTextView = (TextView)findViewById(R.id.textView1);
		try {
			mSourceTextView.setText(getSourceFromUrl(mUrl));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mSourceTextView.setText("Error Occured getting source from URL:" + mUrl);
		}
	}
	
	/**
	 * get Source from the URL provided 
	 * 
	 * creates a HttpClient to use as a means to access the source of the URL passed in.
	 * Response is read via a InputStreamReader into a StringBuidler line by line
	 * 
	 * @param url string to parse
	 * @return String of the URL source in text
	 * @throws IOException
	 * 
	 * @example http://stackoverflow.com/questions/2423498/how-to-get-the-html-source-of-a-page-from-a-html-link-in-android
	 */
	private String getSourceFromUrl(String url) throws IOException {
		
		/* Create the client, request, and response */
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		
		/* execute the request,  */
		HttpResponse response = client.execute(request);

		String html = "";
		
		/* Stream the response if successful */
		InputStream in = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder str = new StringBuilder();
		String line = null;
		while((line = reader.readLine()) != null)
		{
		    str.append(line);
		}
		in.close();
		
		html = str.toString();	
		return html;
	}
}
