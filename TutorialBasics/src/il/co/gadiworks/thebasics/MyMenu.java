package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyMenu extends Activity {
	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnTutorial1 = (Button) findViewById(R.id.btnTutorial1);
        btnTutorial1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("il.co.gadiworks.thebasics.TUTORIALONE"));
			}
		});
    }
}
