package com.bani.toerstbar;

import java.util.concurrent.TimeUnit;

import com.bani.toerstbar.quiz.RequestHandler;
import com.google.gson.JsonObject;

import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout.Alignment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	private Button viewMenu;
	private Button bookTable;
	private Button winShot;
	private TextView timerTV, info;
	private JsonObject object;
	//private int secondsLeft;
	private int color;
	private RequestHandler rh;
	private String android_id;
	private MyCount count;
	private ProgressBar loading;
	private ImageButton viewMenuImgBtn, bookTableImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {		
    	viewMenu = (Button) findViewById(R.id.viewMenu);
    	viewMenu.setOnClickListener(this);
    	viewMenu.setVisibility(View.INVISIBLE);
    	bookTable = (Button) findViewById(R.id.bookTable);
		bookTable.setOnClickListener(this);
		bookTable.setVisibility(View.INVISIBLE);
		winShot = (Button) findViewById(R.id.winShot);
		winShot.setVisibility(View.INVISIBLE);
		timerTV = (TextView) findViewById(R.id.timer);
		timerTV.setVisibility(View.INVISIBLE);
		loading = (ProgressBar) findViewById(R.id.loading);
		info = (TextView) findViewById(R.id.info);
		info.setVisibility(View.INVISIBLE);
		info.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		
		viewMenuImgBtn = (ImageButton) findViewById(R.id.viewMenuImgBtn);
		viewMenuImgBtn.setOnClickListener(this);
		bookTableImgBtn = (ImageButton) findViewById(R.id.bookTableImgBtn);
		bookTableImgBtn.setOnClickListener(this);
		
		android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		rh = new RequestHandler(this, null);
		rh.execute(android_id);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	winShot.setVisibility(View.INVISIBLE);
    	timerTV.setVisibility(View.INVISIBLE);
    	info.setVisibility(View.INVISIBLE);
    	loading.setVisibility(View.VISIBLE);
    	rh = new RequestHandler(this, null);
    	rh.execute(android_id);
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent;
		Bundle extras;
		switch(id){
			case R.id.viewMenu:
			case R.id.viewMenuImgBtn:
				startActivity(new Intent("com.bani.toerstbar.menu.MENUCATEGORIES"));
				break;
			case R.id.bookTable:
			case R.id.bookTableImgBtn:
				startActivity(new Intent("com.bani.toerstbar.reserve.NUM_GUESTS"));
				break;
			case R.id.winShot:
				intent = new Intent("com.bani.toerstbar.quiz.QUESTION_VIEW");
				extras = new Bundle();
				extras.putString("object", object.toString());
				intent.putExtras(extras);
				startActivity(intent);
				break;
			case R.id.timer:
				intent = new Intent("com.bani.toerstbar.quiz.TIMER_VIEW");
				extras = new Bundle();
				extras.putInt("color", color);
				//extras.putInt("secondsLeft", secondsLeft);
				extras.putBoolean("justAnswered", false);
				intent.putExtras(extras);
				startActivity(intent);
				break;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(count != null){
			//count.cancel();
		}
	}
	
	public void initTimer(JsonObject object){
		timerTV.setVisibility(View.VISIBLE);
		winShot.setVisibility(View.INVISIBLE);
		loading.setVisibility(View.INVISIBLE);
		timerTV.setClickable(true);
		timerTV.setOnClickListener(this);
		this.object = object;
		Seconds.secondsLeft = object.get("timeLeft").getAsInt();
		String untilWhat = object.get("timeLeftUntil").getAsString();
		if(untilWhat.equals("becomesAvailable")){
			timerTV.setTextColor(Color.parseColor("#960018"));
			color = Color.RED;
			info.setText("Time left before your shot becomes available:");
			info.setVisibility(View.VISIBLE);
		}
		else if(untilWhat.equals("expires")){
			timerTV.setTextColor(Color.GREEN);
			color = Color.GREEN;
			info.setText("There is a shot waiting for you at the bar right now");
			info.setVisibility(View.VISIBLE);
		}
		count = new MyCount(Seconds.secondsLeft * 1000, 1000);
		count.start();
	}
	
	
	public void initWinButton(JsonObject object){
		winShot.setVisibility(View.VISIBLE);
		timerTV.setVisibility(View.INVISIBLE);
		loading.setVisibility(View.INVISIBLE);
		info.setText("Answer a question and win a free shot!");
		info.setVisibility(View.VISIBLE);
		winShot.setOnClickListener(this);
		this.object = object;
	}
	
	public void initFail(){
		timerTV.setVisibility(View.INVISIBLE);
		winShot.setVisibility(View.INVISIBLE);
		loading.setVisibility(View.INVISIBLE);
		info.setText("Can't connect to server");
		info.setVisibility(View.VISIBLE);
	}
	
	
public class MyCount extends CountDownTimer{
		
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            timerTV.setText("00:00:00");
            if(color == Color.RED){
            	//notification
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
        	String displayValue = String.format("%02d:%02d:%02d",
        			TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
        			TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - 
        			TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
        		    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - 
        		    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
        		);
        	timerTV.setText(displayValue);
        	Seconds.secondsLeft = (int) millisUntilFinished / 1000;
        }
    }
}
