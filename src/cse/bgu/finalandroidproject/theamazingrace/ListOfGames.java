package cse.bgu.finalandroidproject.theamazingrace;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
	private WebReceiver receiver;
	private ListView listView;
	private String choosengame;
	private String choosencreator;
	private String choosenarea;
	private int playgameempty;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_game);
		listView = (ListView) findViewById(R.id.list_data);

		IntentFilter filter = new IntentFilter(WebReceiver.PROCESS_RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new WebReceiver();
		registerReceiver(receiver, filter);

		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, 
					int position, long id) {
				TextView tv_n = (TextView) v.findViewById(R.id.text_game_name);
				TextView tv_c = (TextView) v.findViewById(R.id.text_creator);
				TextView tv_a = (TextView) v.findViewById(R.id.text_area);

				if(!db.is_name_exist(tv_n.getText().toString().replace(" ", "_"))){
					Toast.makeText(getBaseContext(),"Downloading game from server", Toast.LENGTH_LONG).show();
					Intent svc = new Intent(ListOfGames.this, LocalService.class); 
					svc.putExtra("gameType", 2);
					svc.putExtra("gamename", tv_n.getText().toString().replace(" ", "%20"));
					choosengame = tv_n.getText().toString();
					choosencreator = tv_c.getText().toString();
					choosenarea = tv_a.getText().toString();
					startService(svc);
				}
				else{
					Intent mIntent = new Intent(getBaseContext(), PlayGame.class);
					mIntent.putExtra(Extras.GAME_NAME, tv_n.getText().toString().replace(" ","_"));
					startActivity(mIntent);
					finish();
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
								new Handler().post(new Runnable() {
									@Override
									public void run() {
										if(db.remove_game(tv.getText().toString()) != 1)
											Toast.makeText(getBaseContext(),"failed to delete not on local", Toast.LENGTH_SHORT).show();
										db.close();
										onResume();
									}
								});
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
				finish();
				return super.onMenuItemSelected(featureId, item);

			case R.id.update_list:
				Intent svc = new Intent(ListOfGames.this, LocalService.class); 
				svc.putExtra("gameType", 1);
				startService(svc); 
				return super.onMenuItemSelected(featureId, item);

			default:
				return super.onMenuItemSelected(featureId, item);

			}
		}

	}

	public class WebReceiver extends BroadcastReceiver{
		public static final String PROCESS_RESPONSE = "amazingrace1";
		@Override        
		public void onReceive(Context context, Intent intent) {
			int type = intent.getIntExtra("responseType", 0);
			String  Data = intent.getStringExtra(LocalService.RESPONSE_MESSAGE).replace("%20", " ");
			if (type==  1){	// get games list
				parse_list_of_games(Data);
				CustomCursorAdapter customAdapter = new CustomCursorAdapter(ListOfGames.this, db.get_all_games());
				listView.setAdapter(customAdapter);
			}
			if (type==2){	// get games list
				parse_play(Data);
				if (playgameempty == 0){
					Intent mIntent = new Intent(getBaseContext(), PlayGame.class);
					mIntent.putExtra(Extras.GAME_NAME, choosengame.replace(" ", "_"));
					startActivity(mIntent);
					finish();
				}
				else {
					Intent svc = new Intent(ListOfGames.this, LocalService.class); 
					svc.putExtra("gameType", 1);
					startService(svc); 
				}
			}
		} 
	}

	private void parse_list_of_games(String to_parse){
		String[] result = to_parse.split("\\n");
		db.flush_listOfGames();
		if(result[0].equals("No Games")){
			Toast.makeText(getBaseContext(), "No Games", Toast.LENGTH_SHORT).show();
		}
		else{
			for (int i = 0; i < result.length; i++) {
				String params[] = result[i].split("--");
				db.insert_game_to_listOfGames(params[0], params[2], params[1]);	
			}
		}
	}


	private void parse_play(String to_parse){
		playgameempty = 0;
		List<Challenge> challengesList = new ArrayList<Challenge>();
		Challenge c_cha = new Challenge();
		String[] result = to_parse.split("\\n");
		if(result[0].equals("No Points")){
			playgameempty = 1;
			Toast.makeText(getBaseContext(), "No challeges, game is deleted", Toast.LENGTH_SHORT).show();
		}
		else{
			for (int i = 0; i < result.length; i++) {
				String params[] = result[i].split("--");
				String wrongs[] ={params[4],params[5],params[6]};	
				c_cha.setCheckpoint(Double.valueOf(params[0]),Double.valueOf(params[1]));
				c_cha.setChallenge(params[2]);
				c_cha.setRight_answer(params[3]);
				c_cha.setWrong_answers(wrongs);
				challengesList.add(c_cha);
			}
			db.addGame(new Game(choosencreator ,choosengame ,choosenarea ,challengesList),false);
		}
	}
}


