package com.bani.toerstbar.reserve;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.id;
import com.bani.toerstbar.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class BookTable extends Activity implements OnItemSelectedListener {

	private Button sendEmail;
	private Spinner area;
	private String[] allAreas;
	private String preferredArea;
	private TextView test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_table);
		init();
	}

	private void init() {
		area = (Spinner) findViewById(R.id.area);
		test = (TextView) findViewById(R.id.test);
		allAreas = new String[3];
		allAreas[0] = "Basement";
		allAreas[1] = "Big Bar";
		allAreas[2] = "Small Bar";
		preferredArea = allAreas[0];
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_text_for_spinner, allAreas);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		area.setAdapter(adapter);
		area.setOnItemSelectedListener(this);
		test.setText(preferredArea);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		preferredArea = allAreas[arg2];
		test.setText(preferredArea);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
