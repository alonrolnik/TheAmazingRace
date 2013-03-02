package cse.bgu.finalandroidproject.theamazingrace;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

	MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//initTest();
	}
	
	public void goToPlay(View view){
		
		//startActivity(new Intent(this, PlayGame.class));
		Toast.makeText(this, "not available anymore", Toast.LENGTH_LONG).show();
	}

	public void goToCreate(View view){
		startActivity(new Intent(this, CreateGameInstructions.class));
	}
	
	public void goToGameList(View view){
		startActivity(new Intent(this, ListOfGames.class));
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		ActionBar actionBar;
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.my_menu, menu);
	    actionBar=getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    return true;
	}
	
	public void initTest(){
		List<Challenge> list = new ArrayList<Challenge>();
	    Challenge newChallenge[] = {new Challenge (new LatLng(31.25, 34.85), "Whats my name?", "Alon", new String[] {"Tal", "Oscar", "Gil"}),
				new Challenge (new LatLng(31.15, 34.75), "What is the color of Napolion's white horse?", "White", new String[] {"Black", "Red", "Blue"}), 
				new Challenge (new LatLng(31.2, 34.8), "How meny meters are in one nautical mile?", "1852", new String[] {"1609", "1734", "1586"}),
				new Challenge (new LatLng(31.3, 34.9), "Where are we?", "All the answers are correct", new String[] {"bilding 95", "lab 105", "B.G.U"})};
	    for (int i = 0; i < newChallenge.length; i++) {
			list.add(newChallenge[i]);
	    }
	    Game game = new Game("Alon", "the_mighty_race3", "Beer Sheva Israel", list);
	    db.addGame(game);
	}
	

}
