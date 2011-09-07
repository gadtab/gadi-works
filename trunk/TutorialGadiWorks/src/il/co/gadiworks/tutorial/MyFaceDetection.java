package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.os.Bundle;

public class MyFaceDetection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FaceDetectionView faceView = new FaceDetectionView(this);
		setContentView(faceView);
	}
}