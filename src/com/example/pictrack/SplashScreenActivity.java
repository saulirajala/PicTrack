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

public class SplashScreenActivity extends FragmentActivity {

	protected MyStateSaver data;
	private final LocationListener mLocationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

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

	protected void startNextActivity() {
		Intent intent = new Intent(this, MainPage.class);
		this.startActivity(intent);
		this.finish();
	}

	protected void doInit() {
		this.data.doInit = false;
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {

				try {

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