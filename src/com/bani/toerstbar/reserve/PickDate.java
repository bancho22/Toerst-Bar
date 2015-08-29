package com.bani.toerstbar.reserve;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

public class PickDate extends Activity implements OnClickListener, OnDateChangedListener {

	private DatePicker datePicker;
	private Button next;
	private HashMap<String,String> resInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_date);
		init();
		
		//below method not working properly
		//setUpDate();
	}


	private void init() {
		Bundle extras = getIntent().getExtras();
		resInfo = (HashMap<String, String>) extras.getSerializable("resInfo");
		
		Calendar cal = Calendar.getInstance();
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
		
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		resInfo.put("date", retrieveDateFromPicker());
	}


	private String retrieveDateFromPicker() {
		int dayOfM = datePicker.getDayOfMonth();
		int iMonth = datePicker.getMonth();
		int year = datePicker.getYear();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, dayOfM);
		cal.set(Calendar.MONTH, iMonth);
		cal.set(Calendar.YEAR, year);
		
		int iDay = cal.get(Calendar.DAY_OF_WEEK);
		String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String day = dayNames[iDay - 1];
	
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		dayOfM = cal.get(Calendar.DAY_OF_MONTH);
		iMonth = cal.get(Calendar.MONTH);

		String month = "";
		String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		month = monthNames[iMonth];
		
		String sDate = month + " " + dayOfM + ", " + day; //should look like, e.g.: "October 22, Sunday"
		return sDate;
	}


	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent("com.bani.toerstbar.reserve.PICK_TIME");
		Bundle extras = new Bundle();
		extras.putSerializable("resInfo", resInfo);
		intent.putExtras(extras);
		startActivity(intent);
	}


	@Override
	public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
		resInfo.put("date", retrieveDateFromPicker());
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
