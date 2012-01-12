package washington.edu.willi7;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Homework007Activity extends Activity {

	private Button mButton;
	private WebView mWebview;
	private TextView mTextView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* request progress on the window so we can see if web pages are loading */
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		/* inflate the view */
		setContentView(R.layout.main);

		/* for the WebChromeClient */
		final Activity mActivity = this;

		/* find the button */
		mButton = (Button) findViewById(R.id.button1);
		/* set the on click listener */
		mButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				loadUrl();
			}
		});

		/* find the text control */
		mTextView = (TextView) findViewById(R.id.editText1);
		mTextView.setText(R.string.default_url);

		/* find the webview */
		mWebview = (WebView) findViewById(R.id.webView1);
		/* adjust settings */
		mWebview.getSettings().setJavaScriptEnabled(true);
		mWebview.getSettings().setBuiltInZoomControls(true);
		
		/* Creating a new webclient to keep url clicks in my webview */
		mWebview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				mActivity.setProgress(newProgress * 100);
			}
		});

		/*
		 * set the WebView client to my own custom class, otherwise it opens it
		 * in the real browser
		 */
		mWebview.setWebViewClient(new MyWebViewClient());

		/* load a url */
		loadUrl();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, Menu.NONE, "Show Source");
		menu.add(Menu.NONE, 2, Menu.NONE, "Show Cookies");
		// menu.add(Menu.NONE, 3, Menu.NONE, "Reload");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent = null;
		switch (item.getItemId()) {
		// Show the source, load ShowSourceActivity passing current URL
		case 1:
			intent = new Intent(getApplicationContext(),
					ViewSourceActivity.class);
			intent.putExtra(ViewerConstants.INTENT_URL_STRING,
					mWebview.getUrl());
			startActivity(intent);
			break;
		// Show the Cookies, load the ShowCookiesActivity passing the current
		// URL
		case 2:
			intent = new Intent(getApplicationContext(),
					ViewCookiesActivity.class);
			intent.putExtra(ViewerConstants.INTENT_URL_STRING,
					mWebview.getUrl());
			startActivity(intent);
			break;
		/*
		 * // Reload the WebView case 3: Toast.makeText(this, "Reload Pressed!",
		 * Toast.LENGTH_SHORT).show(); loadUrl(); break;
		 */
		default:
			break;
		}
		super.onOptionsItemSelected(item);
		return false;
	}

	private void loadUrl() {
		mWebview.loadUrl(mTextView.getText().toString());
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mTextView.getWindowToken(), 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the BACK key and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
			mWebview.goBack();
			return true;
		}
		// If it wasn't the BACK key or there's no web page history, bubble up
		// to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}

	/* override the load Url even so that user clicks stay in my app */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}