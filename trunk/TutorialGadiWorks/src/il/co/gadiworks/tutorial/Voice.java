package il.co.gadiworks.tutorial;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Voice extends Activity implements OnClickListener {
	ListView lv;
	static final int check = 1111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice);
		
		lv = (ListView) findViewById(R.id.lvVoiceReturn);
		Button b = (Button) findViewById(R.id.bVoice);
		
		b.setOnClickListener(this);
 	}

	@Override
	public void onClick(View v) {
		// Check to see if a recognition activity is present
//		PackageManager pm = getPackageManager();
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		i.putExtra("calling_package", "il.co.gadiworks.tutorial");
		i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak up son!");
		
//		List<ResolveInfo> activities = pm.queryIntentActivities(i, 0);
		
//		if (activities.size() != 0) {
		try{
			startActivityForResult(i, check);
		}
//		}
//		else {
		catch (ActivityNotFoundException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == check && resultCode == RESULT_OK) {
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}