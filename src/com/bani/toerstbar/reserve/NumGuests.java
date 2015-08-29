package com.bani.toerstbar.reserve;

import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class NumGuests extends Activity implements OnClickListener, OnValueChangeListener {

	private Button next;
	private NumberPicker picker;
	private HashMap<String, String> resInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.num_guests);
		init();
	}

	private void init() {
		Bundle extras = getIntent().getExtras();
		resInfo = (HashMap<String, String>) extras.getSerializable("resInfo");
		
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		picker = (NumberPicker) findViewById(R.id.picker);
		picker.setMinValue(1);
		picker.setMaxValue(30);
		picker.setWrapSelectorWheel(false);
		picker.setOnValueChangedListener(this);
		
		resInfo.put("guests", "1");
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent("com.bani.toerstbar.reserve.PICK_DATE");
		Bundle extras = new Bundle();
		extras.putSerializable("resInfo", resInfo);
		intent.putExtras(extras);
		startActivity(intent);
	}

	@Override
	public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
		resInfo.put("guests", picker.getValue() + "");
	}
	
}
