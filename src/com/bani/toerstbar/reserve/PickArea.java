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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PickArea extends Activity implements OnCheckedChangeListener, OnClickListener, NamingContract {

	private RadioGroup buttons;
	private Button next;
	private HashMap<String, String> resInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_area);
		init();
	}

	private void init() {
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			resInfo = (HashMap<String, String>) extras.getSerializable(RESV_INFO);
		}else{
			resInfo = new HashMap<String, String>();
		}
		
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		buttons = (RadioGroup) findViewById(R.id.radioGroup1);
		buttons.setOnCheckedChangeListener(this);
		
		if(resInfo.get(AREA) != null){
			String area = resInfo.get(AREA);
			switch(area){
				case BASEMENT:
					buttons.check(R.id.basement);
					resInfo.put(AREA, BASEMENT);
					break;
				case BIG_BAR:
					buttons.check(R.id.bigBar);
					resInfo.put(AREA, BIG_BAR);
					break;
				case SMALL_BAR:
					buttons.check(R.id.smallBar);
					resInfo.put(AREA, SMALL_BAR);
					break;
			}
		}else{
			resInfo = new HashMap<String, String>();
			resInfo.put(AREA, BASEMENT);
		}
				
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch(arg1){
			case R.id.basement:
				resInfo.put(AREA, BASEMENT);
				break;
			case R.id.bigBar:
				resInfo.put(AREA, BIG_BAR);
				break;
			case R.id.smallBar:
				resInfo.put(AREA, SMALL_BAR);
				break;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent("com.bani.toerstbar.reserve.NUM_GUESTS");
		Bundle extras = new Bundle();
		extras.putSerializable(RESV_INFO, resInfo);
		intent.putExtras(extras);
		startActivity(intent);
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
			finish();
		}
		return true;
	}

}
