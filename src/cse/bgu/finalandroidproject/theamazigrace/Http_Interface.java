package cse.bgu.finalandroidproject.theamazigrace;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

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

	// Uses AsyncTask to create a task away from the main UI thread. This task takes a 
	// URL string and uses it to create an HttpUrlConnection. Once the connection
	// has been established, the AsyncTask downloads the contents of the webpage as
	// an InputStream. Finally, the InputStream is converted into a string, which is
	// displayed in the UI by the AsyncTask's onPostExecute method.
	private class DownloadWebpageText extends AsyncTask {
		@Override
		protected String doInBackground(String... urls) {

			// params comes from the execute() call: params[0] is the url.
			try {
				return downloadUrl(urls[0]);
			} catch (IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			textView.setText(result);
		}
	}


	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	/**
	 * fire an http GET to requested url 
	 * @param url
	 * @return JSONObject
	 * @throws IOException
	 */
	private static JSONObject httpGet(String my_url) throws IOException {
		InputStream is = null;
		// Only display the first 500 characters of the retrieved content
		int len = 500;
		JSONObject myJSON = null;

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

			// Convert the InputStream into a string
			myJSON = is.
			String contentAsString = readIt(is, len);
			return contentAsString;

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (is != null) {
				is.close();
			} 
		}
	}

	//Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");        
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

}

