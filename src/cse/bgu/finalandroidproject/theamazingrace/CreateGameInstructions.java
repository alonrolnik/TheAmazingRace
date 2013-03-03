package cse.bgu.finalandroidproject.theamazingrace;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGameInstructions extends Activity {

	private EditText gameName;
	private EditText creator;
	private EditText area;
	private MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_instructions);
		
		gameName = (EditText) findViewById(R.id.et_game_name);
		creator = (EditText) findViewById(R.id.et_creator);
		area = (EditText) findViewById(R.id.et_area);
	}

	public void bContinueClicked(View v){
		if(		(gameName.getText().length() > 0) &&
				(area.getText().length() > 0) &&
				(creator.getText().length() > 0 ) 
				){
			if(!name_exist(gameName.getText().toString().replace(" ", "_"))){
			Intent intent = new Intent(this, CreateGame.class);
			intent.putExtra(Extras.Area,area.getText().toString());
			intent.putExtra(Extras.Creator,creator.getText().toString());
			intent.putExtra(Extras.GAME_NAME,gameName.getText().toString().replace(" ", "_"));
			startActivity(intent);
			}
			else
				Toast.makeText(this, "name allready exist, please choose another name", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
		}
	}
	
	private boolean name_exist(String replace) {
		// TODO Auto-generated method stub
		
		return db.is_name_exist(replace);
	}

	public void bBackClicked(View v){
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		ActionBar actionBar;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_game_instructions, menu);
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {
		// TODO Auto-generated method stub
		{
			switch (item.getItemId()) {

			case R.id.menu_exit_app:
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case DialogInterface.BUTTON_POSITIVE:
							//exitApp();
							Toast.makeText(getBaseContext(),"Need to exit app", Toast.LENGTH_LONG).show();
							break;
						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
				return true;
				/*	    		SharedPreferences sharedPref = getSharedPreferences(MY_SHRD_PREF, Context.MODE_PRIVATE);
        	SharedPreferences.Editor editor = sharedPref.edit();
        	editor.putBoolean(EXIT_KEY, true);
        	editor.commit();
			Intent ext_intent = new Intent(this, cse.bgu.ex5.jokelistbook.JokesList.class);
            ext_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ext_intent);
			finish();
    		return true;
				 */

			case R.id.menu_back_to_main_menu:
				Intent intent = new Intent (this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			default:
				return super.onMenuItemSelected(featureId, item);

			}
		}

	}

}
