package com.ecorouting;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class Menu extends Activity implements OnItemClickListener {

	View myView;
	TextView et;
	ListView l;		
	ArrayList<String> slist = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		l = (ListView)findViewById(R.id.listView1);


		slist.add("Data Recording");
		slist.add("Data Visualization");
		slist.add("Route Comparison");
		slist.add("Route Refinement");
		slist.add("Computing New Route");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, slist);
		l.setAdapter(adapter);
		l.setOnItemClickListener(this);


	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//System.out.println(arg2);
		if(arg2==0){
			Intent i = new Intent(this, DataCollector.class);
			this.startActivity(i);
		}
		if(arg2==1){
			Intent i = new Intent(this, AllRoutes.class);
			this.startActivity(i);
		}

		
	}

}
