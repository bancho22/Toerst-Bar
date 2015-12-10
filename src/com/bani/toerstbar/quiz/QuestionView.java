package com.bani.toerstbar.quiz;

import java.util.ArrayList;
import java.util.Random;

import com.bani.toerstbar.R;
import com.bani.toerstbar.Seconds;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class QuestionView extends ActionBarActivity implements OnClickListener, OnCheckedChangeListener {
	
	private String question, userAnswer, correctAnswer;
	private TextView questionTV, sthWentWrong;
	private RadioGroup answersRG;
	private RadioButton button0, button1, button2, button3;
	private Button answerBtn, backToHome;
	private ArrayList<String> possibleAnswers;
	private ProgressBar loading1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.question_view);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		loading1 = (ProgressBar) findViewById(R.id.loading1);
		loading1.setVisibility(View.INVISIBLE);
		
		Bundle extras = getIntent().getExtras();
		String obj = extras.getString("object");
		JsonObject object = new JsonParser().parse(obj).getAsJsonObject();
		question = object.get("question").getAsString();
		correctAnswer = object.get("correctAnswer").getAsString();
		JsonArray inctAns = object.get("incorrectAnswers").getAsJsonArray();
		possibleAnswers = new ArrayList<String>();
		int i = 0;
		int y = new Random().nextInt(4);
		for(JsonElement ans : inctAns){
			if(i == y){
				possibleAnswers.add(correctAnswer);
			}
			possibleAnswers.add(ans.getAsString());
			i++;
		}
		if(y == 3){
			possibleAnswers.add(correctAnswer);
		}
		
		questionTV = (TextView) findViewById(R.id.questionTV);
		questionTV.setText(question);
		
		sthWentWrong = (TextView) findViewById(R.id.sthWentWrong);
		sthWentWrong.setVisibility(View.INVISIBLE);
		
		answersRG = (RadioGroup) findViewById(R.id.answersRG);
		answersRG.setOnCheckedChangeListener(this);
		
		button0 = (RadioButton) findViewById(R.id.radio0);
		button1 = (RadioButton) findViewById(R.id.radio1);
		button2 = (RadioButton) findViewById(R.id.radio2);
		button3 = (RadioButton) findViewById(R.id.radio3);
		
		button0.setText(possibleAnswers.get(0));
		button1.setText(possibleAnswers.get(1));
		button2.setText(possibleAnswers.get(2));
		button3.setText(possibleAnswers.get(3));
		
		answerBtn = (Button) findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(this);
		
		userAnswer = (String) button0.getText();
	}
	
	public void startTimerActivityOnCorrectAnswer(int secondsLeft){
		Intent intent = new Intent("com.bani.toerstbar.quiz.TIMER_VIEW");
		Bundle extras = new Bundle();
		extras.putBoolean("justAnswered", true);
		extras.putInt("color", Color.RED);
		//extras.putInt("secondsLeft", secondsLeft);
		Seconds.secondsLeft = secondsLeft;
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	public void sthWrongWrong(){
		sthWentWrong.setVisibility(View.VISIBLE);
		answerBtn.setClickable(false);
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
				if(userAnswer.equals(correctAnswer)){
					answerBtn.setVisibility(View.INVISIBLE);
					loading1.setVisibility(View.VISIBLE);
					
					AnswerHandler ah = new AnswerHandler(this);
					ah.execute(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
				}
				else{
					startActivity(new Intent("com.bani.toerstbar.quiz.WRONG_ANSWER"));
				}
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
				userAnswer = (String) button0.getText();
				break;
			case R.id.radio1:
				userAnswer = (String) button1.getText();
				break;
			case R.id.radio2:
				userAnswer = (String) button2.getText();
				break;
			case R.id.radio3:
				userAnswer = (String) button3.getText();
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
