package cse.bgu.finalandroidproject.theamazigrace;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/***
 * 
 * @author alon
 * 	userMenu contains 4 buttons
 * First button connect us to the "User details" activity
 * Second button connect us to the "Play an existing game" activity
 * Third button connect us to the "Edit an existing game" activity
 * Forth button connect us to the "Create a new game" activity
 */
public class UserMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_user_menu, menu);
		return true;
	}

}
