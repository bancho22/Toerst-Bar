package com.bani.toerstbar.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;

public class AnswerChecker extends AsyncTask<String, Void, ServerResponse> {

	private AnswerView answerView;
	
	public AnswerChecker(AnswerView answerView){
		this.answerView = answerView;
	}
	
	@Override
	protected ServerResponse doInBackground(String... params) {
		JsonObject jsonToSend = new JsonObject();
		jsonToSend.addProperty("answer", params[0]);
		
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://bani-productions.cloudapp.net/ToerstTesting/api/question/today");
		httpPost.setHeader("Content-type", "application/json");
		StringEntity entity;
		try {
			entity = new StringEntity(jsonToSend.toString());
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ServerResponse servResponse = new ServerResponse();
		try {
			HttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			InputStream content = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while((line = reader.readLine()) != null){
				builder.append(line);
			}
			String jsonStr = builder.toString();
			//JsonObject json = new JsonParser().parse(jsonStr).getAsJsonObject();
			servResponse.setStatus(statusCode);
			
			if(statusCode == 200){
				//boolean answeredCorrectly = json.get("answeredCorrectly").getAsBoolean();
				//servResponse.setServerMsg(String.valueOf(answeredCorrectly));
				if (jsonStr.contains("true")) {
					servResponse.setServerMsg("true");
				}
				else{
					servResponse.setServerMsg("false");
				}
			}else{
				//String serverMsg = json.get("msg").getAsString();
				//servResponse.setServerMsg(serverMsg);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return servResponse;
	}

	
	@Override
	protected void onPostExecute(ServerResponse result) {
		super.onPostExecute(result);
		if (result.getStatus() == 200) {
			if (result.getServerMsg().equals("true")) {
				answerView.setResult("Congratulations! You just won a free shot of your choice! Go claim it at the bar.");
			}
			else{
				answerView.setResult("Sorry, the answer you gave was incorrect. Try again tomorrow!");
			}
		}
		else{
			answerView.setResult("Your device has already been used to answer the question");
		}
	}
}
