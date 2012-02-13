package washington.edu.willi7;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Homework008Activity extends Activity {
	
    private AndroidHttpClient mClient;
    private Context mContext;
    private Button mButtonGet;
    private TextView mHeaderTextView;
    private TextView mContentTextView;
    private String mURLString;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	private String getUrl(String zipCode) {
 		String query = null; // = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20location%3D98109&format=json&callback="; */
		query.format("http://query.yahooapis.com/v1/public/yql?q=select%%20*%%20from%%%20weather.forecast%%20where%%20location%%3D%s%&format=json&callback=", zipCode);
		return query;
	}

	private HttpResponse getResponse(String url) {
		HttpResponse resp = null;
		HttpRequest req = null;
		HttpClient client = null;

		return resp;
	}

	private void parseRequest() {

	}
}