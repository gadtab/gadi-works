package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyMenu extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Set up the button sound.
        final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button_sound);
        
        // Button 1.
        Button btnTutorial1 = (Button) findViewById(R.id.btnTutorial1);
        btnTutorial1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.TUTORIALONE"));
				mpButtonClick.start();
			}
		});
        
        // Button 2.
        Button btnTutorial2 = (Button) findViewById(R.id.btnTutorial2);
        btnTutorial2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.TUTORIALTWO"));
				mpButtonClick.start();
			}
		});
        
        // Button 3.
        Button btnTutorial3 = (Button) findViewById(R.id.btnTutorial3);
        btnTutorial3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.TUTORIALTHREE"));
				mpButtonClick.start();
			}
		});
        
        // Button 4.
        Button btnTutorial4 = (Button) findViewById(R.id.btnTutorial4);
        btnTutorial4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.TUTORIALFOUR"));
				mpButtonClick.start();
			}
		});
        
        // Button 5.
        Button btnTutorial5 = (Button) findViewById(R.id.btnTutorial5);
        btnTutorial5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.TUTORIALFIVE"));
				mpButtonClick.start();
			}
		});
        
        // Button 6.
        Button btnTutorial6 = (Button) findViewById(R.id.btnTutorial6);
        btnTutorial6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.DRAWINGMAIN"));
				mpButtonClick.start();
			}
		});
        
        // Button 7.
        Button btnTutorial7 = (Button) findViewById(R.id.btnTutorial7);
        btnTutorial7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.SURFACEVIEWEXAMPLE"));
				mpButtonClick.start();
			}
		});
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.main_menu, menu);
    	
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menuSweet:
			startActivity(new Intent("il.co.gadiworks.thebasics.SWEET"));
			return true;
		case R.id.menuToast:
			Toast display = Toast.makeText(this, "Again, Welcome to GadiWorks!!!", Toast.LENGTH_SHORT);
			display.show();
			return true;
		}
		
		return false;
	}
}
