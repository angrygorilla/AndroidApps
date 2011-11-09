package washington.edu.willi7;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class GorillaDetailActivity extends Activity {

	private TextView mName;
	private TextView mAge;
	private TextView mWeight;
	private TextView mDescription;
	private ImageView mImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// set the content view
		setContentView(R.layout.gorilla_detail);

		mName = (TextView)findViewById(R.id.detail_name);
		mAge = (TextView)findViewById(R.id.detail_age);
		mWeight = (TextView)findViewById(R.id.detail_weight);
		mDescription = (TextView)findViewById(R.id.detail_description);
		mImage = (ImageView)findViewById(R.id.detail_image);
		
		// get the extras that was passed
		Bundle extras = getIntent().getExtras();
		
		String name = extras.getString("name");
		int age = extras.getInt("age");
		int weight = extras.getInt("weight");
		String description = extras.getString("description");
		String image = extras.getString("image");
		
		mName.setText(name);
		mAge.setText(Integer.toString(age));
		mWeight.setText(Integer.toString(weight));
		mDescription.setText(description);
		
		// get the bitmap from assets
		Bitmap bmap = null;
		InputStream istream = null;
		
		try {
			istream = getAssets().open(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//BitmapFactory bmf = new BitmapFactory();
		
		bmap = BitmapFactory.decodeStream(istream);
		Bitmap bMapScaled = Bitmap.createScaledBitmap(bmap, 300, 300, true);
		
		mImage.setImageBitmap(bMapScaled);
		Log.d("Assignment3", "name: " + name + " age: " + age + " image: " + image + 
				" weight: " + weight + " description: " + description );
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

}
