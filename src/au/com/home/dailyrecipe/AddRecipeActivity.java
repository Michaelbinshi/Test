package au.com.home.dailyrecipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import au.com.home.dailyrecipe.domain.Recipe;
import au.com.home.dailyrecipe.util.DatabaseHandler;

public class AddRecipeActivity extends Activity {

	private static final String TAG = "AddRecipeActivity";
	
	private DatabaseHandler dbHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHandler = new DatabaseHandler(getApplicationContext());
		
		
		Button btn  = (Button)findViewById(R.id.confirmBtn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//save to sqlite
				EditText name = (EditText)findViewById(R.id.editName);
				
				Recipe recipe = new Recipe();
				recipe.setName(name.getText().toString());
				
				dbHandler.addRecipe(recipe);
				
				//back to fullscreen
				Intent intent = new Intent(getApplicationContext(), FullscreenActivity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
