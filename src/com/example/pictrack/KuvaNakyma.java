package com.example.pictrack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Tämä luokka huolehtii yksittäisestä kuvanäkymästä. Konstruktorissa luodaan
 * uusi Intent, joka näyttää puhelimen galleriassa olevat kuvat. Kun kuva
 * valitaan, olio tulostaa kuvan ja siihen liittyvän datan (date ja gps-tiedot).
 */
public class KuvaNakyma extends Activity {
	private static final int REQUEST_CODE_IMAGE = 5;
	private Bitmap bitmap;
	private ImageView imageView;
	
	/*
	 * Mitä tapahtuu, kun luokka luodaan
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kuvanakyma);
		imageView = (ImageView) findViewById(R.id.image);

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, REQUEST_CODE_IMAGE);
	}
	
	/*
	 * Metodissa kerrotaan mitä tapahtuu, kun ohjelman suorittaminen tulee 
	 * takaisin tälle oliolle. Eli kun käyttäjä on klikannut jostain kuvasta
	 * galleriassa. Eli tapahtuu seuraavaa: kuva ja sen exif-tiedto tulostetaan
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		InputStream stream = null;
		if (requestCode == REQUEST_CODE_IMAGE
		        && resultCode == Activity.RESULT_OK)
			try {

				if (bitmap != null) {
					bitmap.recycle();
				}
				stream = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				imageView.setImageBitmap(bitmap);

				String picturePath = muodostaOsoite(data.getData());
				TextView textViewLocation = (TextView) findViewById(R.id.textLocation);
				textViewLocation.setText(readExif(picturePath, "Location"));

				TextView textViewDate = (TextView) findViewById(R.id.textDate);
				textViewDate.setText(readExif(picturePath, "Date"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (stream != null)
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	}

	/*
	 * Otettu osoitteesta
	 * http://stackoverflow.com/questions/14978566/how-to-get-
	 * selected-image-from-gallery-in-android Muodostaa kuvan osoitteen niin,
	 * että ReadExif()-metodit ymmärtävät sen
	 */
	private String muodostaOsoite(Uri data) {
		Uri selectedImage = data;
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(selectedImage,
		        filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	/*
	 * Otettu
	 * http://android-coding.blogspot.fi/2011/10/read-exif-of-jpg-file-using
	 * .html
	 * 
	 * Metodi lukee kuva-tiedoston exif-tiedot riippuen sille annetusta lipusta
	 * Jos flag==Location => palautetaan GPS-tiedot
	 * Jos flag==Date => palautetaan päivämäärä
	 *
	 */
	public String readExif(String file, String flag) {
		String exif = "";
		try {
			if (flag.equalsIgnoreCase("Location")) {
				ExifInterface exifInterface = new ExifInterface(file);
				exif += exifInterface
				        .getAttribute(ExifInterface.TAG_GPS_LATITUDE);
				exif += exifInterface
				        .getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);

				exif += " "
				        + exifInterface
				                .getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
				exif += exifInterface
				        .getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
				return exif;
			}
			ExifInterface exifInterface = new ExifInterface(file);

			exif += "Date: "
			        + exifInterface
			                .getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
			return exif;

		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(KuvaNakyma.this, e.toString(), Toast.LENGTH_LONG)
			        .show();
		}
		return exif;
	}

	/*
	 * Metodi avaa uudelleen puhelimen gallerian
	 */
	public void onClickOpenGallery(View view) {
		Intent i = new Intent(this, KuvaNakyma.class);
		startActivity(i);
	}

}
