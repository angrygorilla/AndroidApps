package washington.edu.willi7;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.widget.TextView;

/**
 * Activity to show the cookies for a given url. Uses
 * the CookieManager to get the cookies based off the URL
 * passed in. This is a very simple activity.
 * 
 * @author martin
 *
 */
public class ViewCookiesActivity extends Activity {
	
	private String mUrl;
	private TextView mTextView;
	private String mCookie;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* load the layout */
		setContentView(R.layout.view_cookies);
		
		/* get the URL string */
		mUrl = getIntent().getExtras().getString(ViewerConstants.INTENT_URL_STRING);
		
		/* find the TextView */
		mTextView = (TextView)findViewById(R.id.textViewCookies);
		/* simply get the cookie manager, and tada! */
		mCookie = CookieManager.getInstance().getCookie(mUrl);
		if(mCookie == null )
			mCookie = "Error!";
		
		/* set text */
		mTextView.setText(mCookie);	
	}
}
