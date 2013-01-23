package cse.bgu.finalandroidproject.theamazigrace;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ViewGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_game, menu);
		return true;
	}

}
