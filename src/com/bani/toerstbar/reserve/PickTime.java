package com.bani.toerstbar.reserve;

import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class PickTime extends Activity implements OnTimeChangedListener, OnClickListener {
	
	private HashMap<String, String> resInfo;
	private TimePicker timePicker;
	private Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_time);
		init();
	}

	private void init() {
		Bundle extras = getIntent().getExtras();
		resInfo = (HashMap<String, String>) extras.getSerializable("resInfo");
		
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(this);
		timePicker.setCurrentHour(21);
		timePicker.setCurrentMinute(0);
		
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		resInfo.put("time", retrieveTimeFromPicker());
	}

	private String retrieveTimeFromPicker() {
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();
		String time = hour + ":" + minute;
		time = formatTime(time);
		return time;
	}

	private String formatTime(String time) {
		if(time.length() == 5){
			return time;
		}else if(time.length() == 4){
			String [] splitted = time.split(":");
			if(splitted[0].length() == 1){
				time = "0" + time;
				return time;
			}else{
				time = splitted[0] + ":0" + splitted[1];
				return time;
			}
		}else{
			String splitted[] = time.split(":");
			time = "0" + splitted[0] + ":0" + splitted[1];
			return time;
		}
	}

	@Override
	public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
		resInfo.put("time", retrieveTimeFromPicker());
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent("com.bani.toerstbar.reserve.NAME_COMMENTS");
		Bundle extras = new Bundle();
		extras.putSerializable("resInfo", resInfo);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
}
