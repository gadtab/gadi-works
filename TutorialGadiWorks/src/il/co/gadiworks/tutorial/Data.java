package il.co.gadiworks.tutorial;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Data extends Activity implements OnClickListener {
	EditText etSend;
	Button bSA, bSAFR;
	TextView tvGot;
	RelativeLayout rl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get);
		
		initialize();
		// Adding an ad with only Java
		rl = (RelativeLayout) findViewById(R.id.relLayout);
		AdView ad = new AdView(this, AdSize.BANNER, "4dd345qwre3");
		rl.addView(ad);
		ad.loadAd(new AdRequest());
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
		switch (v.getId()) {
		case R.id.bSA:
			String bread = etSend.getText().toString();
			Bundle basket = new Bundle();
			basket.putString("key", bread);
			Intent a = new Intent(this, OpenedClass.class);
			a.putExtras(basket);
			startActivity(a);
			break;
		case R.id.bSAFR:
			Intent i = new Intent(this, OpenedClass.class);
			startActivityForResult(i, 0);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle basket = data.getExtras();
			String s = basket.getString("answer");
			tvGot.setText(s);
		}
	}
}
