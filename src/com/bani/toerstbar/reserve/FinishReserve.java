package com.bani.toerstbar.reserve;

import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinishReserve extends Activity implements OnClickListener {
	
	private HashMap<String, String> resInfo;
	private TextView name;
	private TextView area;
	private TextView numGuests;
	private TextView date;
	private TextView time;
	private TextView comments;
	private Button sendEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_reserve);
		init();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	private void init() {
		Bundle extras = getIntent().getExtras();
		resInfo = (HashMap<String, String>) extras.getSerializable("resInfo");
		
		sendEmail = (Button) findViewById(R.id.sendEmail);
		sendEmail.setOnClickListener(this);
		
		name = (TextView) findViewById(R.id.rName);
		area = (TextView) findViewById(R.id.area);
		numGuests = (TextView) findViewById(R.id.numGuests);
		date = (TextView) findViewById(R.id.date);
		time = (TextView) findViewById(R.id.time);
		comments = (TextView) findViewById(R.id.rComments);
		
		name.setText(resInfo.get("name"));
		area.setText(resInfo.get("area"));
		numGuests.setText(resInfo.get("guests"));
		date.setText(resInfo.get("date"));
		time.setText(resInfo.get("time"));
		comments.setText(resInfo.get("comments"));
	}

	@Override
	public void onClick(View arg0) {
		String[] emailAddr = {"bancho_p@abv.bg"};
		String message = createEmailText();
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddr);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Table Reservation");
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Please select your preferred app for sending the e-mail");
		startActivity(emailIntent);
	}

	private String createEmailText() {
		String email = "";
		email = "Reservation name: " + name.getText() + "\nPreferred area: " + area.getText() + "\nNumber of guests: " + numGuests.getText() + "\nDate: " + date.getText() +
				"\nTime: " + time.getText() + "\nComments: " + comments.getText() + "\n\nThis email was automatically generated by the T�rst bar App.";
		return email;
	}
	
}
