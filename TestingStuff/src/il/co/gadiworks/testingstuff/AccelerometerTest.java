package il.co.gadiworks.testingstuff;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerTest extends Activity implements SensorEventListener {
	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		textView = new TextView(this);
		setContentView(textView);
		
		SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0) {	
			textView.setText("No accelerometer installed");
		}
		else {
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			
			if (!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)) {
				textView.setText("Couldn't register sensor listener");
			}
		}
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		builder.setLength(0);
		builder.append("x: ").append(event.values[0]).append(", y: ").append(event.values[1]).append(", z: ").append(event.values[2]);
		
		textView.setText(builder.toString());
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// nothing to do here
	}
}