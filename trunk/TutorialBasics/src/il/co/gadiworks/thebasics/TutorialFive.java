package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.os.Bundle;

public class TutorialFive extends Activity {
	
	GadiWorks sweetBK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sweetBK = new GadiWorks(this);
		setContentView(sweetBK);
	}
}
