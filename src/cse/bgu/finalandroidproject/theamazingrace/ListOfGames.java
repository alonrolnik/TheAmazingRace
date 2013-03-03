package cse.bgu.finalandroidproject.theamazingrace;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



@SuppressLint("NewApi")
public class ListOfGames extends Activity
{

	MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);
	private CustomCursorAdapter customAdapter;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_game);
		listView = (ListView) findViewById(R.id.list_data);

		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, 
					int position, long id) {
				TextView tv = (TextView) v.findViewById(R.id.text_game_name);
				Intent mIntent = new Intent(getBaseContext(), PlayGame.class);
				mIntent.putExtra(Extras.GAME_NAME, tv.getText().toString().replace(" ","_"));
				startActivity(mIntent);
			}				
		}
				);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) v.findViewById(R.id.text_game_name);
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case DialogInterface.BUTTON_POSITIVE:
							db.remove_game(parent().getText().toString().replace(" ","_"));
							break;
						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
				builder.setMessage("You about to delete thie game, are you sure?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
			return true;
			}
		});
		// Database query can be a time consuming task ..
		// so its safe to call database query in another thread
		// Handler, will handle this stuff for you :)

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				customAdapter = new CustomCursorAdapter(ListOfGames.this, db.get_all_games());
				listView.setAdapter(customAdapter);
				db.close();
			}
		});
	}



	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		ActionBar actionBar;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_list_of_games, menu);
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
