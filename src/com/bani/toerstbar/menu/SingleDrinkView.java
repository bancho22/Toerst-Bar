package com.bani.toerstbar.menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.id;
import com.bani.toerstbar.R.layout;
import com.bani.toerstbar.db.DataAdapter;
import com.bani.toerstbar.entity.Drink;
import com.bani.toerstbar.entity.Ingredient;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleDrinkView extends Activity {

	private Drink drink;
	private TextView drinkName;
	private TextView desc;
	private TextView ings;
	private TextView price;
	private TextView ingTit;
	private ImageView imgV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_drink_view);
		init();
	}

	private void init() {
		drinkName = (TextView) findViewById(R.id.drinkName);
		desc = (TextView) findViewById(R.id.desc);
		ings = (TextView) findViewById(R.id.ings);
		price = (TextView) findViewById(R.id.price);
		ingTit = (TextView) findViewById(R.id.TextViewI);
		imgV = (ImageView) findViewById(R.id.imageView1);

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
		desc.setText(drink.getDesc());
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
		
		try {
			InputStream stream = getAssets().open(drink.getDrinkName() + ".png");
			Drawable img = Drawable.createFromStream(stream, null);
			imgV.setImageDrawable(img);
			stream.close();
			//getAssets().close();
		} catch (IOException e) {
			imgV.setVisibility(View.INVISIBLE);
			//e.printStackTrace();
		}
		
		/*if(drink.getImg() != null){
			imgV.setImageBitmap(drink.getImg());
		}*/
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
