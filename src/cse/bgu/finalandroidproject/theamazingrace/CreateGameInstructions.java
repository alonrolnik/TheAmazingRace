package cse.bgu.finalandroidproject.theamazingrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGameInstructions extends Activity {

	private EditText gameName;
	private EditText creator;
	private EditText area;
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
			Intent intent = new Intent(this, CreateGame.class);
			intent.putExtra(Extras.Area,area.getText().toString());
			intent.putExtra(Extras.Creator,creator.getText().toString());
			intent.putExtra(Extras.GAME_NAME,gameName.getText().toString().replace(" ", "_"));
			startActivity(intent);
		}
		else {
			Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
		}
	}
	
	public void bBackClicked(View v){
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_game_instructions, menu);
		return true;
	}

}
