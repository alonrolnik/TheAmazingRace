package cse.bgu.finalandroidproject.theamazigrace;

import com.google.android.gms.maps.GoogleMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class StartPlayActivity extends Activity {
    private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_play);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		super.setTitle(title);
	}

	/**
	 *After clicking on "check it" and he arrived to the right place,
	 *an android dialog pop up with the relevant question and 4 possible answers.
	 *after clicking the right answer update his score,
	 *remove dialog,
	 *fetch the next checkpoint,
	 *put it on the map and continue.
	 * @param view
	 */
	public void check_it(View view){

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_start_play, menu);
		return true;
	}

}
