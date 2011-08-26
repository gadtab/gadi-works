package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Data extends Activity implements OnClickListener {
	EditText etSend;
	Button bSA, bSAFR;
	TextView tvGot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get);
		
		initialize();
	}

	private void initialize() {
		etSend = (EditText) findViewById(R.id.etSend);
		bSA = (Button) findViewById(R.id.bSA);
		bSAFR = (Button) findViewById(R.id.bSAFR);
		tvGot = (TextView) findViewById(R.id.tvGot);
		
		bSA.setOnClickListener(this);
		bSAFR.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}
}
