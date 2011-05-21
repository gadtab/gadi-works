package il.co.gadiworks.mrnom.framework.impl;

import il.co.gadiworks.mrnom.framework.Pool;
import il.co.gadiworks.mrnom.framework.Input.TouchEvent;
import il.co.gadiworks.mrnom.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

public class MultiTouchHandler implements TouchHandler {
	boolean[] isTouched = new boolean[20];
	int[] touchX = new int[20], touchY = new int[20];
	
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	
	float scaleX, scaleY;
	
	public MultiTouchHandler(View view, float scaleX, float scaleY) {
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
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerId = event.getPointerId(pointerIndex);
			
			TouchEvent touchEvent;// = this.touchEventPool.newObject();
			
			switch (action) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				touchEvent = this.touchEventPool.newObject();
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				touchEvent.pointer = pointerId;
				touchEvent.x = this.touchX[pointerId] = (int) (event.getX(pointerIndex) * this.scaleX);
				touchEvent.y = this.touchY[pointerId] = (int) (event.getY(pointerIndex) * this.scaleY);
				isTouched[pointerId] = true;
				this.touchEventsBuffer.add(touchEvent);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL:
				touchEvent = this.touchEventPool.newObject();
				touchEvent.type = TouchEvent.TOUCH_UP;
				touchEvent.pointer = pointerId;
				touchEvent.x = this.touchX[pointerId] = (int) (event.getX(pointerIndex) * this.scaleX);
				touchEvent.y = this.touchY[pointerId] = (int) (event.getY(pointerIndex) * this.scaleY);
				isTouched[pointerId] = false;
				this.touchEventsBuffer.add(touchEvent);
				break;
			case MotionEvent.ACTION_MOVE:
				int pointerCount = event.getPointerCount();
				for (int i = 0; i < pointerCount; i++) {
					pointerIndex = i;
					pointerId = event.getPointerId(pointerIndex);
					
					touchEvent = this.touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					touchEvent.pointer = pointerId;
					touchEvent.x = this.touchX[pointerId] = (int) (event.getX(pointerIndex) * this.scaleX);
					touchEvent.y = this.touchY[pointerId] = (int) (event.getY(pointerIndex) * this.scaleY);
//					isTouched[pointerId] = true;
					this.touchEventsBuffer.add(touchEvent);
				}
				break;
			}
			
			return true;
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			if (pointer < 0 || pointer >= 20) {
				return false;
			}
			else {
				return isTouched[pointer];
			}
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			if (pointer < 0 || pointer >= 20) {
				return 0;
			}
			else {
				return this.touchX[pointer];
			}
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			if (pointer < 0 || pointer >= 20) {
				return 0;
			}
			else {
				return this.touchY[pointer];
			}
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