package com.bani.toerstbar.quiz;

import java.util.ArrayList;

public class ServerResponse {

	private int status;
	private String question;
	private ArrayList<String> possibleAnswers;
	private String serverMsg;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public ArrayList<String> getPossibleAnswers() {
		return possibleAnswers;
	}
	public void setPossibleAnswers(ArrayList<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}
	public String getServerMsg() {
		return serverMsg;
	}
	public void setServerMsg(String serverMsg) {
		this.serverMsg = serverMsg;
	}
	
}
