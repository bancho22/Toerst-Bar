package com.bani.toerstbar.entity;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Drink {

	private int id;
	private String drinkName;
	private String category;
	private int price;
	private String desc;
	//private Bitmap img;
	private ArrayList<Ingredient> ings;
	
	public Drink(){
		ings = new ArrayList<Ingredient>();
	}
	
	public Drink(int id, String drinkName, String category, int price, String desc,
			ArrayList<Ingredient> ings) {
		super();
		this.id = id;
		this.drinkName = drinkName;
		this.category = category;
		this.price = price;
		this.desc = desc;
		this.ings = ings;
	}
	
	/*public Drink(int id, String drinkName, String category, int price, String desc,
			ArrayList<Ingredient> ings, Bitmap img) {
		super();
		this.id = id;
		this.drinkName = drinkName;
		this.category = category;
		this.price = price;
		this.desc = desc;
		this.ings = ings;
		this.img = img;
	}*/
	
	/*public Bitmap getImg(){
		return img;
	}*/
	
	public int getId() {
		return id;
	}
	public String getDrinkName() {
		return drinkName;
	}
	public String getCategory() {
		return category;
	}
	public int getPrice() {
		return price;
	}
	public ArrayList<Ingredient> getIngs() {
		return ings;
	}
	public void setIngs(ArrayList<Ingredient> ings) {
		this.ings = ings;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public void addIngredient(Ingredient ing){
		ings.add(ing);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
