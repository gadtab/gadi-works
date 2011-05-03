package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MyMain extends Activity {
	MediaPlayer mpSplash;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        mpSplash = MediaPlayer.create(this, R.raw.hi);
        mpSplash.start();
        
        Thread logoTimer = new Thread()
        {
        	public void run() {
        		try {
        			int logoTimer = 0; 
        			while (logoTimer < 2000) {
        				sleep(100);
        				logoTimer += 100;
        			}
        			
        			startActivity(new Intent("il.co.gadiworks.thebasics.CLEARSCREEN"));
        		} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		finally {
        			finish();
        		}
        	}
        };
        
        logoTimer.start();
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mpSplash.release();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mpSplash.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mpSplash.start();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
    
    
}