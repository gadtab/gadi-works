package il.co.gadiworks.tutorial;

import java.util.Locale;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {
	String[] classes = { "StartingPoint", "TextPlay", "Email", "Camera",
			"Data", "GFX", "GFXSurface", "SoundStuff", "Slider", "Tabs",
			"SimpleBrowser", "Flipper", "SharedPrefs", "InternalData",
			"ExternalData", "SQLiteExample", "Accelerate", "HttpExample",
			"WeatherXMLParsing", "GLExample", "GLCubeEx", "Voice", "TextVoice",
			"StatusBar", "SeekBarVolume", "ShoppingList", "MyFaceDetection" };

	TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, classes));

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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Class<?> ourClass;
		try {
			ourClass = Class.forName("il.co.gadiworks.tutorial."
					+ classes[position]);
			Intent ourIntent = new Intent(Menu.this, ourClass);
			tts.speak(classes[position], TextToSpeech.QUEUE_FLUSH, null);
			Thread.sleep(1000);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.cool_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aboutUs:
			Intent i = new Intent("il.co.gadiworks.tutorial.ABOUT");
			startActivity(i);
			break;
		case R.id.preferences:
			Intent p = new Intent("il.co.gadiworks.tutorial.PREFS");
			startActivity(p);
			break;
		case R.id.exit:
			finish();
			break;
		}

		return false;
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
