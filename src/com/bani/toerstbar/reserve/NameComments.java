package com.bani.toerstbar.reserve;

import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NameComments extends Activity implements OnClickListener, NamingContract {
	
	private HashMap<String, String> resInfo;
	private EditText et_name;
	private EditText et_comments;
	private Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.name_comments);
		init();
	}

	private void init() {
		Bundle extras = getIntent().getExtras();
		resInfo = (HashMap<String, String>) extras.getSerializable("resInfo");
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		et_name = (EditText) findViewById(R.id.name);
		et_comments = (EditText) findViewById(R.id.comments);
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		if(resInfo.get(NAME) != null){
			et_name.setText(resInfo.get(NAME));
		}
		
		if(resInfo.get(COMMENTS) != null){
			et_comments.setText(resInfo.get(COMMENTS));
		}
	}

	
	@Override
	public void onClick(View arg0) {
		String name = "";
		name = et_name.getText().toString();
		if(name.isEmpty()){
			Intent intent = new Intent("com.bani.toerstbar.popups.MISSING_NAME");
			startActivity(intent);
			return;
		}
		resInfo.put(NAME, name);
		
		String comments = "";
		comments = et_comments.getText().toString();
		resInfo.put(COMMENTS, comments);
		
		if(comments.isEmpty()){
			resInfo.put(COMMENTS, "none");
		}
		Intent intent = new Intent("com.bani.toerstbar.reserve.FINISH_RESERVE");
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
			Intent intent = new Intent("com.bani.toerstbar.reserve.PICK_TIME");
			Bundle extras = new Bundle();
			extras.putSerializable(RESV_INFO, resInfo);
			intent.putExtras(extras);
			startActivity(intent);
		}
		return true;
	}
	
}
