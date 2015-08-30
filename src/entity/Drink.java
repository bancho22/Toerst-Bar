package entity;

import java.util.ArrayList;

public class Drink {

	private int id;
	private String drinkName;
	private String category;
	private int price;
	private ArrayList<Ingredient> ings;
	
	
	
	public Drink(int id, String drinkName, String category, int price,
			ArrayList<Ingredient> ings) {
		super();
		this.id = id;
		this.drinkName = drinkName;
		this.category = category;
		this.price = price;
		this.ings = ings;
	}
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
	
}
