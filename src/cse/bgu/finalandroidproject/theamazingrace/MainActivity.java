package cse.bgu.finalandroidproject.theamazingrace;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Challenge challenge1 = new Challenge(new LatLng(30, 34), new LatLng(31, 34), "hello whats my name",
				"Alon",
				new String[] {"Tal", "Oscar", "Gil"}
				);
		MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);
		// inserting
		Log.d("insert: ", "Inserting ...");
		long id = db.addChallenge(challenge1);
		
		// reading
		Log.d("reading: ", "Reading all challenges ...");
		List<Challenge> list = db.getEntireGame();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	

}
