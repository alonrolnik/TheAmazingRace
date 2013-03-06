package cse.bgu.finalandroidproject.theamazingrace;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGameInstructions extends Activity {

	private EditText gameName;
	private EditText creator;
	private EditText area;
	private String fixedname;
	private String c_area;
	private String c_creator;

	ProgressDialog ag_pdialog;
	public Handler ag_handler;

	public final String url = "http://1.amazingracegamenew.appspot.com/";

	private MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_instructions);

		gameName = (EditText) findViewById(R.id.et_game_name);
		creator = (EditText) findViewById(R.id.et_creator);
		area = (EditText) findViewById(R.id.et_area);

		ag_handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				ag_pdialog.cancel();
				boolean success = msg.getData().getBoolean("success");
				if (success){
					continueWithcreation();
				}
			}
		};

	}

	public void bContinueClicked(View v){
		if(		(gameName.getText().length() > 0) &&
				(area.getText().length() > 0) &&
				(creator.getText().length() > 0 ) 
				){
			fixedname = fixname(gameName.getText().toString());
			c_creator = fixname(creator.getText().toString());
			c_area= fixname(area.getText().toString());
			ag_pdialog = ProgressDialog.show(CreateGameInstructions.this, "checking if game name exist...", "Please wait");
			new Thread(new Runnable() {
				public void run() {
					ag_GetHttpResponse();
				}
			}).start();
		}
		else {
			Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
			}
	}
	
	private String fixname(String  name) {
		int i=name.length();
		String Tempfix =null;
		
		while (i>0){
			if (name.charAt(i-1)== ' '){
				i--;
			}
			else{
				Tempfix=name.substring(0, i);
				break;
			}
			Tempfix=name;
		}
		i=0;
		while (i<Tempfix.length()){
			if (name.charAt(i)== ' '){
				i++;
			}
			else{
				fixedname=Tempfix.substring(i);
				break;
			}
			fixedname=name;
		}
		return fixedname;
		
	}

	private void ag_GetHttpResponse(){
		org.apache.http.Header[] headers;
		String SndUrl =(url+"addquest?creator="+c_creator+"&gamename="+fixedname+"&area="+c_area).replace(" ", "%20");
		boolean newgame = false;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpRequestBase httpRequest = new HttpGet(SndUrl);
		HttpResponse response;

		Message msg = ag_handler.obtainMessage();
		Bundle bundle = new Bundle();

		try {
			response = httpClient.execute(httpRequest);
			headers=response.getAllHeaders();
			newgame=checknew(headers);
			bundle.putBoolean("success", true);
			bundle.putBoolean("Exist", newgame);
		} catch (ClientProtocolException e) {
			bundle.putBoolean("success", false);
		} catch (IOException e) {
			bundle.putBoolean("success", false);
		}
		msg.setData(bundle);
		ag_handler.sendMessage(msg);
	}

	private boolean checknew (org.apache.http.Header[] headers){
		boolean Result = false;
		for (int i=0; i<headers.length;i++){
			if(headers[i].getName().equals("Exist")){ 
				if (Integer.valueOf(headers[i].getValue().toString()) == 1){
					Result = true;
				}
			}
			if(headers[i].getName().equals("index")){
				CreateGame.currentGameindex = Integer.valueOf(headers[i].getValue().toString());			
			}

		}
		return Result;
	}

	public void continueWithcreation(){
		if(!name_exist(fixedname.replace(" ", "_"))){
			Intent intent = new Intent(this, CreateGame.class);
			intent.putExtra(Extras.Area,area.getText().toString());
			intent.putExtra(Extras.Creator,creator.getText().toString());
			intent.putExtra(Extras.GAME_NAME,fixedname);
			startActivity(intent);
			finish();
		}
		else
			Toast.makeText(this, "name allready exist, please choose another name", Toast.LENGTH_LONG).show();
	}

	private boolean name_exist(String replace) {
		// TODO Auto-generated method stub
		return db.is_name_exist(replace);
	}

	public void bBackClicked(View v){
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}



	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		ActionBar actionBar;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_game_instructions, menu);
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
				/*	    		SharedPreferences sharedPref = getSharedPreferences(MY_SHRD_PREF, Context.MODE_PRIVATE);
        	SharedPreferences.Editor editor = sharedPref.edit();
        	editor.putBoolean(EXIT_KEY, true);
        	editor.commit();
			Intent ext_intent = new Intent(this, cse.bgu.ex5.jokelistbook.JokesList.class);
            ext_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ext_intent);
			finish();
    		return true;
				 */

			case R.id.menu_back_to_main_menu:
				Intent intent = new Intent (this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			default:
				return super.onMenuItemSelected(featureId, item);

			}
		}

	}

}
