package com.bani.toerstbar.reserve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

public class PickDate extends Activity implements OnClickListener, OnDateChangedListener, NamingContract {

	private DatePicker datePicker;
	private Button next;
	private HashMap<String,String> resInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_date);
		init();
		
		//setUpDate() method not working properly
	}


	private void init() {
		Bundle extras = getIntent().getExtras();
		resInfo = (HashMap<String, String>) extras.getSerializable(RESV_INFO);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
				
		if(resInfo.get(DATE) != null){
			String date = resInfo.get(DATE);
			String[] splitted = date.split(",");
			splitted = splitted[0].split(" ");
			
			HashMap<String, Integer> months = new HashMap<String, Integer>();
			int i = 0;
			for(String m : MONTH_NAMES){
				months.put(m, i++);
			}
			month = months.get(splitted[0]);
			dayOfMonth = Integer.parseInt(splitted[1]);
			
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			month = cal.get(Calendar.MONTH);
			dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		}
		
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		datePicker.init(year, month, dayOfMonth, this);
		
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		resInfo.put(DATE, retrieveDateFromPicker());
	}


	private String retrieveDateFromPicker() {
		int dayOfM = datePicker.getDayOfMonth();
		int monthInt = datePicker.getMonth();
		int year = datePicker.getYear();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, dayOfM);
		cal.set(Calendar.MONTH, monthInt);
		cal.set(Calendar.YEAR, year);
			
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		dayOfM = cal.get(Calendar.DAY_OF_MONTH);
		monthInt = cal.get(Calendar.MONTH);

		int dayInt = cal.get(Calendar.DAY_OF_WEEK);
		String day = DAY_NAMES[dayInt - 1];
		String month = MONTH_NAMES[monthInt];
		
		String dateStr = month + " " + dayOfM + ", " + day; //should look like this, e.g.: "October 22, Sunday"
		return dateStr;
	}


	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent("com.bani.toerstbar.reserve.PICK_TIME");
		Bundle extras = new Bundle();
		extras.putSerializable(RESV_INFO, resInfo);
		intent.putExtras(extras);
		startActivity(intent);
	}


	@Override
	public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
		resInfo.put(DATE, retrieveDateFromPicker());
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
			Intent intent = new Intent("com.bani.toerstbar.reserve.NUM_GUESTS");
			Bundle extras = new Bundle();
			extras.putSerializable(RESV_INFO, resInfo);
			intent.putExtras(extras);
			startActivity(intent);
		}
		return true;
	}
	
	
/*	private void setUpDate() {
		Calendar c = Calendar.getInstance();
		Date today = c.getTime();
		long minDate = today.getTime();
		datePicker.setMinDate(minDate);
		c.set(c.MONTH, c.get(c.MONTH)+2);
		Date twoMonthsFromNow = c.getTime();
		long maxDate = twoMonthsFromNow.getTime();
		datePicker.setMaxDate(maxDate);
		}*/
}
