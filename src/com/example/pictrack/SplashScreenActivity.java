package com.example.pictrack;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

/** Tämä sekä muut splash screen jutut otettu osoitteesta
 * http://www.michenux.net/splashscreen-android-221.html
 * 
 */
public class SplashScreenActivity extends FragmentActivity {

	protected MyStateSaver data;
	
	/* Luokka, joka kuuntelee location muuttumista
	 * 
	 */
	private final LocationListener mLocationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment);

		this.data = (MyStateSaver) getLastCustomNonConfigurationInstance();
		if (this.data == null) {
			this.data = new MyStateSaver();
		}
		if (this.data.doInit) {
			doInit();
		}
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return this.data;
	}

	/* Metodi määrittä, mikä on seuraava activity, mikä aloitetaan
	 * 
	 */
	protected void startNextActivity() {
		Intent intent = new Intent(this, MainPage.class);
		this.startActivity(intent);
		this.finish();
	}

	/*
	 * Metodi määrittää, mitä splash screen aikana tehdään (tässä haetaan
	 * GPS-tiedot)
	 */
	protected void doInit() {
		this.data.doInit = false;
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {

				try {

					//haetaan GPS-tiedot
					String context = Context.LOCATION_SERVICE;
					LocationManager mLocationManager = (LocationManager) getSystemService(context);
					Criteria criteria = new Criteria();
					criteria.setAccuracy(Criteria.ACCURACY_FINE);
					criteria.setSpeedRequired(false);
					criteria.setAltitudeRequired(false);
					criteria.setBearingRequired(false);
					criteria.setCostAllowed(true);
					criteria.setPowerRequirement(Criteria.POWER_HIGH);
					String provider = mLocationManager.getBestProvider(
					        criteria, false);
					
					//pyöritetään splash-screen, kunnes GPS-tiedot on saatu
					while (String.valueOf(
					        mLocationManager.getLastKnownLocation(provider))
					        .equals("null")) {
						//Päivitetään 10s ja 10 metrin välein
						mLocationManager.requestLocationUpdates(provider, 10000, 10,
						        mLocationListener);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					startNextActivity();
				}
			}
		}, 1000);

	}

	private class MyStateSaver {
		public boolean doInit = true;
	}
}