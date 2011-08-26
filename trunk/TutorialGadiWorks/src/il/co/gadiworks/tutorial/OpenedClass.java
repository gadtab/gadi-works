package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		
		initialize();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId) {
		case R.id.rCrazy:
			
			break;
		case R.id.rSexy:
			
			break;
		case R.id.rBoth:
	
			break;
		}
	}
}
