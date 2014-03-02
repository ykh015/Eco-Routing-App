package com.ecorouting;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AllRoutes extends Activity implements OnItemClickListener {
	TextView et;
	ListView l;
	String fname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.allroutes);
		String pathToExternalStorage = Environment
				.getExternalStorageDirectory().toString();
		String path = pathToExternalStorage + "/" + "Eco_Routing";
		java.io.File mfile = new java.io.File(path);
		java.io.File[] list = mfile.listFiles();
		
		l = (ListView)findViewById(R.id.listView1);
		ArrayList<String> slist = new ArrayList<String>();


		if(list==null){
			slist.add("No Trips Found");
		} else {
			for (int i = 0; i < list.length; i++) {
				if (!list[i].getName().equals("Summary.txt"))
					slist.add(list[i].getName().substring(0, list[i].getName().length()-9));
			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, slist);
		l.setAdapter(adapter);
		l.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		TextView temp = (TextView) arg1;
		fname=(String) temp.getText() + "_data.txt";
		Intent i = new Intent(AllRoutes.this,Route.class);
		i.putExtra("File", fname);
		startActivity(i);

	}
	
	

}
