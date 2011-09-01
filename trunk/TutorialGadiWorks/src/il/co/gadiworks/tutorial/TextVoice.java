package il.co.gadiworks.tutorial;

import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TextVoice extends Activity implements OnClickListener {
	static final String[] texts = {
		"What's up Gangstaas!", "You smell!", "I love android!"
	};
	
	TextToSpeech tts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textvoice);
		
		Button b = (Button) findViewById(R.id.bTextToVoice);
		b.setOnClickListener(this);
		
		tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			
			@Override
			public void onInit(int status) {
				if (status != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.US);
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		Random r = new Random();
		String random = texts[r.nextInt(texts.length)];
		
		tts.speak(random, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
	}
}