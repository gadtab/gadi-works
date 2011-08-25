package il.co.gadiworks.thebasics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class TutorialFour extends Activity implements OnCheckedChangeListener {
	MediaPlayer song1, song2, song3, song4;
	int whatSong;
	int rSong;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial4);
		
		whatSong = 0;
		
		RadioGroup rgMusic = (RadioGroup) findViewById(R.id.groupMusic);
		rgMusic.setOnCheckedChangeListener(this);
		
		song1 = MediaPlayer.create(this, R.raw.hot_dog);
		song2 = MediaPlayer.create(this, R.raw.party);
		song3 = MediaPlayer.create(this, R.raw.steadfast_loyal_and_true);
		song4 = MediaPlayer.create(this, R.raw.yesterday_one_more);
		
		Button bPlay = (Button) findViewById (R.id.bPlay);
		Button bStop = (Button) findViewById (R.id.bStop);
		
		bPlay.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
				File file = new File(path, "GadiWorks Song " + whatSong + ".mp3");
				
				try {
					switch (whatSong) {
					case 1:
						rSong = R.raw.hot_dog;
						break;
					case 2:
						rSong = R.raw.party;
						break;
					case 3:
						rSong = R.raw.steadfast_loyal_and_true;
						break;
					case 4:
						rSong = R.raw.yesterday_one_more;
						break;
					}
					
					InputStream is = getResources().openRawResource(rSong);
					OutputStream os = new FileOutputStream(file);
					
					byte[] data = new byte[is.available()];
					is.read(data);
					os.write(data);
					
					is.close();
					os.close();
					
					Toast.makeText(TutorialFour.this, "Song Saved", Toast.LENGTH_SHORT).show();
				}
				catch (IOException e) {
					Toast fail = Toast.makeText(TutorialFour.this, "Fail", Toast.LENGTH_SHORT);
					fail.show();
					
					e.printStackTrace();
				}
				
				return false;
			}
		});
		
		bPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get what the radio button is set up as
				// and play the song accordingly
				// but first we want to make sure stop the 
				// current song if it is playing, 
				// so we won't get and overlap
				if(song1.isPlaying()){
					song1.pause();
					song1.seekTo(0);
				}
				if(song2.isPlaying()){
					song2.pause();
					song2.seekTo(0);
				}
				if(song3.isPlaying()){
					song3.pause();
					song3.seekTo(0);
				}
				if(song4.isPlaying()){
					song4.pause();
					song4.seekTo(0);
				}
				
				// Now we find what song is selected from our int whatsong
				// 0 means no song is selected
				// 1 means the first song is selected, etc
				switch (whatSong){
				case 0:
					Toast noSong = Toast.makeText(TutorialFour.this, "Please select a song", Toast.LENGTH_SHORT);
					noSong.show();
					break;
				case 1:
					song1.start();
					break;
				case 2:
					song2.start();
					break;
				case 3:
					song3.start();
					break;
				case 4:
					song4.start();
					break;
				}
			}
		});
		
		bStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Stop all music
				if(song1.isPlaying()){
					song1.pause();
					song1.seekTo(0);
				} else if(song2.isPlaying()){
					song2.pause();
					song2.seekTo(0);
				}else if(song3.isPlaying()){
					song3.pause();
					song3.seekTo(0);
				} else if(song4.isPlaying()){
					song4.pause();
					song4.seekTo(0);
				}
			}
		});
	}
	
	// This is the method that will change our whatSong variable when
	// a radio button is clicked.
	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// Change our Song to what the current radio button is
		// This will happen automatically as the radio button is changed
		
		// First we need to check for the Id of the radio button
		// This will be the int passed into this method, named arg1
		switch (arg1){
		case R.id.rbMusic1:
			whatSong = 1;
			break;
		case R.id.rbMusic2:
			whatSong = 2;
			break;
		case R.id.rbMusic3:
			whatSong = 3;
			break;
		case R.id.rbMusic4:
			whatSong = 4;
			break;
		}
	}
}
