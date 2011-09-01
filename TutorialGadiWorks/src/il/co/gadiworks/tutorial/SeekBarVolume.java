package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarVolume extends Activity implements OnSeekBarChangeListener {
	SeekBar sb;
	MediaPlayer mp;
	AudioManager am;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seekbarvolume);
		
		sb = (SeekBar) findViewById(R.id.sbVolume);
		
		mp = MediaPlayer.create(this, R.raw.splashsound);
		mp.start();
		
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		int maxV = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);		
		int curV = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		sb.setMax(maxV);
		sb.setProgress(curV);
		sb.setOnSeekBarChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.release();
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		
	}
}