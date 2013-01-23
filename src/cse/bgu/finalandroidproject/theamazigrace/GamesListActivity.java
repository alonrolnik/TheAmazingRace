package cse.bgu.finalandroidproject.theamazigrace;

import java.util.ArrayList;

import android.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamesListActivity extends AcbWithMenu {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_list);
		
		
		mydb = new DB_Intrface(this);
	 	mydb.open();
		jokeList = (ArrayList<Joke>) mydb.getAllJokes();
		mydb.close();
		
		ArrayAdapter<Joke> adapter = new ArrayAdapter<Joke>(this,
									 android.R.layout.simple_list_item_1,
									 jokeList);
		listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, 
                int position, long id) {   
				Intent intent = new Intent (parent.getContext(), cse.bgu.ex5.jokelistbook.ViewJoke.class);
				intent.putExtra(KEY, jokeList.get(position).getId());
	        //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    		startActivity(intent);
	        }
		}
		);
	    listView.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_games_list, menu);
		return true;
	}

}
