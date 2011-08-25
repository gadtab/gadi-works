package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.os.Bundle;

public class DrawingMain extends Activity {
	DrawingTheBall v;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		v = new DrawingTheBall(this);
		setContentView(v);
	}
}
