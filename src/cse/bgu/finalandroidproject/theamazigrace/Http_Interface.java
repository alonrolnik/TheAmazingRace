package cse.bgu.finalandroidproject.theamazigrace;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class contains the necessary functions to
 * construct an http connection. 
 * @author alon
 */
public class Http_Interface{

    private static final String DEBUG_TAG = "HttpExample";

	//create functions for http get and post

	// When user clicks button, calls AsyncTask.
	// Before attempting to fetch the URL, makes sure that there is a network connection.
	// Gets the URL from the UI's text field.

	/**
	 * Checks for available connectivity return true if available else return false
	 * @param context :: Context()
	 * @return
	 */
	public static boolean is_available(Context context){
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * fire an http GET to requested url. returns JSONArray 
	 * @param url
	 * @return JSONArray
	 * @throws IOException
	 */
	public static JSONArray httpGet(String my_url) throws IOException {
		InputStream is = null;
		// Only display the first 500 characters of the retrieved content
		int len = 500;
		JSONArray myJSON = null;

		try {
			URL url = new URL(my_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.d(DEBUG_TAG, "The response is: " + response);
			is = conn.getInputStream();
			
			String json = readIt(is, len);
			try {
				myJSON = new JSONArray(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return myJSON;

			// Makes sure that the InputStream is closed after
			// finished using it.
		} finally {
			if (is != null) {
				is.close();
			} 
		}
	}
	
	/**
	 * fire an http POST with JSON to requested url
	 * @param my_url
	 * @param json
	 * @throws IOException
	 */
	public static void httpPost(String my_url, JSONObject json) throws IOException {
		OutputStream os = null;
//		InputStream is = null;
		String json_s = json.toString();
		
		try {
			URL url = new URL(my_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setFixedLengthStreamingMode(json_s.getBytes().length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);

			// Starts the query
			conn.connect();
			
			//setup send
			os = new BufferedOutputStream(conn.getOutputStream());
			os.write(json_s.getBytes());
			//clean up
			os.flush();

			//do something with response
		//	is = conn.getInputStream();
		//	String responseString = readIt(is,len);
			int response = conn.getResponseCode();
			Log.d(DEBUG_TAG, "The response is: " + response);
			
			// Makes sure that the InputStream is closed after
			// finished using it.
		} finally {
			if (os != null) {
				os.close();
		//		is.close();
			} 
		}
	}
	
	/**
	 * Reads an InputStream and converts it to a String.
	 * @param stream
	 * @param len
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");        
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

}

