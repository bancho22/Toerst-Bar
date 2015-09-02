package com.bani.toerstbar.menu;

import java.util.ArrayList;

import com.bani.toerstbar.R;
import com.bani.toerstbar.R.drawable;
import com.bani.toerstbar.R.layout;
import com.bani.toerstbar.db.DataAdapter;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
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
		
		DataAdapter data = new DataAdapter(this);
		data.createDatabase();
		data.open();
		categories = data.getCategories();
		data.close();
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == android.R.id.home){
			finish();
		}
		return true;
	}
	
	
}
