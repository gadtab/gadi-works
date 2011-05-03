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
				startActivity(new Intent("il.co.gadiworks.thebasics.TUTORIALONE"));
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
		}
		
		return false;
	}
    
    
}
