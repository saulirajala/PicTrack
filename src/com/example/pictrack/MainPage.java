package com.example.pictrack;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/*
 * Sovelluksessa on lista paikoista, joissa on otettu kuva. Lista pitää
 * sisällään kuvan ottamispaikan koordinaatit ja ajan. Kun lista-itemiä
 * klikataan => näyttää kuvan
 * Aktiviteetit:
 * - pääsivu (MainPage), jossa tekstiä, nappeja ja toiminnallisuutta
 * 		-myös fragment layout
 * 		-toiminnallisuus = menu (about-diologi, ja jotain muuta)
 * 		-kaksi nappia: avaa ListView ja avaa picture view
 * 		-some labels as captions 
 * 		
 * - listanäkymä (list view), joka näyttää paikan, jossa käyttäjä on ollut
 * 		-normal layout
 * - kuvanäkymä (picture view), joka näyttää kuvat (yhden ja useamman)
 * - splash screen aloitukseen (fragment layout)
 * 		-kun orientaatio vaihtuu => älä lataa kaikkia uudestaan
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
