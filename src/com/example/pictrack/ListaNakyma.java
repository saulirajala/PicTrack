package com.example.pictrack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.media.ExifInterface;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaNakyma extends Activity {

	private class MunAdapter extends ArrayAdapter<String> {

		private final Context context;
		private final ArrayList<String> kuvienURL;

		public MunAdapter(Context context, ArrayList<String> kuvienURL) {
			super(context, R.layout.activity_listviewexampleactivity, kuvienURL);
			this.context = context;
			this.kuvienURL = kuvienURL;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_row, parent, false);
			TextView textViewBottom = (TextView) rowView
			        .findViewById(R.id.bottom);
			TextView textViewUpper = (TextView) rowView
			        .findViewById(R.id.upper);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
			imageView.setImageResource(R.drawable.talvimaisema1);
			textViewBottom.setText(ReadExifDate(kuvienURL.get(position)));
			textViewUpper.setText(ReadExifLocation(kuvienURL.get(position)));

			return rowView;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listviewexampleactivity);

		final ListView listview = (ListView) findViewById(R.id.listview);

		// haetaan kaikki sdcard/DCIM kansiossa olevat tiedostot ja kansiot
		final ArrayList<String> kuvienURL = new ArrayList<String>();
		File[] listFile;
		File file = new File(
		        android.os.Environment.getExternalStorageDirectory(), "DCIM");

		if (file.isDirectory()) {
			listFile = file.listFiles();
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].getAbsolutePath().contains(".jpg"))
					kuvienURL.add(listFile[i].getAbsolutePath());
			}
		}

		final MunAdapter adapter = new MunAdapter(this, kuvienURL);
		listview.setAdapter(adapter);

		// asetetaan kuuntelija listan riveille
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
			        int position, long id) {
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				File file = new File(kuvienURL.get(position));
				intent.setDataAndType(Uri.fromFile(file), "image/*");
				startActivity(intent);
			}

		});
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
			Toast.makeText(ListaNakyma.this, e.toString(), Toast.LENGTH_LONG)
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
			Toast.makeText(ListaNakyma.this, e.toString(), Toast.LENGTH_LONG)
			        .show();
		}
		return exif;
	}

}