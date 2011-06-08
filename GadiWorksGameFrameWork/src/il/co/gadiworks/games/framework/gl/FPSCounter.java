package il.co.gadiworks.games.framework.gl;

import android.util.Log;

public class FPSCounter {
	long startTime = System.nanoTime();
	int frames = 0;
	
	public void logFrame() {
		this.frames++;
		if (System.nanoTime() - this.startTime >= 1000000000) {
			Log.d("FPSCounter", "fps: " + this.frames);
			
			this.frames = 0;
			this.startTime = System.nanoTime();
		}
	}
}
