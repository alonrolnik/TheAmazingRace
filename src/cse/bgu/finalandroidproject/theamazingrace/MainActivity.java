package cse.bgu.finalandroidproject.theamazingrace;

import java.util.ArrayList;
import java.util.List;

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

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

	public final static String FILE_NAME = "userloginname.txt";
	public static String userNew;
	public static boolean returning;
	public static int failedtolog = 0;
	public final static int Exit = 99;

	MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		//initTest();
	}

	public void goToCreate(View view){
		startActivity(new Intent(this, CreateGameInstructions.class));
	}

	public void goToGameList(View view){
		startActivity(new Intent(this, ListOfGames.class));
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
		db.addGame(game,true);
	}


	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		ActionBar actionBar;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
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
							finish();
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
			case R.id.About:
				startActivity(new Intent(this, AboutActivity.class));
			default:
				return super.onMenuItemSelected(featureId, item);

			}
		}

	}


}
