package cse.bgu.finalandroidproject.theamazingrace;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class CreateGame extends android.support.v4.app.FragmentActivity 
implements	 OnMapClickListener, OnMapLongClickListener, OnCameraChangeListener{

	/**
	 * Note that this may be null if the Google Play services APK is not available.
	 */
	private GoogleMap mMap;
	private TextView mTapTextView;
	private TextView mCameraTextView;
	private Button getMyLocation;
	private Button enterAddress;
	// UI handler codes.
	private TextView mLatLng;
	private TextView mAddress;
	private Button mFineProviderButton;
	private Button mBothProviderButton;
	private LocationManager mLocationManager;
	private Handler mHandler;
	private boolean mGeocoderAvailable;
	private boolean mUseFine;
	private boolean mUseBoth;

	// Keys for maintaining UI states after rotation.
	private static final String KEY_FINE = "use_fine";
	private static final String KEY_BOTH = "use_both";
	// UI handler codes.
	private static final int UPDATE_ADDRESS = 1;
	private static final int UPDATE_LATLNG = 2;
	private static final int UPDATE_SHORT = 3;
	private static final int UPDATE_LONG = 4;

	private static final int TEN_SECONDS = 10000;
	private static final int TEN_METERS = 10;
	private static final int TWO_MINUTES = 1000 * 60 * 2;


	// challenge variables
	private LatLng currntPoint;
	private String newAddress;

	MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		mTapTextView = (TextView) findViewById(R.id.tap_text);
		mCameraTextView = (TextView) findViewById(R.id.camera_text);
		getMyLocation = (Button) findViewById(R.id.get_my_loc);
		setUpMapIfNeeded();


		// Restore apps state (if exists) after rotation.
		if (savedInstanceState != null) {
			mUseFine = savedInstanceState.getBoolean(KEY_FINE);
			mUseBoth = savedInstanceState.getBoolean(KEY_BOTH);
		} else {
			mUseFine = false;
			mUseBoth = false;
		}
		mLatLng = (TextView) findViewById(R.id.latlng);
		mAddress = (TextView) findViewById(R.id.address);
		// Receive location updates from the fine location provider (GPS) only.
		mFineProviderButton = (Button) findViewById(R.id.provider_fine);
		// Receive location updates from both the fine (GPS) and coarse (network) location
		// providers.
		mBothProviderButton = (Button) findViewById(R.id.provider_both);

		// The isPresent() helper method is only available on Gingerbread or above.
		mGeocoderAvailable =
				Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && Geocoder.isPresent();

				// Handler for updating text fields on the UI like the lat/long and address.
				mHandler = new Handler() {
					public void handleMessage(Message msg) {
						switch (msg.what) {
						case UPDATE_ADDRESS:
							mAddress.setText((String) msg.obj);
							break;
						case UPDATE_LATLNG:
							mLatLng.setText((String) msg.obj);
							break;
						case UPDATE_SHORT:
							newAddress = (String)msg.obj;
							mTapTextView.setText("tapped, point=" + newAddress);
							break;
						case UPDATE_LONG:
							newAddress = (String)msg.obj;
							mTapTextView.setText("tapped, point=" + newAddress);
							createChallenge(currntPoint);
							break;
						}
					}
				};
				// Get a reference to the LocationManager object.
				mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				/*	        
	        // loading the first challenge
	        //////////////////////////////////////////////////////////////////
	        myList.add(newChallenge[0]);
	        myList.add(newChallenge[1]);
	        myList.add(newChallenge[2]);
	        myList.add(newChallenge[3]);

	        //Challenge curChallenge = myList.iterator().next();
	        if (myIterator==null) 
				myIterator = myList.iterator();

	        suffleAnswers(myIterator.next());

	        /////////////////////////////////////////////////////////////////
				 */			
				
				getMyLocation.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Location myLocation = new Location("myLoc");        
						Location gpsLocation = requestUpdatesFromProvider(
								LocationManager.GPS_PROVIDER, R.string.not_support_gps);
						Location networkLocation = requestUpdatesFromProvider(
								LocationManager.NETWORK_PROVIDER, R.string.not_support_network);

						// If both providers return last known locations, compare the two and use the better
						// one to update the UI.  If only one provider returns a location, use it.
						if (gpsLocation != null && networkLocation != null) {
							myLocation=getBetterLocation(gpsLocation, networkLocation);
						} else if (gpsLocation != null) {
							myLocation=gpsLocation;
						} else if (networkLocation != null) {
							myLocation=networkLocation;
						} else{
							myLocation.setLatitude(0.0);
							myLocation.setLongitude(0.0);
						}
						if(myLocation==null){
							Log.d("myLocationis:", "null");
							
						}
						mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 16));
						mMap.addMarker(new MarkerOptions().position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).title("You are here!"));
					}
					

				});
	}

	// Restores UI states after rotation.
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_FINE, mUseFine);
		outState.putBoolean(KEY_BOTH, mUseBoth);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Check if the GPS setting is currently enabled on the device.
		// This verification should be done during onStart() because the system calls this method
		// when the user returns to the activity, which ensures the desired location provider is
		// enabled each time the activity resumes from the stopped state.
		LocationManager locationManager =
				(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!gpsEnabled) {
			// Build an alert dialog here that requests that the user enable
			// the location services, then when the user clicks the "OK" button,
			// call enableLocationSettings()
			new EnableGpsDialogFragment().show(getSupportFragmentManager(), "enableGpsDialog");
		}
	}

	// Method to launch Settings
	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(settingsIntent);
	}

	// Stop receiving location updates whenever the Activity becomes invisible.
	@Override
	protected void onStop() {
		super.onStop();
		mLocationManager.removeUpdates(listener);
	}

	// Set up fine and/or coarse location providers depending on whether the fine provider or
	// both providers button is pressed.
	private void setup() {
		Location gpsLocation = null;
		Location networkLocation = null;
		mLocationManager.removeUpdates(listener);
		mLatLng.setText(R.string.unknown);
		mAddress.setText(R.string.unknown);
		// Get fine location updates only.
		if (mUseFine) {
			mFineProviderButton.setBackgroundResource(R.drawable.button_active);
			mBothProviderButton.setBackgroundResource(R.drawable.button_inactive);
			// Request updates from just the fine (gps) provider.
			gpsLocation = requestUpdatesFromProvider(
					LocationManager.GPS_PROVIDER, R.string.not_support_gps);
			// Update the UI immediately if a location is obtained.
			if (gpsLocation != null) updateUILocation(gpsLocation);
		} else if (mUseBoth) {
			// Get coarse and fine location updates.
			mFineProviderButton.setBackgroundResource(R.drawable.button_inactive);
			mBothProviderButton.setBackgroundResource(R.drawable.button_active);
			// Request updates from both fine (gps) and coarse (network) providers.
			gpsLocation = requestUpdatesFromProvider(
					LocationManager.GPS_PROVIDER, R.string.not_support_gps);
			networkLocation = requestUpdatesFromProvider(
					LocationManager.NETWORK_PROVIDER, R.string.not_support_network);

			// If both providers return last known locations, compare the two and use the better
			// one to update the UI.  If only one provider returns a location, use it.
			if (gpsLocation != null && networkLocation != null) {
				updateUILocation(getBetterLocation(gpsLocation, networkLocation));
			} else if (gpsLocation != null) {
				updateUILocation(gpsLocation);
			} else if (networkLocation != null) {
				updateUILocation(networkLocation);
			}
		}
	}

	/**
	 * Method to register location updates with a desired location provider.  If the requested
	 * provider is not available on the device, the app displays a Toast with a message referenced
	 * by a resource id.
	 *
	 * @param provider Name of the requested provider.
	 * @param errorResId Resource id for the string message to be displayed if the provider does
	 *                   not exist on the device.
	 * @return A previously returned {@link android.location.Location} from the requested provider,
	 *         if exists.
	 */
	private Location requestUpdatesFromProvider(final String provider, final int errorResId) {
		Location location = null;
		if (mLocationManager.isProviderEnabled(provider)) {
			mLocationManager.requestLocationUpdates(provider, TEN_SECONDS, TEN_METERS, listener);
			location = mLocationManager.getLastKnownLocation(provider);
		} else {
			Toast.makeText(this, errorResId, Toast.LENGTH_LONG).show();
		}
		return location;
	}

	// Callback method for the "fine provider" button.
	public void useFineProvider(View v) {
		mUseFine = true;
		mUseBoth = false;
		setup();
	}

	// Callback method for the "both providers" button.
	public void useCoarseFineProviders(View v) {
		mUseFine = false;
		mUseBoth = true;
		setup();
	}

	private void doReverseGeocoding(Location location) {
		// Since the geocoding API is synchronous and may take a while.  You don't want to lock
		// up the UI thread.  Invoking reverse geocoding in an AsyncTask.
		(new ReverseGeocodingTask(this)).execute(new Location[] {location});
	}

	private void updateUILocation(Location location) {
		// We're sending the update to a handler which then updates the UI with the new
		// location.
		Message.obtain(mHandler,
				UPDATE_LATLNG,
				location.getLatitude() + ", " + location.getLongitude()).sendToTarget();

		// Bypass reverse-geocoding only if the Geocoder service is available on the device.
		if (mGeocoderAvailable) doReverseGeocoding(location);
	}

	private final LocationListener listener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// A new location update is received.  Do something useful with it.  Update the UI with
			// the location update.
			updateUILocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	/** Determines whether one Location reading is better than the current Location fix.
	 * Code taken from
	 * http://developer.android.com/guide/topics/location/obtaining-user-location.html
	 *
	 * @param newLocation  The new Location that you want to evaluate
	 * @param currentBestLocation  The current Location fix, to which you want to compare the new
	 *        one
	 * @return The better Location object based on recency and accuracy.
	 */
	protected Location getBetterLocation(Location newLocation, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return newLocation;
		}
		return currentBestLocation;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	// AsyncTask encapsulating the reverse-geocoding API.  Since the geocoder API is blocked,
	// we do not want to invoke it from the UI thread.
	private class ReverseGeocodingTask extends AsyncTask<Location, Void, Void> {
		Context mContext;

		public ReverseGeocodingTask(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected Void doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

			Location loc = params[0];
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			} catch (IOException e) {
				e.printStackTrace();
				// Update address field with the exception.
				Message.obtain(mHandler, UPDATE_ADDRESS, e.toString()).sendToTarget();
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				// Format the first line of address (if available), city, and country name.
				String addressText = String.format("%s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
								address.getLocality(),
								address.getCountryName());
				// Update address field on UI.
				Message.obtain(mHandler, UPDATE_ADDRESS, addressText).sendToTarget();
			}
			return null;
		}
	}

	/**
	 * Dialog to prompt users to enable GPS on the device.
	 */
	private class EnableGpsDialogFragment extends DialogFragment
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
			.setTitle(R.string.enable_gps)
			.setMessage(R.string.enable_gps_dialog)
			.setPositiveButton(R.string.enable_gps, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					enableLocationSettings();
				}
			})
			.create();
		}
	}
	/**
	 * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
	 * installed) and the map has not already been instantiated.. This will ensure that we only ever
	 * call {@link #setUpMap()} once when {@link #mMap} is not null.
	 * <p>
	 * If it isn't installed {@link SupportMapFragment} (and
	 * {@link com.google.android.gms.maps.MapView
	 * MapView}) will show a prompt for the user to install/update the Google Play services APK on
	 * their device.

	    private static final String DEBUG_TAG = "HttpExample";

		//create functions for http get and post

		// When user clicks button, calls AsyncTask.
		// Before attempting to fetch the URL, makes sure that there is a network connection.
		// Gets the URL from the UI's text field.

		/**
	 * Checks for available connectivity return true if available else return false
	 * @param context :: Context()
	 * @return
	 * <p>
	 * A user can return to this Activity after following the prompt and correctly
	 * installing/updating/enabling the Google Play services. Since the Activity may not have been
	 * completely destroyed during this process (it is likely that it would only be stopped or
	 * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
	 * {@link #onResume()} to guarantee that it will be called.
	 */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the camera. In this case, we
	 * just add a marker near Africa.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap} is not null.
	 */
	private void setUpMap() {
		//    mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
		mMap.setOnMapClickListener(this);
		mMap.setOnMapLongClickListener(this);
		mMap.setOnCameraChangeListener(this);
	}



	/**
	 * Get the player location
	 */
	public void checkMyLocation(View view){    	
	}


	@Override
	public void onMapClick(LatLng point) {
		if (mGeocoderAvailable) {
			Location tempLoc = new Location("tempLoc");
			tempLoc.setLatitude(point.latitude);
			tempLoc.setLongitude(point.longitude);
			doReverseGeocoding1(tempLoc, UPDATE_SHORT);
		}	    
	}

	@Override
	public void onMapLongClick(LatLng point) {
		if (mGeocoderAvailable) {
			Location tempLoc = new Location("tempLoc");
			tempLoc.setLatitude(point.latitude);
			tempLoc.setLongitude(point.longitude);
			doReverseGeocoding1(tempLoc, UPDATE_LONG);
		}	
		currntPoint = point;// added marker on tapped point
	}
	
	

	@Override
	public void onCameraChange(final CameraPosition position) {
		//    mCameraTextView.setText(position.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_play_game, menu);
		return true;
	}



	private void createChallenge(final LatLng point){

		// inflate createChallenge pop up window
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().
				getSystemService(LAYOUT_INFLATER_SERVICE);  
		View popupView = layoutInflater.inflate(R.layout.fill_in_challenge, null);  
		final PopupWindow popupWindow = new PopupWindow(popupView,700, 1000); 

		final EditText myChallenge = (EditText) popupView.findViewById(R.id.challenge);
		final EditText answer1 = (EditText) popupView.findViewById(R.id.wrongAns1);
		final EditText answer2 = (EditText) popupView.findViewById(R.id.wrongAns2);
		final EditText answer3 = (EditText) popupView.findViewById(R.id.wrongAns3);
		final EditText correctAnswer = (EditText) popupView.findViewById(R.id.correctAns);
		Button buttonBack = (Button) popupView.findViewById(R.id.back);
		Button buttonDone = (Button) popupView.findViewById(R.id.done);
		TextView tLat = (TextView) popupView.findViewById(R.id.tLat);
		TextView tLong = (TextView) popupView.findViewById(R.id.tLong);
		TextView tAddress = (TextView) popupView.findViewById(R.id.tAddress);

		tLat.setText("Lat: " + point.latitude);
		tLong.setText("Long: " + point.longitude);
		tAddress.setText("Address: " + newAddress);
		tLat.setTextSize(10);
		tLong.setTextSize(10);
		tAddress.setTextSize(15);


		popupWindow.setFocusable(true); 
		popupWindow.showAsDropDown(getMyLocation, 0, 300);//showAsDropDown(getMyLocation);//some location on the layout)


		buttonBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});

		buttonDone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(
						(myChallenge.getText() != null)&&
						(correctAnswer.getText() != null)&& 
						(answer1.getText() !=null)&&
						(answer2.getText() != null)&&
						(answer3.getText() != null)){
					Challenge challenge = new Challenge(point,
							myChallenge.getText().toString(),
							correctAnswer.getText().toString(), 
							new String[] {answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString()});
					//add new challenge to DB
					long id = db.addChallenge(challenge);
					mMap.addMarker(new MarkerOptions().position(point).visible(true));
					if(id!=-1)
						Toast.makeText(getBaseContext(), "challenge added!", Toast.LENGTH_LONG).show();
					else
						Toast.makeText(getBaseContext(), "Error on inserting challenge to DB", Toast.LENGTH_LONG).show();
				}else
					Toast.makeText(getBaseContext(), "Error some fiels is null", Toast.LENGTH_LONG).show();
				popupWindow.dismiss();
			}
		});

		

	}

	private void doReverseGeocoding1(Location location, int ourCase) {
		// Since the geocoding API is synchronous and may take a while.  You don't want to lock
		// up the UI thread.  Invoking reverse geocoding in an AsyncTask.
		(new ReverseGeocodingTask1(this, ourCase)).execute(new Location[] {location});
	}	

	// AsyncTask encapsulating the reverse-geocoding API.  Since the geocoder API is blocked,
	// we do not want to invoke it from the UI thread.
	private class ReverseGeocodingTask1 extends AsyncTask<Location, Void, Void> {
		Context mContext;
		int theCase;

		public ReverseGeocodingTask1(Context context, int ourCase) {
			super();
			mContext = context;
			theCase = ourCase;
		}


		@Override
		protected Void doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

			Location loc = params[0];
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			} catch (IOException e) {
				e.printStackTrace();
				// Update address field with the exception.
				Message.obtain(mHandler, theCase, e.toString()).sendToTarget();
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				// Format the first line of address (if available), city, and country name.
				String addressText = String.format("%s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
								address.getLocality(),
								address.getCountryName());
				// Update address field on UI.
				Message.obtain(mHandler, theCase, addressText).sendToTarget();
			}
			return null;
		}
	}
}
