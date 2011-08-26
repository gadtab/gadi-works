package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity {
	MediaPlayer ourSong;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		ourSong = MediaPlayer.create(this, R.raw.splashsound);
		ourSong.start();
		
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(5000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally {
					Intent openStartingPoint = new Intent("il.co.gadiworks.tutorial.MENU");
					startActivity(openStartingPoint);
				}
			}
		};
		
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		ourSong.release();
		finish();
	}
}
