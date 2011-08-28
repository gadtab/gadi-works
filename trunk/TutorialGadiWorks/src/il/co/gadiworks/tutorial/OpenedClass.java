package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class OpenedClass extends Activity implements OnClickListener, OnCheckedChangeListener {
	TextView tvQuestion, tvText;
	RadioGroup rgAnswers;
	Button bReturn;
	
	String gotBread, setData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		
		initialize();
		
		SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String et = getData.getString("name", "Gadi is...");
		String values = getData.getString("list", "4");
		
		if (values.contentEquals("1")){
			tvQuestion.setText(et);
		}
		
		Bundle gotBasket = getIntent().getExtras();
		if (gotBasket != null) {
			gotBread = gotBasket.getString("key");
			tvQuestion.setText(gotBread);
		}
	}

	private void initialize() {
		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		tvText = (TextView) findViewById(R.id.tvText);
		rgAnswers = (RadioGroup) findViewById(R.id.rgAnswers);
		bReturn = (Button) findViewById(R.id.bReturn);
		
		bReturn.setOnClickListener(this);
		rgAnswers.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent person =  new Intent();
		Bundle backpack = new Bundle();
		backpack.putString("answer", setData);
		person.putExtras(backpack);
		setResult(RESULT_OK, person);
		finish();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId) {
		case R.id.rCrazy:
			setData = "Probably right!";
			break;
		case R.id.rSexy:
			setData = "Definitely right!";
			break;
		case R.id.rBoth:
			setData = "Spot on!";
			break;
		}
		tvText.setText(setData);
	}
}
