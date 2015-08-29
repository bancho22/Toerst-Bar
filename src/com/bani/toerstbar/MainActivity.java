package com.bani.toerstbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	//private String[] appFeatures;
	private Button viewMenu;
	private Button bookTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        
    }

    private void initialize() {
    	/*        ListView list = (ListView) findViewById(R.id.listView1);
        appFeatures = new String[2];
        appFeatures[0] = "View Menu";
        appFeatures[1] = "Book a Table";
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appFeatures));        
    	 */
		
    	viewMenu = (Button) findViewById(R.id.viewMenu);
    	viewMenu.setOnClickListener(this);
    	bookTable = (Button) findViewById(R.id.bookTable);
		bookTable.setOnClickListener(this);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
			case R.id.viewMenu:
/*				try {
					Class menuC = Class.forName("com.example.myapp.MenuCategories");
					Intent i = new Intent(this, menuC);
					startActivity(i);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}*/
				startActivity(new Intent("com.bani.toerstbar.menu.MENUCATEGORIES"));
				break;
			case R.id.bookTable:
				//startActivity(new Intent("com.bani.toerstbar.reserve.BOOKTABLE"));
				startActivity(new Intent("com.bani.toerstbar.reserve.PICK_AREA"));
				break;
		}
	}
}
