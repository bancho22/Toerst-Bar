package com.bani.toerstbar.reserve;

import java.util.HashMap;

import com.bani.toerstbar.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NameComments extends Activity implements OnClickListener {
	
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
		
		et_name = (EditText) findViewById(R.id.name);
		et_comments = (EditText) findViewById(R.id.comments);
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View arg0) {
		String name = "";
		name = et_name.getText().toString();
		if(name.isEmpty()){
			/*Dialog dialog = new Dialog(NameComments.this);
			dialog.setContentView(R.layout.no_name);
			dialog.setCancelable(true);
			dialog.show();*/
			Intent intent = new Intent("com.bani.toerstbar.popups.MISSING_NAME");
			startActivity(intent);
			return;
		}
		resInfo.put("name", name);
		
		String comments = "";
		comments = et_comments.getText().toString();
		resInfo.put("comments", comments);
		
		if(comments.isEmpty()){
			resInfo.put("comments", "none");
		}
		Intent intent = new Intent("com.bani.toerstbar.reserve.FINISH_RESERVE");
		Bundle extras = new Bundle();
		extras.putSerializable("resInfo", resInfo);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
}
