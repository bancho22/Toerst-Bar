package com.bani.toerstbar.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;

public class QuestionRetriever extends AsyncTask<Void, Void, ServerResponse> {
	
	private QuestionView questionView;
	
	public QuestionRetriever(QuestionView questionView) {
		this.questionView = questionView;
	}

	@Override
	protected ServerResponse doInBackground(Void... params) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://bani-productions.cloudapp.net/ToerstTesting/api/question/today");
		ServerResponse servResponse = new ServerResponse();
		try {
			HttpResponse response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			InputStream content = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while((line = reader.readLine()) != null){
				builder.append(line);
			}
			String jsonStr = builder.toString();
			JsonObject json = new JsonParser().parse(jsonStr).getAsJsonObject();
			servResponse.setStatus(statusCode);
			
			if(statusCode == 200){
				String question = json.get("question").getAsString();
				JsonArray jsonAnswers = json.get("answers").getAsJsonArray();
				ArrayList<String> answers = new ArrayList<String>();
				for (JsonElement jsonAnswer : jsonAnswers) {
					answers.add(jsonAnswer.getAsString());
				}
				servResponse.setQuestion(question);
				servResponse.setPossibleAnswers(answers);
			}else{
				String serverMsg = json.get("msg").getAsString();
				servResponse.setServerMsg(serverMsg);
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
		if (result.getStatus() == 200) {
			questionView.initializeSuccess(result);
		}else{
			questionView.initializeFail(result);
		}
		super.onPostExecute(result);
	}

}
