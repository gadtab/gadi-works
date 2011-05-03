package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TutorialOne extends Activity {
	TextView tvGetInput;
	EditText etInput;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial1);
        
        tvGetInput = (TextView) findViewById(R.id.tvGetInput);
        etInput = (EditText) findViewById(R.id.etInput);
        
        Button btnOK = (Button) findViewById(R.id.btnOK);
        
        btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tvGetInput.setText(etInput.getText());
			}
		});
    }
}
