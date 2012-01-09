package washington.edu.willi7;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Homework007Activity extends Activity {
	
	private Button mButton;
	private WebView mWebview;
	private TextView mTextView;
	private URL mUrl;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
			mUrl = new URL("http://www.google.com");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        /* find the button */
        mButton = (Button)findViewById(R.id.button1);
        /* set the on click listener */
        mButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v){
        		mWebview.loadUrl((String) mTextView.getText());
//        		mWebview.loadUrl(mUrl.getFile());
        	} 
        });
        
        /* find the text control */
        mTextView = (TextView)findViewById(R.id.editText1);
        
        /* find the webview */
        mWebview = (WebView)findViewById(R.id.webView1);
        mWebview.getSettings().setJavaScriptEnabled(true);
        /* Creating a new webclient to keep url clicks in my webview */
        mWebview.setWebViewClient(new WebViewClient());
        
        /* load a url */
        mWebview.loadUrl("http://www.google.com");
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	menu.add(Menu.NONE, 1, Menu.NONE, "Show Source");
    	menu.add(Menu.NONE, 2, Menu.NONE, "Show Cookies");
    	menu.add(Menu.NONE, 3, Menu.NONE, "Reload");
    	return true;
    } 

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Show the source, load ShowSourceActivity passing current URL
		case 1:
			Toast.makeText(this, "Show Source Pressed!", Toast.LENGTH_SHORT).show();
			break;
		// Show the Cookies, load the ShowCookiesActivity passing the current URL 
		case 2:
			Toast.makeText(this, "Show Cookies Pressed!", Toast.LENGTH_SHORT).show();
			break;
		// Reload the WebView
		case 3:
			Toast.makeText(this, "Reload Pressed!", Toast.LENGTH_SHORT).show();
			break;
		}
		super.onOptionsItemSelected(item);
		return false;
	}
	
	
	
}