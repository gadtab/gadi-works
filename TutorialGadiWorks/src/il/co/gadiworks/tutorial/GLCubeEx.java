package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class GLCubeEx extends Activity {
	GLSurfaceView ourSurface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ourSurface = new GLSurfaceView(this);
		ourSurface.setRenderer(new GLCubeRendererEx());
		
		setContentView(ourSurface);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		ourSurface.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ourSurface.onResume();
	}
}