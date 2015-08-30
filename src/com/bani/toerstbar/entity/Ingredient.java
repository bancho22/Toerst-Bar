package com.bani.toerstbar.entity;

public class Ingredient {

	private int id;
	private String ingName;
	
	
	public Ingredient(int id, String ingName) {
		super();
		this.id = id;
		this.ingName = ingName;
	}
	
	public int getId() {
		return id;
	}
	
	public String getIngName() {
		return ingName;
	}
	
	
	
}
