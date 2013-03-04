package cse.bgu.finalandroidproject.theamazingrace;

import java.util.Map;

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

				if(!db.is_name_exist(tv.getText().toString().replace(" ","_"))){
					Toast.makeText(getBaseContext(),"need to download from server not yet implemented", Toast.LENGTH_LONG).show();
				}
				else{
				Intent mIntent = new Intent(getBaseContext(), PlayGame.class);
				mIntent.putExtra(Extras.GAME_NAME, tv.getText().toString().replace(" ","_"));
				startActivity(mIntent);
				}
			}				
		}
				);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					final int position, long id) {
				// TODO Auto-generated method stub
				final TextView tv = (TextView) v.findViewById(R.id.text_game_name);
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case DialogInterface.BUTTON_POSITIVE:
							if(db.is_name_exist(tv.getText().toString().replace(" ","_"))){
							new Handler().post(new Runnable() {
								@Override
								public void run() {
									if(db.remove_game(tv.getText().toString().replace(" ","_")) != 1)
										Toast.makeText(getBaseContext(),"failed to delete not on local", Toast.LENGTH_SHORT).show();
										db.close();
									onResume();
								}
							});
							}
							else
								Toast.makeText(getBaseContext(),"game not on the local DB", Toast.LENGTH_SHORT).show();
							break;
						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(ListOfGames.this);
				builder.setMessage("You about to delete thie game, are you sure?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
			return true;
			}
		});
		// Database query can be a time consuming task ..
		// so its safe to call database query in another thread
		// Handler, will handle this stuff for you :)
//		new Handler().post (new Runnable() {
//			@Override
//			public void run() {
//				parse_list_of_games("aaa--alon--ar\nmoran seek--moran--beersheva\nThe mighty race--tal--beersheva");
//			}
//		});

	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CustomCursorAdapter customAdapter = new CustomCursorAdapter(ListOfGames.this, db.get_all_games());
		listView.setAdapter(customAdapter);

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
	
	private void parse_list_of_games(String to_parse){
		String[] result = to_parse.split("\\n");
		for (int i = 0; i < result.length; i++) {
			String params[] = result[i].split("--");
			db.insert_game_to_listOfGames(params[0], params[1], params[2]);
		}
		
	}


}
