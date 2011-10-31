package edu.washington.willi7;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.view.inputmethod.InputMethodManager;

public class HomeActivity extends ListActivity {

	// override add string click
	private Button m_add_string_button;

	// extend to populate the ListView
	private RowListAdapter m_list_adapter;

	// array to hold row items
	private ArrayList<RowClass> m_array_list = new ArrayList<RowClass>();

	@Override
	public void onPause() {
		super.onPause();

		// on pause save the data
		this.SaveListData();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.v("mytag", "content view was just set");

		// setup list adapter
		this.m_list_adapter = new HomeActivity.RowListAdapter(
				this.m_array_list, this);
		this.setListAdapter(this.m_list_adapter);

		// setup add text on click
		this.m_add_string_button = (Button) this
				.findViewById(R.id.add_string_button);
		this.m_add_string_button
				.setOnClickListener(new HomeActivity.AddTextListener());

		// populate the list
		this.GetListData();
	}

	// handler for when user clicked an item in the list
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	

		String keyword = this.m_array_list.get(position).GetJoke();
		Toast.makeText(this, "You have selected: " + keyword,
				Toast.LENGTH_SHORT).show();

		// get a reference to which row was clicked
		RowClass row = this.m_array_list.get(position);

		// Create Intent
		Intent rate_intent = new Intent(this.getBaseContext(),
				RateActivity.class);

		// pass the data to the RateActivity
		rate_intent.putExtra("text", row.GetJoke());
		rate_intent.putExtra("rating", row.GetRating());
		rate_intent.putExtra("position", position);

		// start the activity, pass 1 for request value
		startActivityForResult(rate_intent, 1);

		// hide keyboard, for some reason it pops up when you click list item
		// hide the keyboard?
	}

	// Listener class to add text to the ListView
	public class AddTextListener implements View.OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			EditText edit_text = (EditText) findViewById(R.id.editText);

			// get text from the text field
			String new_string = edit_text.getText().toString();

			HomeActivity home = (HomeActivity) v.getContext();

			// print out add message
			if (new_string.length() > 0) {
				Toast.makeText(getBaseContext(), "You added: " + new_string,
						Toast.LENGTH_SHORT).show();
				home.AddTextToList(new_string);

				// notify the ListView that the list has been changed
				home.m_list_adapter.notifyDataSetChanged();
			}

			// clear the text box
			edit_text.setText("");

			// hide the keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(edit_text.getWindowToken(), 0);
		}

	}

	// handle result
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// this result came from the quote rating activity
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			// get the position from the original click and get the new rating
			int position = data.getIntExtra("position", 0);
			RowClass row = this.m_array_list.get(position);
			row.setM_rating(data.getFloatExtra("rating", 0));

			// notify data
			this.m_list_adapter.notifyDataSetChanged();
		}
	}

	@SuppressWarnings("unchecked")
	public void GetListData() {
		SharedPreferences preference = this.getPreferences(MODE_PRIVATE);
		int count = preference.getInt("count", 0);

		// if none have been saved
		if (count == 0) {
			// read from the string array (default)Resources res =
			// getResources();
			String[] string_list = this.getResources().getStringArray(
					R.array.string_list);

			// loop through strings and add
			for (String s : string_list) {
				this.m_array_list.add(new RowClass(s));
			}
		} else {
			// in file, so read them back in
			FileInputStream input = null;
			ObjectInputStream obj_input = null;

			// open the file
			try {
				input = this.openFileInput("jokes");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// connect file stream to object stream
			try {
				obj_input = new ObjectInputStream(input);
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// read in the array list
			try {
				this.m_array_list = ((ArrayList<RowClass>) obj_input
						.readObject());
			} catch (OptionalDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Log.v("mytag", "nofitying the data set changed!");
		this.m_list_adapter.notifyDataSetChanged();
	}

	/**
	 * Save out the list data to a file
	 */
	public void SaveListData() {

		FileOutputStream output = null;
		ObjectOutputStream obj_output = null;
		// get the size and save it
		SharedPreferences.Editor editor = this.getPreferences(MODE_PRIVATE)
				.edit();
		editor.putInt("count", this.m_array_list.size());
		editor.commit();

		// open the file for writing
		try {
			output = openFileOutput("jokes", MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// connect file stream with object stream
		try {
			obj_output = new ObjectOutputStream(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			obj_output.writeObject(this.m_array_list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * // write out objects for(int i = 0; i < this.m_array_list.size();
		 * i++) { try { obj_output.writeObject(this.m_array_list.get(i)); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		// close stream
		try {
			obj_output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// close file
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// extend Base Adapter to populate the ListView
	private class RowListAdapter extends BaseAdapter {
		private ArrayList<RowClass> m_list;
		private LayoutInflater m_inflater;

		// widgets to get populated
		class RowView {
			TextView text;
			RatingBar rating;
		}

		// constructor
		RowListAdapter(ArrayList<RowClass> list, Context context) {
			this.m_list = list;
			this.m_inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return this.m_list.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return this.m_list.get(position);
		}

		// not sure if this is needed... or what to return
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		// create the view of the list
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			RowView row;

			// When convertView is not null, we can reuse it directly, there is
			// no need
			// to reinflate it. We only inflate a new View when the convertView
			// supplied
			// by ListView is null.
			if (convertView == null) {

				// set view to the list item layout (contains a TextView and
				// Rating)
				convertView = this.m_inflater.inflate(R.layout.list_item, null);

				// Creates a ViewHolder and store references to the two children
				// views
				// we want to bind data to.
				row = new RowView();
				row.text = (TextView) convertView.findViewById(R.id.textView1);
				row.rating = (RatingBar) convertView
						.findViewById(R.id.ratingBar1);

				convertView.setTag(row);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				row = (RowView) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			row.text.setText((String) this.m_list.get(position).GetJoke());
			row.rating.setRating((float) this.m_list.get(position).GetRating());

			return convertView;
		}
	}

	public void AddTextToList(String str) {
		this.m_array_list.add(new RowClass(str));
	}
}