package cse.bgu.finalandroidproject.theamazigrace;

import org.xmlpull.v1.XmlPullParserException;

import android.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class AcbWithMenu extends SherlockActivity{
	
	final static String EXIT_KEY = "cse.bgu.finalandroidproject.theamazigrace.exit_app";
	final static String MY_SHRD_PREF = "cse.bgu.finalandroidproject.theamazigrace.mySherdPref";
	ProgressDialog dialog;
	Handler handler;
	DB_Intrface mydb;



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		ActionBar actionBar;
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.my_menu, menu);
	    actionBar=getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.addNewJoke:
	            Intent intent = new Intent(this, cse.bgu.ex5.jokelistbook.AddNewJoke.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        case R.id.importNewJoke:
	        	dialog = ProgressDialog.show(this, "Loading...", "Please wait");
	            importNewJoke();
	            return true;
	        case R.id.changeColor:
	        	return true;
	        case R.id.changeToRed:
	        	changeColor(getClass().getSimpleName(), Color.RED);
	        	return true;
	        case R.id.changeToBlue:
	        	changeColor(getClass().getSimpleName(), Color.BLUE);
	        	return true;
	        case R.id.changeToGreen:
	        	changeColor(getClass().getSimpleName(), Color.GREEN);
	        	return true;
	        case R.id.exitApp:
	    		SharedPreferences sharedPref = getSharedPreferences(MY_SHRD_PREF, Context.MODE_PRIVATE);
            	SharedPreferences.Editor editor = sharedPref.edit();
            	editor.putBoolean(EXIT_KEY, true);
            	editor.commit();
				Intent ext_intent = new Intent(this, cse.bgu.ex5.jokelistbook.JokesList.class);
	            ext_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(ext_intent);
				finish();
	    		return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void importNewJoke() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			public void run() {
				try {
					HttpComm.getHttpResponse(handler, mydb);
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		

	}

	private void changeColor(String activityName, int color){
		if(activityName.equals(AddNewJoke.class.getSimpleName().toString()))
			findViewById(R.id.RelativeLayout2).setBackgroundColor(color);
		else if(activityName.equals(JokesList.class.getSimpleName().toString())){
			findViewById(R.id.LinearLayout1).setBackgroundColor(color);
		}
		else findViewById(R.id.RelativeLayout1).setBackgroundColor(color);
	}
}
