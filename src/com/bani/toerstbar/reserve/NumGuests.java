package com.bani.toerstbar.reserve;

import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class NumGuests extends Activity implements OnClickListener, OnValueChangeListener, NamingContract {

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
		resInfo = (HashMap<String, String>) extras.getSerializable(RESV_INFO);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		picker = (NumberPicker) findViewById(R.id.picker);
		picker.setMinValue(1);
		picker.setMaxValue(30);
		picker.setWrapSelectorWheel(false);
		picker.setOnValueChangedListener(this);
		
		if(resInfo.get(GUESTS_NUM) != null){
			int value = Integer.parseInt(resInfo.get(GUESTS_NUM));
			picker.setValue(value);
			resInfo.put(GUESTS_NUM, Integer.toString(value));
		}else{
			resInfo.put(GUESTS_NUM, Integer.toString(1));
		}
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent("com.bani.toerstbar.reserve.PICK_DATE");
		Bundle extras = new Bundle();
		extras.putSerializable(RESV_INFO, resInfo);
		intent.putExtras(extras);
		startActivity(intent);
	}

	@Override
	public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
		resInfo.put(GUESTS_NUM, picker.getValue() + "");
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == android.R.id.home){
			Intent intent = new Intent("com.bani.toerstbar.reserve.PICK_AREA");
			Bundle extras = new Bundle();
			extras.putSerializable(RESV_INFO, resInfo);
			intent.putExtras(extras);
			startActivity(intent);
		}
		return true;
	}
}
