package com.bani.toerstbar.quiz;

import com.bani.toerstbar.R;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class QuestionView extends ActionBarActivity implements OnClickListener, OnCheckedChangeListener {
	
	private String answer;
	private TextView questionTV;
	private RadioGroup answersRG;
	private RadioButton button0, button1, button2, button3;
	private Button answerBtn, backToHome;
	private TextView failMsgTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		QuestionRetriever qRet = new QuestionRetriever(this);
		qRet.execute();
	}
	
	public void initializeSuccess(ServerResponse servResp){
		setContentView(R.layout.question_view);
		
		questionTV = (TextView) findViewById(R.id.questionTV);
		questionTV.setText(servResp.getQuestion());
		
		answersRG = (RadioGroup) findViewById(R.id.answersRG);
		answersRG.setOnCheckedChangeListener(this);
		
		button0 = (RadioButton) findViewById(R.id.radio0);
		button1 = (RadioButton) findViewById(R.id.radio1);
		button2 = (RadioButton) findViewById(R.id.radio2);
		button3 = (RadioButton) findViewById(R.id.radio3);
		
		button0.setText(servResp.getPossibleAnswers().get(0));
		button1.setText(servResp.getPossibleAnswers().get(1));
		button2.setText(servResp.getPossibleAnswers().get(2));
		button3.setText(servResp.getPossibleAnswers().get(3));
		
		answerBtn = (Button) findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(this);
		
		answer = (String) button0.getText();
	}
	
	public void initializeFail(ServerResponse servResp){
		setContentView(R.layout.question_fail);
		
		failMsgTV = (TextView) findViewById(R.id.failMsg);
		failMsgTV.setText(servResp.getServerMsg());
		
		backToHome = (Button) findViewById(R.id.backToHome);
		backToHome.setOnClickListener(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
			case R.id.answerBtn:
				Intent intent = new Intent("com.bani.toerstbar.quiz.ANSWER_VIEW");
				Bundle extras = new Bundle();
				extras.putString("answer", answer);
				intent.putExtras(extras);
				startActivity(intent);
				break;
			case R.id.backToHome:
				finish();
				break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch(arg1){
			case R.id.radio0:
				answer = (String) button0.getText();
				break;
			case R.id.radio1:
				answer = (String) button1.getText();
				break;
			case R.id.radio2:
				answer = (String) button2.getText();
				break;
			case R.id.radio3:
				answer = (String) button3.getText();
				break;
		}
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
