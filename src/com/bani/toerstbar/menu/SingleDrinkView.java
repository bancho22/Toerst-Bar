package com.bani.toerstbar.menu;

import java.util.ArrayList;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.id;
import com.bani.toerstbar.R.layout;
import com.bani.toerstbar.db.DataAdapter;
import com.bani.toerstbar.entity.Drink;
import com.bani.toerstbar.entity.Ingredient;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class SingleDrinkView extends Activity {

	private Drink drink;
	private TextView drinkName;
	private TextView ings;
	private TextView price;
	private TextView ingTit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_drink_view);
		init();
	}

	private void init() {
		drinkName = (TextView) findViewById(R.id.drinkName);
		ings = (TextView) findViewById(R.id.ings);
		price = (TextView) findViewById(R.id.price);
		ingTit = (TextView) findViewById(R.id.TextViewI);

		Bundle bundle = getIntent().getExtras();
		String drinkName = bundle.getString("drinkName");
		
		DataAdapter data = new DataAdapter(this);
		data.createDatabase();
		data.open();
		drink = data.getSingleDrink(drinkName);
		data.close();
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		setTitle(drink.getDrinkName());
		
		this.drinkName.setText(drink.getDrinkName());
		String ings = "";
		for (Ingredient i : drink.getIngs()) {
			ings += "\u2022 " + i.getIngName() + "\n";
		}
		this.ings.setText(ings);
		if(drink.getIngs().isEmpty()){
			this.ings.setVisibility(TextView.GONE);
			ingTit.setVisibility(TextView.GONE);
		}
		price.setText(drink.getPrice() + " kr.");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == android.R.id.home){
			finish();
		}
		return true;
	}
}
