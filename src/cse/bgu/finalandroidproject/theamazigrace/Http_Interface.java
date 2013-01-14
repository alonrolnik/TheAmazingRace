package cse.bgu.finalandroidproject.theamazigrace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Http_Interface {

	//create functions for http get and post

	private static final URI url = null;

	private void getHttpResponse(){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpRequestBase httpRequest = new HttpGet(url);
		HttpResponse response;
		Bundle bundle = new Bundle();
		
		try {
			response = httpClient.execute(httpRequest);
			String res = resultToString(response);    		
			bundle.putBoolean("success", true);
			bundle.putString("result", res);
		} catch (ClientProtocolException e) {
			bundle.putBoolean("success", false);
		} catch (IOException e) {
			bundle.putBoolean("success", false);
		}
	}

	private String resultToString(HttpResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

