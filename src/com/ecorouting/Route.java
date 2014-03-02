package com.ecorouting;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Route extends Activity {
	GoogleMap map;
	PolylineOptions polylineOptions;
	ArrayList<Double> longitude = new ArrayList<Double>(0);
	ArrayList<Double> latitude = new ArrayList<Double>(0);
	String fname;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.route);
		fname = getIntent().getExtras().getString("File");

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		read_file();
		if(latitude.size()>0)
		{
		map.addMarker(new MarkerOptions().position(
				new LatLng(latitude.get(0), longitude.get(0))).title(
				"Start Point"));

		map.addMarker(new MarkerOptions().position(
				new LatLng(latitude.get(latitude.size() - 1), longitude
						.get(longitude.size() - 1))).title("End Point"));

		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude
				.get(0), longitude.get(0)));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

		map.moveCamera(center);
		map.animateCamera(zoom);

		polylineOptions = new PolylineOptions();

		for (int i = 0; i < latitude.size(); i = i + 1) {
			polylineOptions.add(new LatLng(latitude.get(i), longitude.get(i)));
		}

		polylineOptions.color(Color.BLUE);
		polylineOptions.width(10);
		polylineOptions.geodesic(false);

		map.addPolyline(polylineOptions);
		}
	}

	private void read_file() {
		// TODO Auto-generated method stub
		String pathToExternalStorage = Environment
				.getExternalStorageDirectory().toString();
		String csvFile = pathToExternalStorage + File.separator
				+ "Eco_Routing" + File.separator + fname;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int c = 0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if (c != 0) {
					String[] row = line.split(cvsSplitBy);
					latitude.add(Double.parseDouble(row[2]));
					longitude.add(Double.parseDouble(row[3]));
				}
				c++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}