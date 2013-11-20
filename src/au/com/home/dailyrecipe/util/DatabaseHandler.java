package au.com.home.dailyrecipe.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import au.com.home.dailyrecipe.domain.Recipe;

public class DatabaseHandler extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "recipesDB";
	
	private static final String TABLE_RECIPES = "recipes";
	
	// Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" +")";
		db.execSQL(CREATE_RECIPES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
		
		onCreate(db);
	}
	
	public void addRecipe(Recipe recipe){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, recipe.getName());
		
		db.insert(TABLE_RECIPES, null, values);
		db.close();
	}
	
	public Recipe getRecipe(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_RECIPES, new String[]{KEY_ID, KEY_NAME}, KEY_ID+"=?", new String[]{ String.valueOf(id)}, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
	
		Recipe recipe = new Recipe();

		recipe.setId(Integer.parseInt(cursor.getString(0)));
		recipe.setName(cursor.getString(1));
		
		return recipe;
	}
	
	public List<Recipe> getAllRecipes(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM"+TABLE_RECIPES, null);
		
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		if(cursor.moveToFirst()){
			do{
				Recipe recipe = new Recipe();
				recipe.setId(cursor.getInt(0));
				recipe.setName(cursor.getString(1));
				
				recipes.add(recipe);
			}while(cursor.moveToNext());
		}
		
		return recipes;
	}

}
