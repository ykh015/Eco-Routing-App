package com.ecorouting;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DataCollector extends Activity implements LocationListener,OnClickListener {
	double latitude,longitude,speed;
	String s_lat,s_long,s_speed,s_sat,s_accuracy,s_bearing;
	float accuracy,bearing;
	public static int Satellites = 0;
	LocationManager lm;
	TextView tv_lat,tv_long,tv_speed,tv_accuracy,tv_sat;
	File f_data;
	DateFormat format4 = new SimpleDateFormat( "MM-dd-yyyy" );
	FileWriter writer_gps;
	Button b;
	String fname;
	boolean isRunning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.datacollector);
		 tv_lat=(TextView)findViewById(R.id.tv_lat);
		 tv_long=(TextView)findViewById(R.id.tv_long);
		 tv_speed=(TextView)findViewById(R.id.tv_speed);
		 tv_accuracy=(TextView)findViewById(R.id.tv_accuracy);
		 tv_sat=(TextView)findViewById(R.id.tv_sat);
		 b=(Button)findViewById(R.id.button1);
		
		 b.setOnClickListener(this);
		 lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		 
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(b.getText().equals("Start")){
			isRunning=true;
			b.setText("Stop");
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
					DataCollector.this);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
					DataCollector.this);
			GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
				public void onGpsStatusChanged(int event) {
					// Log("In onGpsStatusChanged event: " + event);
					if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS
							|| event == GpsStatus.GPS_EVENT_FIRST_FIX) {
						GpsStatus status = lm.getGpsStatus(null);
						Iterable<GpsSatellite> sats = status.getSatellites();
						// Check number of satellites in list to determine fix
						// state
						Satellites = 0;
						for (GpsSatellite sat : sats) {
							if (sat.usedInFix())
								Satellites++;
						}
						// Log("Setting Satellites from GpsStatusListener: " +
						// Satellites);
					}
				}
			};
			lm.addGpsStatusListener(gpsStatusListener);
			create_file();
			try {
				writer_gps = new FileWriter(f_data, true);
				writer_gps.append("Date,Time,Latitude,Longitude,Speed,Satellites,Accuracy,Bearing");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(b.getText().equals("Stop")){
			isRunning=false;
			lm.removeUpdates(DataCollector.this);
			try {
				writer_gps.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Intent i = new Intent(DataCollector.this,Route.class);
			i.putExtra("File", fname);
			startActivity(i);
			finish();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude=location.getLatitude();
		longitude=location.getLongitude();
		speed=location.getSpeed();
		accuracy=location.getAccuracy();
		bearing=location.getBearing();
		
		s_lat=Double.toString(latitude);
		s_long=Double.toString(longitude);
		s_speed=Double.toString(speed);
		s_accuracy=Float.toString(accuracy);
		s_bearing=Float.toString(bearing);
		
		
		tv_lat.setText(s_lat);
		tv_long.setText(s_long);
		tv_accuracy.setText(s_accuracy);
		tv_speed.setText(s_speed);
		tv_sat.setText(" "+Satellites);
		String data=s_lat+","+s_long+","+s_speed+","+Satellites+","+s_accuracy+","+s_bearing;
		try {
			String gps_date = format4.format(new Date());
			String gps_time=DateFormat.getTimeInstance().format(new Date());
			writer_gps.append("\n"+gps_date +","+gps_time+","+ data);
			//System.out.println("GPS onChange() "+gps_time);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	private void create_file() {
		String pathToExternalStorage = Environment
				.getExternalStorageDirectory().toString();
		File appDirectory = new File(pathToExternalStorage + "/" + "Eco_Routing");
		appDirectory.mkdirs();
		
		String date = format4.format(new Date());
		String time=DateFormat.getTimeInstance().format(new Date());

		fname=date +" "+time+ "_Data.txt";
		f_data = new File(appDirectory, fname);

		try {
			if (!f_data.exists())
				f_data.createNewFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(isRunning)
	    this.moveTaskToBack(true);
		else
			finish();
	}

}
