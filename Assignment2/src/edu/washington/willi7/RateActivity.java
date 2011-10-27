package edu.washington.willi7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class RateActivity extends Activity {

	TextView m_text_view;
	RatingBar m_rating_bar;
	Intent m_return_intent = new Intent();
	
	// implement class, needs reference to activity for data and intent
	private class JokeRatingChangedListener implements OnRatingBarChangeListener {
		private RateActivity m_activity;
		
		JokeRatingChangedListener(RateActivity act) {
			this.m_activity = act;
		}
		
		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			// TODO Auto-generated method stub
			this.m_activity.m_return_intent.putExtra("rating", rating);
			this.m_activity.setResult(Activity.RESULT_OK, this.m_activity.m_return_intent);
			
			// finish this activity and return
			this.m_activity.finish();
		}
	}
	
	// setup data and show the layout
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// set the content view
		this.setContentView(R.layout.rate_item);
		
		// get the bundle
		Bundle data = this.getIntent().getExtras();
		if(data != null) {
			
			// get the text view and populate it with the passed text
			this.m_text_view = (TextView)this.findViewById(R.id.RateTextView2);
			this.m_text_view.setText(data.getString("text"));
			
			// get the rating and populate it with the passed rating
			this.m_rating_bar = (RatingBar)this.findViewById(R.id.RateRatingBar1);
			this.m_rating_bar.setRating(data.getFloat("rating"));
		
			// save position,rating
			this.m_return_intent.putExtra("position", data.getInt("position"));
			this.m_return_intent.putExtra("rating", data.getFloat("rating"));
			
			// add the Listener
			this.m_rating_bar.setOnRatingBarChangeListener(new JokeRatingChangedListener(this));
		}
	}
}
