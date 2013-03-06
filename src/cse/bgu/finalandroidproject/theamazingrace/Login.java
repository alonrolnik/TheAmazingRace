package cse.bgu.finalandroidproject.theamazingrace;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Login extends Activity {
	
	final int MINIMUM_CHAR = 1;
	final int MaxUserNameLength = 30;
	public final static String FILE_NAME = "userloginname.txt";
	public final String url = "http://1.amazingracegamenew.appspot.com/";
	private String userFile;
	ProgressDialog pdialog;
	public Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userFile=parse(readFile());
		if(userFile!=null){
			((EditText) findViewById(R.id.loginname)).setText(userFile);
		}
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				pdialog.cancel();
				boolean success = msg.getData().getBoolean("success");
				if (success){
					MainActivity.returning = msg.getData().getBoolean("Exist");
					Intent addintent = new Intent(Login.this, MainActivity.class);
					startActivity(addintent);
					finish();
				}
				else{
					MainActivity.failedtolog = MainActivity.Exit;
				}
			}
		};
	}



	public void login(View view) {
		MainActivity.userNew = ((EditText) findViewById(R.id.loginname)).getText().toString();
		if ((MainActivity.userNew.length()<MINIMUM_CHAR)||((MainActivity.userNew.length()>MaxUserNameLength)))
			Toast.makeText(this, "Name is too short or too long", Toast.LENGTH_LONG).show(); 
		else{
			checkUser();
			File file = new File(MainActivity.FILE_NAME);
			file.delete();
			fileCreate();
		}
	}

	private String readFile() {
		try {
			FileInputStream fin = openFileInput(FILE_NAME);
			InputStreamReader isReader = new InputStreamReader(fin);
			char[] buffer = new char[MaxUserNameLength];
			// Fill the buffer with data from file
			isReader.read(buffer);
			return new String (buffer);
		} catch (Exception e) {
			Log.i("ReadNWrite, readFile()", "Exception e = " + e);
			return null;
		}
	}

	public String parse(String user){
		if (user!=null){
			char [] bla =user.toCharArray();
			String Parsed="";
			int i = 0;
			while (bla[i]!=0){
				Parsed+=bla[i];
				i++;
			}
			return Parsed;
		}
		else{
			return user;
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("WorldReadableFiles")
	private void fileCreate() {
		try {
			String userNew = ((EditText) findViewById(R.id.loginname)).getText().toString();
			OutputStream os = openFileOutput(FILE_NAME, MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(userNew);
			osw.close();
		} catch (Exception e) {
			Log.i("ReadNWrite, fileCreate()", "Exception e = " + e);
		}
	}

	private void checkUser(){
		// Check Remote
		pdialog = ProgressDialog.show(Login.this, "Connecting Server...", "Please wait");
		new Thread(new Runnable() {
			public void run() {
				GetHttpResponse();
			}
		}).start();
	}

	private void GetHttpResponse(){
		org.apache.http.Header[] headers;
		String SndUrl =(url+"wel?user="+MainActivity.userNew).replace(" ", "%20");
		boolean newuser = false;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpRequestBase httpRequest = new HttpGet(SndUrl);
		HttpResponse response;

		Message msg = handler.obtainMessage();
		Bundle bundle = new Bundle();

		try {
			response = httpClient.execute(httpRequest);
			headers=response.getAllHeaders();
			newuser=checknew(headers);
			bundle.putBoolean("success", true);
			bundle.putBoolean("Exist", newuser);
		} catch (ClientProtocolException e) {
			bundle.putBoolean("success", false);
		} catch (IOException e) {
			bundle.putBoolean("success", false);
		}
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	private boolean checknew (org.apache.http.Header[] headers){
		boolean Result = false;
		for (int i=0; i<headers.length;i++){
			if(headers[i].getName().equals("Exist")){ 
				if (Integer.valueOf(headers[i].getValue().toString()).equals("1")){
					Result = true;
				}
			}
		}
		return Result;
	}
}
