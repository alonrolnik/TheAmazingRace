package cse.bgu.finalandroidproject.theamazingrace;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;



@SuppressLint("NewApi")
public class ListOfGames extends Activity
{

	MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);
	private CustomCursorAdapter customAdapter;
	private static final int ENTER_DATA_REQUEST_CODE = 1;
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
				mIntent.putExtra(Extras.GAME_NAME, tv.getText().toString());
				startActivity(mIntent);
			}				
		}
				);

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
	
/*	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		TextView tv = (TextView) v.findViewById(R.id.text_game_name);
		Intent mIntent = new Intent(this, ListOfGames.class);
		mIntent.putExtra(Extras.GAME_NAME, tv.getText().toString());
	}
*/

}
