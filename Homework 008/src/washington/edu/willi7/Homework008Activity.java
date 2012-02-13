package washington.edu.willi7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * main activity that handles user input. Activity will allow user to enter
 * zipcode into edittext control. Information will be updated after the "go"
 * button is pressed.
 * 
 * Current conditions, Current Temp, and a Forecast for future days is also
 * display. The temperature is displayed in fahreinhiet and celsius.
 * 
 * @author martin
 * 
 */

public class Homework008Activity extends Activity implements OnClickListener {

	private Button mButtonGet;
	private EditText mEditTextZipcode;
	private TextView mCurrentTemp;
	private TextView mCurrentCondition;
	private TextView mCurrentTitle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		/* get the go button and set onclick listener */
		mButtonGet = (Button) findViewById(R.id.button1);
		mButtonGet.setOnClickListener(this);

		/* get the zipcode edit field */
		mEditTextZipcode = (EditText) findViewById(R.id.editText1);

		/* weather controls */
		mCurrentCondition = (TextView) findViewById(R.id.current_condition);
		mCurrentTemp = (TextView) findViewById(R.id.current_temperature);
		mCurrentTitle = (TextView) findViewById(R.id.current_title);
	}

	private String getUrl(String zipCode) {
		String query = null;
		query = String
				.format("http://query.yahooapis.com/v1/public/yql?q=select%%20*%%20from%%20weather.forecast%%20where%%20location%%3D%s&format=json&callback=",
						zipCode);
		return query;
	}

	private String getTempFromFer(int fahrenheit) {
		int celsius = (int) ((fahrenheit - 32) * (5.0 / 9.0));
		return new String(Integer.toString(fahrenheit) + " F "
				+ Integer.toString(celsius) + " C");
	}

	private class GetSourceTask extends AsyncTask<String, Void, String> {

		/**
		 * get the json return from the yahoo query
		 * 
		 * @param URI
		 *            string
		 * @return string json string representation
		 */
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			String[] url = params;

			/* Create the client, request, and response */
			HttpClient client = new DefaultHttpClient();
			/* only one string being passed in, take first */
			HttpGet request = new HttpGet(url[0]);

			/* execute the request, */
			try {
				HttpResponse response = client.execute(request);

				/* Stream the response if successful */
				InputStream in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));

				StringBuilder str = new StringBuilder();
				String line = null;

				/* loop on reader until done */
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}

				/* close input stream */
				in.close();

				/* set the return string */
				result = str.toString();

			} catch (IOException e) {
				result = "Error!";
				e.printStackTrace();
			}

			/* return the json string */
			return result;
		}

		/**
		 * handles query results and parses into JSON object. Populate Main
		 * activity screen with results.
		 * 
		 * Top level weather info query->results->item Use: title - has nice
		 * condition string
		 * 
		 * Temp item->condition Use: temp - current temp
		 * 
		 * Forcast list item->forcast [] Use: day - day high - high for that day
		 * low - low for that day text - text of that day i.e. "Showers"
		 * 
		 * query -> results -> channel -> item ...
		 * 
		 * 
		 * -- Nice little json viewer -- http://jsonviewer.stack.hu/
		 * 
		 * 
		 * @param result
		 *            string representation of the json query
		 * @return void nothing returned
		 */
		@Override
		protected void onPostExecute(String result) {

			try {
				/* parse string */
				JSONObject full = new JSONObject(result);

				/* get main object set title */
				JSONObject item = full.getJSONObject("query")
						.getJSONObject("results").getJSONObject("channel")
						.getJSONObject("item");
				mCurrentTitle.setText(item.getString("title"));

				/* get condition and temperature */
				JSONObject condition = item.getJSONObject("condition");
				int currentTemp = condition.getInt("temp");
				mCurrentTemp.setText(getTempFromFer(currentTemp));
				mCurrentCondition.setText(condition.getString("text"));

				/* set the forecast table */
				JSONArray forecast_list = item.getJSONArray("forecast");

				/* get inflater and table reference */
				LayoutInflater inf = getLayoutInflater();
				TableLayout forecast_table = (TableLayout) findViewById(R.id.forecast_table);

				/* loop through forecast array */
				for (int i = 0; i < forecast_list.length(); i++) {

					JSONObject cur_day = forecast_list.getJSONObject(i);
					TableRow row = (TableRow) forecast_table.getChildAt(i);
					/* need to create a new one */
					if (row == null) {

						/* inflate with inflater! finally got this */
						row = (TableRow) inf
								.inflate(R.layout.forcast_row, null);

						/* make the rows pop by using a different color */
						if (i % 2 == 0) {
							row.setBackgroundColor(Color.LTGRAY);
						}

						/* add the row */
						forecast_table.addView(row);
					}

					/* Day, High, Low, Desc */
					TextView day = (TextView) row
							.findViewById(R.id.tableDayTextView);
					TextView high = (TextView) row
							.findViewById(R.id.tableHighTextView);
					TextView low = (TextView) row
							.findViewById(R.id.tableLowTextView);
					TextView desc = (TextView) row
							.findViewById(R.id.tableDescTextView);

					day.setText(cur_day.getString("day"));
					high.setText(getTempFromFer(cur_day.getInt("high")));
					low.setText(getTempFromFer(cur_day.getInt("low")));
					desc.setText(cur_day.getString("text"));
				}
			} catch (Exception e) {
				Toast.makeText(getBaseContext(),
						"Zipcode not found or service unavailable",
						Toast.LENGTH_SHORT).show();
			}

			/* handle errors? */
		}
	}

	/**
	 * Onclick handler for go button.
	 * 
	 * This will check the values current in the edit text control.
	 * If its of valid length, task is created with query string.
	 * 
	 * @param v view which the click originated
	 */
	public void onClick(View v) {

		String zipCode = mEditTextZipcode.getText().toString();
		if (zipCode != null && zipCode.length() != 5) {
			Toast.makeText(getBaseContext(),
					"Invalid zipcode! Must be 5 Digits i.e. \"98109\"",
					Toast.LENGTH_SHORT).show();
		} else {
			/* get the query uri with the new zipcode */
			String url = getUrl(zipCode);

			/* spawn the task and execute */
			GetSourceTask task = new GetSourceTask();
			task.execute(new String[] { url });
		}
	}
}