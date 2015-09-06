package com.bani.toerstbar.db;

public interface DB_Contract {

	//tables
	public static final String DRINK_TABLE = "Drink";
	public static final String INGREDIENT_TABLE = "Ingredient";
	public static final String MIX_TABLE = "Mix";
	
	//primary key column in Drink and Ingredient tables
	public static final String ID_COLUMN = "_id";
	
	//columns in Drink table
	public static final String DRINK_NAME_COLUMN = "DrinkName";
	public static final String CATEGORY_COLUMN = "Category";
	public static final String PRICE_COLUMN = "Price";
	
	//column(s) in Ingredient table
	public static final String INGREDIENT_NAME_COLUMN = "IngName";
	
	//columns in Mix table
	public static final String DRINK_ID_COLUMN = "DrinkID";
	public static final String INGREDIENT_ID_COLUMN = "IngredientID";
	
}
