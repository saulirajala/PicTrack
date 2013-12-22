package com.example.pictrack;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Sovelluksessa on lista paikoista, joissa on otettu kuva. Lista pit��
 * sis�ll��n kuvan ottamispaikan koordinaatit ja ajan. Kun lista-itemi�
 * klikataan => n�ytt�� kuvan
 * 
 * MainPage on nimens� mukaisesti sovelluksen p��sivu
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
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}
	
	/*
	 * Metodi m��ritt��, mit� tapahtuu kun painetaan "Avaa listan�kym�" -painiketta
	 * Eli aloitetaan uusi Intent (ListaNakyma.java)
	 */
	public void onClickOpenList(View view) {
		Intent i = new Intent(this, ListaNakyma.class);
		startActivity(i);
	}

	/*
	 * Metodi m��ritt��, mit� tapahtuu kun painetaan "Avaa kuvan�kym�" -painiketta
	 * Eli aloitetaan uusi Intent (KuvaNakyma.java)
	 */
	public void onClickOpenImage(View view) {
		Intent i = new Intent(this, KuvaNakyma.class);
		startActivity(i);
	}

	/*
	 * Kertoo mit� tapahtuu, kun menun painikkeita painetaan
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_info:
				infoDialogi();
				return true;
			case R.id.menu_apu:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * Metodi muodostaa info dialogin k�ytt�en hyv�ksi AlertDialog-luokkaa
	 */
	@SuppressWarnings("deprecation")
	private void infoDialogi() {
		AlertDialog alertDialog = new AlertDialog.Builder(MainPage.this)
		        .create();
		alertDialog.setTitle("Infoa");
		alertDialog.setMessage("T�m� on joku sovellus");
		alertDialog.setButton("Pois", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				closeOptionsMenu();
			}
		});
		alertDialog.show();
	}
}
