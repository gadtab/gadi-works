package il.co.gadiworks.games.framework.impl;

import il.co.gadiworks.games.framework.Input.TouchEvent;
import il.co.gadiworks.games.framework.Pool;
import il.co.gadiworks.games.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

public class SingleTouchHandler implements TouchHandler {
	boolean isTouched;
	int touchX, touchY;
	
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	
	float scaleX, scaleY;
	
	public SingleTouchHandler(View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {

			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		
		this.touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			TouchEvent touchEvent = this.touchEventPool.newObject();
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				isTouched = true;
				break;
			case MotionEvent.ACTION_MOVE:
				touchEvent.type = TouchEvent.TOUCH_DRAGGED;
				isTouched = true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchEvent.type = TouchEvent.TOUCH_UP;
				isTouched = false;
				break;
			}
			
			touchEvent.x = this.touchX = (int)(event.getX() * this.scaleX);
			touchEvent.y = this.touchY = (int)(event.getY() * this.scaleY);
			
			this.touchEventsBuffer.add(touchEvent);
			
			return true;
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			if (pointer == 0) {
				return this.isTouched;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			return this.touchX;
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			return this.touchY;
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized (this) {
			int len = this.touchEvents.size();
			
			for (int i = 0; i < len; i++) {
				this.touchEventPool.free(this.touchEvents.get(i));
			}
			
			this.touchEvents.clear();
			this.touchEvents.addAll(this.touchEventsBuffer);
			this.touchEventsBuffer.clear();
			
			return this.touchEvents;
		}
	}
}