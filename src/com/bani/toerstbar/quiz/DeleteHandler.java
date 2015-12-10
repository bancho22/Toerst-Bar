package com.bani.toerstbar.quiz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonObject;

import android.os.AsyncTask;

public class DeleteHandler extends AsyncTask<String, Void, Boolean> {

	private TimerView tv;
	
	public DeleteHandler(TimerView tv) {
		this.tv = tv;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		String androidID = params[0];
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete("http://toerstbar-baniproductions.rhcloud.com/api/quiz/" + androidID);
		httpDelete.setHeader("Content-type", "application/json");
		JsonObject result;
		try {
			HttpResponse response = client.execute(httpDelete);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200){
				return true;
			}
			else{
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);
		if(success){
			tv.drinkClaimed();
		}else{
			//sth, I dunno
		}
	}
}
