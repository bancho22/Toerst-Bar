package com.bani.toerstbar.menu;

import java.util.ArrayList;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.drawable;
import com.bani.toerstbar.R.layout;
import com.bani.toerstbar.db.DataAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuCategories extends ListActivity {

	private ArrayList<String> categories;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	
	private void init() {
		categories = new ArrayList<String>();
//		categories.add("Fadøl");
//		categories.add("Flaskeøl");
//		categories.add("Kæmpe Cocktails");
//		
//		for (int i = 4; i < 20; i++){
//			categories.add("category " + i);
//		}
		
		DataAdapter data = new DataAdapter(this);
		data.createDatabase();
		data.open();
		categories = data.getCategories();
		data.close();
		
		getListView().setBackgroundResource(R.drawable.wood);
		setListAdapter(new ArrayAdapter<String>(MenuCategories.this, R.layout.my_text_for_list_activity, categories));
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String category = categories.get(position);
		Bundle bundle = new Bundle();
		bundle.putString("category", category);
		Intent intent = new Intent("com.bani.toerstbar.menu.MULTIPLEDRINKS");
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	
}
