package washington.edu.willi7;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.widget.TextView;

public class ViewCookiesActivity extends Activity {
	
	private String mUrl;
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* load the layout */
		setContentView(R.layout.view_cookies);
		
		/* get the URL string */
		mUrl = getIntent().getExtras().getString(ViewerConstants.INTENT_URL_STRING);
		
		/* find the TextView */
		mTextView = (TextView)findViewById(R.id.textViewCookies);
		mTextView.setText(CookieManager.getInstance().getCookie(mUrl));
	}
}
