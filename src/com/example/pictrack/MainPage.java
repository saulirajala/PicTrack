package com.example.pictrack;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

	public void onClickOpenList(View view) {
		Intent i = new Intent(this, ListaNakyma.class);
		startActivity(i);
	}

	/* klikataessa avaa uuden activityn kuten listassakin
	 * Uusi activity heti konstruktorissa avaa gallerian ja palautusarvona
	 * tullut kuva näytettään kuten Intents.javassa
	 * Tästä kuvasta ongitaan exif-tiedot
	 */
	public void onClickOpenImage(View view) {
		Intent i = new Intent(this, KuvaNakyma.class);
		startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_info:
				infoDialogi();
				return true;
			case R.id.menu_apu:
				// showHelp();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@SuppressWarnings("deprecation")
	private void infoDialogi() {
		AlertDialog alertDialog = new AlertDialog.Builder(MainPage.this)
		        .create();
		alertDialog.setTitle("Infoa");
		alertDialog.setMessage("Tämä on joku sovellus");
		alertDialog.setButton("Pois", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				closeOptionsMenu();
			}
		});
		alertDialog.show();
	}
}
