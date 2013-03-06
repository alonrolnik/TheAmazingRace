package cse.bgu.finalandroidproject.theamazingrace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


@SuppressLint("HandlerLeak")
public class LocalService extends IntentService {

	public static final String REQUEST_STRING = "myRequest";
	public static final String RESPONSE_STRING = "myResponse";
	public static final String RESPONSE_MESSAGE = "myResponseMessage";
	public static final String RESPONSE_TYPE = "responseType";

	public int typeofreq;
	private String gamename;


	private Handler handler;
	public final String url = "http://1.amazingracegamenew.appspot.com/";

	public LocalService() {
		super("LocalService");
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				boolean success = msg.getData().getBoolean("success");
				if (success){
					String res = msg.getData().getString("data");
					int type = msg.getData().getInt("gametype",0);
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction(ListOfGames.WebReceiver.PROCESS_RESPONSE);
					broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
					broadcastIntent.putExtra(RESPONSE_STRING, success);
					broadcastIntent.putExtra(RESPONSE_TYPE, type);
					broadcastIntent.putExtra(RESPONSE_MESSAGE, res);
					sendBroadcast(broadcastIntent); 
				}
			}
		};
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		typeofreq=intent.getIntExtra("gameType",0);
		if (typeofreq == 2 ){
			gamename = intent.getStringExtra("gamename");
		}
		GetHttpResponse();
	}

	private void GetHttpResponse(){
		String SndUrl = "";
		if (typeofreq == 1 ){ 
			SndUrl=url+"getgames";
		}
		else if (typeofreq == 2 ){
			SndUrl=url+"play?gamename="+gamename;
		}
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpRequestBase httpRequest = new HttpGet(SndUrl);
		HttpResponse response;


		Message msg = handler.obtainMessage();
		Bundle bundle = new Bundle();

		try {
			response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String Values =convertStreamToString(is);

			bundle.putBoolean("success", true);
			bundle.putString("data", Values);
			bundle.putInt("gametype", typeofreq);
		} catch (ClientProtocolException e) {
			bundle.putBoolean("success", false);
		} catch (IOException e) {
			bundle.putBoolean("success", true);
		}

		msg.setData(bundle);
		handler.sendMessage(msg);
	}


	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}

