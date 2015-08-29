package com.bani.toerstbar.reserve;


import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PickArea extends Activity implements OnCheckedChangeListener, OnClickListener {

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
		buttons = (RadioGroup) findViewById(R.id.radioGroup1);
		buttons.setOnCheckedChangeListener(this);
		
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		resInfo = new HashMap<String, String>();
		resInfo.put("area", "Basement");
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch(arg1){
			case R.id.basement:
				resInfo.put("area", "Basement");
				break;
			case R.id.bigBar:
				resInfo.put("area", "Big Bar");
				break;
			case R.id.smallBar:
				resInfo.put("area", "Small Bar");
				break;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent("com.bani.toerstbar.reserve.NUM_GUESTS");
		Bundle extras = new Bundle();
		extras.putSerializable("resInfo", resInfo);
		intent.putExtras(extras);
		startActivity(intent);
	}

}
