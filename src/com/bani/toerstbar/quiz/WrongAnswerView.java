package com.bani.toerstbar.quiz;

import com.bani.toerstbar.R;
import com.google.gson.JsonObject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WrongAnswerView extends ActionBarActivity implements OnClickListener {

	private Button backToHomeBtn, tryAgainBtn;
	private TextView failMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.wrong_answer);
		
		backToHomeBtn = (Button) findViewById(R.id.backToHome);
		tryAgainBtn = (Button) findViewById(R.id.tryAgain);
		failMsg = (TextView) findViewById(R.id.failMsg);
		
		backToHomeBtn.setOnClickListener(this);
		tryAgainBtn.setOnClickListener(this);
	}
	
	public void showAnotherQuestion(JsonObject object){
		Intent intent = new Intent("com.bani.toerstbar.quiz.QUESTION_VIEW");
		Bundle extras = new Bundle();
		extras.putString("object", object.toString());
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	public void showFail(){
		failMsg.setText("Unfortunately something went wrong.");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch(id){
			case R.id.backToHome:
				finish();
				break;
			case R.id.tryAgain:
				RequestHandler rh = new RequestHandler(null, this);
				String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
				rh.execute(android_id);
				break;
		}
	}
	
}
