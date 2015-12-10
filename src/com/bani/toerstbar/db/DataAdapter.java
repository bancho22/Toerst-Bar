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

public class DataAdapter implements DB_Contract {
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
			String sql = "SELECT DISTINCT " + CATEGORY_COLUMN + " FROM " + DRINK_TABLE;

			Cursor mCur = mDb.rawQuery(sql, null);

			if (mCur != null) {
				mCur.moveToFirst();
				categories.add(mCur.getString(mCur.getColumnIndex(CATEGORY_COLUMN)));
				while (mCur.moveToNext()) {
					categories.add(mCur.getString(mCur
							.getColumnIndex(CATEGORY_COLUMN)));
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
			String sql = "SELECT * FROM " + DRINK_TABLE + " WHERE " + CATEGORY_COLUMN + " = '" + category
					+ "'";

			Cursor mCur = mDb.rawQuery(sql, null);

			if (mCur != null) {
				while (mCur.moveToNext()) {
					int id = mCur.getInt(mCur.getColumnIndex(ID_COLUMN));
					String drinkName = mCur.getString(mCur
							.getColumnIndex(DRINK_NAME_COLUMN));
					int price = mCur.getInt(mCur.getColumnIndex(PRICE_COLUMN));
					String desc = mCur.getString(mCur.getColumnIndex(DESC_COLUMN));
					ArrayList<Ingredient> ings = getDrinkIngredients(id);

					drinks.add(new Drink(id, drinkName, category, price, desc, ings));
				}
			}
			return drinks;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getMultipleDrinks >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	
	
	public Drink getSingleDrink(String drinkName){
		try{
			Drink d = new Drink();
			
			String sql = "SELECT * FROM " + DRINK_TABLE + " WHERE " + DRINK_NAME_COLUMN + " = '" + drinkName + "'";
			Cursor mCur = mDb.rawQuery(sql, null);
			
			if(mCur != null && mCur.moveToFirst()){
				int id = mCur.getInt(mCur.getColumnIndex(ID_COLUMN));
				String category = mCur.getString(mCur.getColumnIndex(CATEGORY_COLUMN));
				int price = mCur.getInt(mCur.getColumnIndex(PRICE_COLUMN));
				String desc = mCur.getString(mCur.getColumnIndex(DESC_COLUMN));
				ArrayList<Ingredient> ings = getDrinkIngredients(id);
				
				return new Drink(id, drinkName, category, price, desc, ings);
			}
			
			return d;
		}catch(SQLException mSQLException){
			Log.e(TAG, "getSingleDrink >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	

	private ArrayList<Ingredient> getDrinkIngredients(int drinkID) {
		try {
			ArrayList<Ingredient> ings = new ArrayList<Ingredient>();
			String sql = "SELECT " + INGREDIENT_ID_COLUMN + " FROM " + MIX_TABLE + " WHERE " + DRINK_ID_COLUMN + " = '"
					+ drinkID + "'";

			Cursor mCur = mDb.rawQuery(sql, null);

			if (mCur != null && mCur.moveToFirst()) {
				int id = mCur.getInt(mCur.getColumnIndex(INGREDIENT_ID_COLUMN));
				ings.add(findIngredientById(id));
				while (mCur.moveToNext()) {
					id = mCur.getInt(mCur.getColumnIndex(INGREDIENT_ID_COLUMN));
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
			String sql = "SELECT " + INGREDIENT_NAME_COLUMN + " FROM " + INGREDIENT_TABLE + " WHERE " + ID_COLUMN + " = '"
					+ ingID + "'";
			
			Cursor mCur = mDb.rawQuery(sql, null);
			
			if(mCur != null && mCur.moveToFirst()){
				String ingName = mCur.getString(mCur.getColumnIndex(INGREDIENT_NAME_COLUMN));
				return new Ingredient(ingID, ingName);
			}
			
			return null;

		} catch (SQLException mSQLException) {
			Log.e(TAG, "findIngredientById >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
}