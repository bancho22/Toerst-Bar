package com.bani.toerstbar.menu;

import java.util.ArrayList;
import java.util.HashMap;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.drawable;
import com.bani.toerstbar.R.layout;
import com.bani.toerstbar.db.DataAdapter;
import com.bani.toerstbar.entity.*;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
		
		DataAdapter data = new DataAdapter(this);
		data.createDatabase();
		data.open();
		for(Drink d : data.getMultipleDrinks(category)){
			String drinkDisplayInfo = d.getDrinkName() + "\n" + d.getPrice() + " kr.";
			drinks.put(drinkDisplayInfo, d);
			drinksDisplayInfo.add(drinkDisplayInfo);
		}
		data.close();
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == android.R.id.home){
			finish();
		}
		return true;
	}
	
}
