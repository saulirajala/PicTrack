package com.example.pictrack;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/*
 * Sovelluksessa on lista paikoista, joissa on otettu kuva. Lista pit��
 * sis�ll��n kuvan ottamispaikan koordinaatit ja ajan. Kun lista-itemi�
 * klikataan => n�ytt�� kuvan
 * Aktiviteetit:
 * - p��sivu (MainPage), jossa teksti�, nappeja ja toiminnallisuutta
 * 		-my�s fragment layout
 * 		-toiminnallisuus = menu (about-diologi, ja jotain muuta)
 * 		-kaksi nappia: avaa ListView ja avaa picture view
 * 		-some labels as captions 
 * 		
 * - listan�kym� (list view), joka n�ytt�� paikan, jossa k�ytt�j� on ollut
 * 		-normal layout
 * - kuvan�kym� (picture view), joka n�ytt�� kuvat (yhden ja useamman)
 * - splash screen aloitukseen (fragment layout)
 * 		-kun orientaatio vaihtuu => �l� lataa kaikkia uudestaan
 * 
 */

public class MainPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
