package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SurfaceViewExample extends Activity implements OnTouchListener {
	OurView v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		v = new OurView(this);
		v.setOnTouchListener(this);
		
		setContentView(v);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		v.resume();
	}

	public class OurView extends SurfaceView implements Runnable {
		Thread t = null;
		SurfaceHolder holder;
		boolean isItOK = false;

		public OurView(Context context) {
			super(context);
			
			holder = getHolder();
		}

		@Override
		public void run() {
			while (isItOK) {
				// perform canvas drawing
				if (!holder.getSurface().isValid()) {
					continue;
				}
				
				Canvas c = holder.lockCanvas();
				c.drawARGB(255, 150, 150, 10);
				holder.unlockCanvasAndPost(c);
			}
		}
		
		public void pause() {
			isItOK = false;
			
			while (true) {
				try{
					t.join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			
			t = null;
		}
		
		public void resume() {
			isItOK = true;
			t = new Thread(this);
			t.start();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		return false;
	}
}
