package il.co.gadiworks.thebasics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GadiWorks extends View {
	Bitmap cloud;
	int x, y;
	Paint paint = new Paint();

	public GadiWorks(Context context) {
		super(context);
		
		cloud = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// Set background.
		Rect cBK = new Rect();
		cBK.set(0, 0, canvas.getWidth(), canvas.getHeight());
		
		Paint pBlue = new Paint();
		pBlue.setStyle(Paint.Style.FILL);
		pBlue.setColor(Color.BLUE);
		
		canvas.drawRect(cBK, pBlue);
		
		drawCloud(x, y, canvas);
		
		if (x < canvas.getWidth()) {
			x += 10;
		}
		else {
			y += 10;
			x = 0;
		}
		
		invalidate();
	}

	private void drawCloud(int x2, int y2, Canvas canvas) {
		canvas.drawBitmap(cloud, x2, y2, paint);
	}
}
