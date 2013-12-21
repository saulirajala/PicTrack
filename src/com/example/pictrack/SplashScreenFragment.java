package com.example.pictrack;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashScreenFragment extends Fragment {

	/*private static final Logger log = LoggerFactory
	        .getLogger(SplashScreenFragment.class);*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splashscreen, container, false);
		return view;
	}
}
