package com.bani.toerstbar.menu;

import java.util.ArrayList;
import java.util.HashMap;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.drawable;
import com.bani.toerstbar.R.layout;
import com.bani.toerstbar.db.DataAdapter;
import com.bani.toerstbar.entity.*;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MultipleDrinksView extends ListActivity {
	
	private String category;
	private HashMap<String, Drink> drinks;
	ArrayList<String> drinksDisplayInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		Bundle bundle = getIntent().getExtras();
		category = bundle.getString("category");
		drinks = new HashMap<String, Drink>();
		drinksDisplayInfo = new ArrayList<String>();
		setTitle(category);
		
//		if(category.equals("Fadøl")){
//			drinks.add("Grøn Tuborg\n42 kr.");
//			drinks.add("Tuborg Classic\n45 kr.");
//		}else if(category.equals("Flaskeøl")){
//			drinks.add("Tuborg Guld\n39 kr.");
//			drinks.add("Corona Extra\n39 kr.");
//		}else if(category.equals("Kæmpe Cocktails")){
//			drinks.add("Long Island Iced Tea\n99 kr.");
//			drinks.add("Tørst Special\n99 kr.");
//		}
//		for(int i = 3; i < 20; i++){
//			drinks.add("drink " + i);
//		}
		
		DataAdapter data = new DataAdapter(this);
		data.createDatabase();
		data.open();
		for(Drink d : data.getMultipleDrinks(category)){
			String drinkDisplayInfo = d.getDrinkName() + "\n" + d.getPrice() + " kr.";
			drinks.put(drinkDisplayInfo, d);
			drinksDisplayInfo.add(drinkDisplayInfo);
		}
		data.close();
		
		getListView().setBackgroundResource(R.drawable.wood);
		setListAdapter(new ArrayAdapter<String>(MultipleDrinksView.this, R.layout.my_text_for_list_activity, drinksDisplayInfo));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String selectedStr = drinksDisplayInfo.get(position);
		String drinkName = drinks.get(selectedStr).getDrinkName();
		Bundle bundle = new Bundle();
		bundle.putString("drinkName", drinkName);
		Intent intent = new Intent("com.bani.toerstbar.menu.SINGLEDRINK");
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
}
