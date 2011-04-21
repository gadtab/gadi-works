package il.co.gadiworks.openglstuff;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class RotateGLSurfaceView extends GLSurfaceView {
	private OpenGLRenderer2 renderer;
	
	public RotateGLSurfaceView(Context context) {
		super(context);
		renderer = new OpenGLRenderer2(context);
		setRenderer(renderer);
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		queueEvent(new Runnable() {
			
			@Override
			public void run() {
				renderer.ry = event.getX();
				renderer.rx = event.getY();
			}
		});
		return true;
	}
}
