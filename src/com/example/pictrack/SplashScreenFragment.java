package com.example.pictrack;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** T�m� sek� muut splash screen jutut otettu osoitteesta
 * http://www.michenux.net/splashscreen-android-221.html
 * 
 */
public class SplashScreenFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splashscreen, container, false);
		return view;
	}
}
