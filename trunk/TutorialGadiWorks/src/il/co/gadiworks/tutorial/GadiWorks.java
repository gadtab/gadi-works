package il.co.gadiworks.tutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

public class GadiWorks extends View {
	Bitmap bBall;
	float changingY;
	Typeface font;

	public GadiWorks(Context context) {
		super(context);
		
		bBall = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
		changingY = 0;
		font = Typeface.createFromAsset(context.getAssets(), "kberry.ttf");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawColor(Color.WHITE);
		
		Paint textPaint = new Paint();
		textPaint.setARGB(50, 128, 0, 0);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(85);
		textPaint.setTypeface(font);
		
		canvas.drawText("GadiWorks", canvas.getWidth() / 2, 200, textPaint);
		
		canvas.drawBitmap(bBall, (canvas.getWidth() / 2), changingY, null);
		
		if (changingY < canvas.getHeight()) {
			changingY += 10;
		}
		else {
			changingY = 0;
		}
		
		Rect middleRect = new Rect();
		middleRect.set(0, 400, canvas.getWidth(), 550);
		Paint ourBlue = new Paint();
		ourBlue.setColor(Color.GREEN);
		
		canvas.drawRect(middleRect, ourBlue);
		
		invalidate();
	}
}
