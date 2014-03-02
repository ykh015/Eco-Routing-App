package com.ecorouting;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


public class Splash extends Activity {
	MediaPlayer ourSong;

	@Override
	protected void onCreate(Bundle Blahblah) {
		// TODO Auto-generated method stub
		super.onCreate(Blahblah);
		setContentView(R.layout.splash);
		ourSong = MediaPlayer.create(Splash.this, R.raw.splash);
		ourSong.start();
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000); //ms
					
				} catch (InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openMainActivity = new Intent(Splash.this,Menu.class) ;
					Splash.this.startActivity(openMainActivity);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSong.release();
		finish();
	}
	
}
