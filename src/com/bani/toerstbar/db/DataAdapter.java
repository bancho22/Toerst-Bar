package com.bani.toerstbar.db;

import java.io.IOException;
import java.util.ArrayList;

import com.bani.toerstbar.entity.Drink;
import com.bani.toerstbar.entity.Ingredient;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataAdapter {
	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;

	public DataAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public DataAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public DataAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public ArrayList<String> getCategories() {
		try {
			ArrayList<String> categories = new ArrayList<String>();
			String sql = "SELECT DISTINCT Category FROM Drink";

			Cursor mCur = mDb.rawQuery(sql, null);

			if (mCur != null) {
				mCur.moveToFirst();
				categories.add(mCur.getString(mCur.getColumnIndex("Category")));
				while (mCur.moveToNext()) {
					categories.add(mCur.getString(mCur
							.getColumnIndex("Category")));
				}
			}
			return categories;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getCategories >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public ArrayList<Drink> getMultipleDrinks(String category) {
		try {
			ArrayList<Drink> drinks = new ArrayList<Drink>();
			String sql = "SELECT * FROM Drink WHERE Category = '" + category
					+ "'";

			Cursor mCur = mDb.rawQuery(sql, null);

			if (mCur != null) {
				while (mCur.moveToNext()) {
					int id = mCur.getInt(mCur.getColumnIndex("_id"));
					String drinkName = mCur.getString(mCur
							.getColumnIndex("DrinkName"));
					int price = mCur.getInt(mCur.getColumnIndex("Price"));
					ArrayList<Ingredient> ings = getDrinkIngredients(id);

					drinks.add(new Drink(id, drinkName, category, price, ings));
				}
			}
			return drinks;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getMultipleDrinks >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	
	

	private ArrayList<Ingredient> getDrinkIngredients(int drinkID) {
		try {
			ArrayList<Ingredient> ings = new ArrayList<Ingredient>();
			String sql = "SELECT IngredientID FROM Mix WHERE DrinkID = '"
					+ drinkID + "'";

			Cursor mCur = mDb.rawQuery(sql, null);

			if (mCur != null) {
				while (mCur.moveToNext()) {
					int id = mCur.getInt(mCur.getColumnIndex("IngredientID"));
					ings.add(findIngredientById(id));
				}
			}
			return ings;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getDrinkIngredients >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	private Ingredient findIngredientById(int ingID) {
		try {
			String sql = "SELECT IngredientName FROM Ingredient WHERE IngredientID = '"
					+ ingID + "'";
			
			Cursor mCur = mDb.rawQuery(sql, null);
			
			if(mCur != null){
				String ingName = mCur.getString(mCur.getColumnIndex("IngName"));
				return new Ingredient(ingID, ingName);
			}
			
			return null;

		} catch (SQLException mSQLException) {
			Log.e(TAG, "findIngredientById >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
}