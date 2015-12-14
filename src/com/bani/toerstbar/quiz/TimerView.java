package com.bani.toerstbar.quiz;

import java.util.concurrent.TimeUnit;

import com.bani.toerstbar.R;
import com.bani.toerstbar.Seconds;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class TimerView extends ActionBarActivity implements OnClickListener {
	
	private TextView infoTextTV, timerTV, sliding;// dontSlideTV;
	private Button backToHome;
	private SeekBar claimSb;
	private ProgressBar loading3;
	private View slideLayout;
	//private int secondsLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_view);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Bundle extras = getIntent().getExtras();	
		int color = extras.getInt("color");
		//secondsLeft = extras.getInt("secondsLeft");
		boolean justAnswered = extras.getBoolean("justAnswered");
		
		slideLayout = findViewById(R.id.slideLayout);
		infoTextTV = (TextView) findViewById(R.id.infoText);
		timerTV = (TextView) findViewById(R.id.timer);
		//dontSlideTV = (TextView) findViewById(R.id.dontslide);
		sliding = (TextView) findViewById(R.id.sliding);
		claimSb =(SeekBar) findViewById(R.id.claim);
		backToHome = (Button) findViewById(R.id.backToHome);
		loading3 = (ProgressBar) findViewById(R.id.loading3);
		loading3.setVisibility(View.INVISIBLE);
		
		timerTV.setTextColor(color);
		backToHome.setOnClickListener(this);
		
		MyCount countdown = new MyCount(Seconds.secondsLeft * 1000, 1000);
		countdown.start();
		
		if(color == Color.RED){
			//dontSlideTV.setVisibility(View.INVISIBLE);
			claimSb.setVisibility(View.INVISIBLE);
			slideLayout.setVisibility(View.INVISIBLE);
			if(justAnswered){
				infoTextTV.setText("Congratulations, you answered correctly. Your drink will become available in");
			}
			else{
				infoTextTV.setText("You will be able to claim your free drink in");
			}
		}
		else{
			infoTextTV.setText("Show this screen at the bar and claim your drink. Time left: ");
			final DeleteHandler dh = new DeleteHandler(this);
			claimSb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

		        @Override
		        public void onStopTrackingTouch(SeekBar seekBar) {

		            if (seekBar.getProgress() > 95) {
		            	//claimSb.setVisibility(View.INVISIBLE);
		            	//dontSlideTV.setVisibility(View.INVISIBLE);
		            	slideLayout.setVisibility(View.INVISIBLE);
		            	backToHome.setClickable(false);
		            	loading3.setVisibility(View.VISIBLE);
		            	dh.execute(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
		            } else {
		            	seekBar.setProgress(0);
		            }

		        }

		        @Override
		        public void onStartTrackingTouch(SeekBar seekBar) {


		        }

		        @Override
		        public void onProgressChanged(SeekBar seekBar, int progress,
		                boolean fromUser) {
		            if(progress>95){
		                //seekBar.setThumb(getResources().getDrawable(R.drawable.toerst_icon));
		            }

		        }
		    });
		}
	}
	
	
	public void drinkClaimed(){
		timerTV.setVisibility(View.INVISIBLE);
		//claimSb.setVisibility(View.INVISIBLE);
		//dontSlideTV.setVisibility(View.INVISIBLE);
		slideLayout.setVisibility(View.INVISIBLE);
		loading3.setVisibility(View.INVISIBLE);
		backToHome.setClickable(true);
		infoTextTV.setText("You have claimed your drink.");
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
	
public class MyCount extends CountDownTimer{
		
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            timerTV.setText("00:00:00");
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
        }
    }
	
}
