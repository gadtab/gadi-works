package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StartingPoint extends Activity {
	static int counter = 0;
	Button bAdd, bSub;
	TextView tvDisplay;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
//        counter = 0;
        
        bAdd = (Button) findViewById(R.id.bAdd);
        bSub = (Button) findViewById(R.id.bSub);
        
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        
        bAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				counter++;
				tvDisplay.setText("Your total is " + counter);
			}
		});
        
        bSub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				counter--;
				tvDisplay.setText("Your total is " + counter);
			}
		});
    }
}