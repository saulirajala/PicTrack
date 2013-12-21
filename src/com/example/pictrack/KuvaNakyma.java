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
 * Pitää listata galleriassa olevat kuvat ja näyttää ne tässä Kuvaa klikatessa
 * => näytä kuva ja data (location + date) + Button (show kuvatNakyma)
 * Intents-ohjelmassa on koodi kuva-gallerian avaamiselle. Käytä sitä Muutokset:
 * klikattaessa kuvaa galleriassa => avaa kuvaNakyma, ei liitetä vanhalle
 * sivulle
 * 
 * @author Ratsala
 */
public class KuvaNakyma extends Activity {
	private static final int REQUEST_CODE_IMAGE = 5;
	private Bitmap bitmap;
	private ImageView imageView;

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

				// Otettu
				// http://stackoverflow.com/questions/14978566/how-to-get-selected-image-from-gallery-in-android
				// muodostaa kuvan osoitteen niin, että ReadExifData()-metodit
				// ymmärtää sen
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
				        filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();

				TextView textViewLocation = (TextView) findViewById(R.id.textLocation);
				textViewLocation.setText(ReadExifLocation(picturePath));

				TextView textViewDate = (TextView) findViewById(R.id.textDate);
				textViewDate.setText(ReadExifDate(picturePath));
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
	 * Otettu
	 * http://android-coding.blogspot.fi/2011/10/read-exif-of-jpg-file-using
	 * .html
	 */
	public String ReadExifLocation(String file) {
		String exif = "";
		try {
			ExifInterface exifInterface = new ExifInterface(file);
			exif += exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
			exif += exifInterface
			        .getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);

			exif += " "
			        + exifInterface
			                .getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
			exif += exifInterface
			        .getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(KuvaNakyma.this, e.toString(), Toast.LENGTH_LONG)
			        .show();
		}
		return exif;
	}

	/*
	 * Otettu
	 * http://android-coding.blogspot.fi/2011/10/read-exif-of-jpg-file-using
	 * .html
	 */
	String ReadExifDate(String file) {
		String exif = "";
		try {
			ExifInterface exifInterface = new ExifInterface(file);

			exif += "Date: "
			        + exifInterface
			                .getAttribute(ExifInterface.TAG_GPS_DATESTAMP);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(KuvaNakyma.this, e.toString(), Toast.LENGTH_LONG)
			        .show();
		}
		return exif;
	}

	public void onClickOpenGallery(View view) {
		Intent i = new Intent(this, KuvaNakyma.class);
		startActivity(i);
	}

}
