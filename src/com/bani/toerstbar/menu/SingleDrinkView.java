package com.bani.toerstbar.menu;

import java.util.ArrayList;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.id;
import com.bani.toerstbar.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleDrinkView extends Activity {

	private String drink;
	private String category;
	private TextView drinkName;
	private TextView ing;
	private TextView price;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_drink_view);
		init();
	}

	private void init() {
		drinkName = (TextView) findViewById(R.id.drinkName);
		ing = (TextView) findViewById(R.id.ing);
		price = (TextView) findViewById(R.id.price);
		
		Bundle bundle = getIntent().getExtras();
		category = bundle.getString("category");
		drink = bundle.getString("drink");
		String[] splitted = drink.split("\n");
		drink = splitted[0];
		String drinkPrice = splitted[1];
		drinkName.setText(drink);
		price.setText(drinkPrice);
		ArrayList<String> ingredients = new ArrayList<String>();
		if(category.contains("Cocktails")){
			if(drink.contains("Long Island")){
				ingredients.add("rom");
				ingredients.add("vodka");
				ingredients.add("gin");
				ingredients.add("tequila");
				ingredients.add("triple sec");
				ingredients.add("lime");
				ingredients.add("cola");
			}else if(drink.contains("Special")){
				ingredients.add("rom");
				ingredients.add("vodka");
				ingredients.add("gin");
				ingredients.add("whiskey");
				ingredients.add("lime");
				ingredients.add("cola");
			}
			String ing_view_mode = "";
			for (String s : ingredients){
				ing_view_mode += s + ", ";
			}
			ing_view_mode = ing_view_mode.substring(0, ing_view_mode.length() - 2);
			ing.setText(ing_view_mode);
		}
	}
	
}
