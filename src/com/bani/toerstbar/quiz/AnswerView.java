package com.bani.toerstbar.quiz;

import com.bani.toerstbar.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AnswerView extends ActionBarActivity implements OnClickListener {
	
	private TextView resultTV;
	private Button backToHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_view);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Bundle extras = getIntent().getExtras();
		String answer = extras.getString("answer");
		
		AnswerChecker aCheck = new AnswerChecker(this);
		aCheck.execute(answer);
		
		resultTV = (TextView) findViewById(R.id.result);
		backToHome = (Button) findViewById(R.id.backToHome);
		backToHome.setOnClickListener(this);
	}
	
	public void setResult(String text){
		resultTV.setText(text);
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

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
			case R.id.backToHome:
				finish();
				break;
		}
	}
	
}
