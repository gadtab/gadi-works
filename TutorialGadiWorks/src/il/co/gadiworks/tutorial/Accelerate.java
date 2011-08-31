package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Accelerate extends Activity implements SensorEventListener {
	float x, y, sensorX, sensorY;
	Bitmap ball;
	SensorManager sm;
	GadiWorksSurface ourSurface;
	
	public class GadiWorksSurface extends SurfaceView implements Runnable {
		SurfaceHolder ourHolder;
		Thread ourThread = null;
		boolean isRunning = false;

		public GadiWorksSurface(Context context) {
			super(context);
			
			ourHolder = getHolder();
		}
		
		public void pause() {
			isRunning = false;
			
			while (true) {
				try {
					ourThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				break;
			}
			
			ourThread = null;
		}
		
		public void resume() {
			isRunning = true;
			ourThread = new Thread(this);
			ourThread.start();
		}

		@Override
		public void run() {
			while (isRunning) {
				if (!ourHolder.getSurface().isValid()) {
					continue;
				}
				
				Canvas canvas = ourHolder.lockCanvas();
				canvas.drawRGB(128, 0, 0);
				
				float centerX = canvas.getWidth() / 2;
				float centerY = canvas.getHeight() / 2;
				
				canvas.drawBitmap(ball, centerX + (sensorX * 20), centerY + (sensorY * 20), null);
				
				ourHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ourSurface = new GadiWorksSurface(this);
		setContentView(ourSurface);
		
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if (sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			Sensor s = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		ball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
		x = y= sensorX = sensorY = 0;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent e) {
		try {
			Thread.sleep(16);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		sensorX = - e.values[0];
		sensorY = e.values[1];
	}

	@Override
	protected void onPause() {
		super.onPause();
		sm.unregisterListener(this);
		ourSurface.pause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ourSurface.resume();
	}
}