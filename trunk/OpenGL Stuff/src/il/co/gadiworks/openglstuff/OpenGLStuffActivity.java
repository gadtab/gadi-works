package il.co.gadiworks.openglstuff;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OpenGLStuffActivity extends Activity {
	private RotateGLSurfaceView view;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new RotateGLSurfaceView(this);
//        GLSurfaceView view = new GLSurfaceView(this);
//        view.setRenderer(new OpenGLRenderer2());
        setContentView(view);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }
}