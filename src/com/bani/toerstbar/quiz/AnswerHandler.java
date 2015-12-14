package com.bani.toerstbar.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AnswerHandler extends AsyncTask<String, Void, JsonObject> {
	
	private QuestionView qv;
	
	public AnswerHandler(QuestionView qv) {
		this.qv = qv;
	}

	@Override
	protected JsonObject doInBackground(String... params) {
		JsonObject jsonToSend = new JsonObject();

		jsonToSend.addProperty("androidID", params[0]);

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://toerstbar-baniproductions.rhcloud.com/api/quiz/answer");
		httpPost.setHeader("Content-type", "application/json");
		StringEntity entity;
		JsonObject result;
		try {
			entity = new StringEntity(jsonToSend.toString());
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			HttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				InputStream content = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				String jsonStr = builder.toString();
				result = new JsonParser().parse(jsonStr).getAsJsonObject();
				result.addProperty("status", "notFail");
			}
			else{
				result = new JsonObject();
				result.addProperty("status", "fail");
			}
		} catch (ClientProtocolException e) {
			result = new JsonObject();
			result.addProperty("status", "fail");
			e.printStackTrace();
		} catch (IOException e) {
			result = new JsonObject();
			result.addProperty("status", "fail");
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	protected void onPostExecute(JsonObject result) {
		super.onPostExecute(result);
		String status = result.get("status").getAsString();
		if(status.equals("fail")){
			qv.sthWrongWrong();
		}
		else{
			int secondsLeft = result.get("timeLeft").getAsInt();
			qv.startTimerActivityOnCorrectAnswer(secondsLeft);
		}
	}
	
}
