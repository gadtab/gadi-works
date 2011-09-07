package il.co.gadiworks.tutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.view.View;

public class FaceDetectionView extends View {
	private static final int NUM_OF_FACES = 5;
	private static final int CIRCLE_STROKE_WIDTH = 5;
	
	private FaceDetector faceDetector;
	private Face[] detectedFaces = new Face[NUM_OF_FACES];
	private PointF[] midPoint = new PointF[NUM_OF_FACES];
	private float[] eyesDistance = new float[NUM_OF_FACES];
	private Bitmap bitmapImg;
	private Paint paintEye = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int imageWidth, imageHeight;
	private float widthNormalize, heightNormalize;
	int numOfFacesDetected;

	public FaceDetectionView(Context context) {
		super(context);
		
		// convert to Bitmap
		BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
		bitmapFactory.inPreferredConfig = Bitmap.Config.RGB_565;
		bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.gadiworks_hi, bitmapFactory);
		
		// face detect
		imageWidth = bitmapImg.getWidth();
		imageHeight = bitmapImg.getHeight();
		
		faceDetector = new FaceDetector(imageWidth, imageHeight, NUM_OF_FACES);
		numOfFacesDetected = faceDetector.findFaces(bitmapImg, detectedFaces);
		
		// prepare the paint eliptic eye mark.
		paintEye.setColor(Color.YELLOW);
		paintEye.setStyle(Style.STROKE);
		
		// extract the face detection results
		for (int indx = 0; indx < numOfFacesDetected; indx++) {
			PointF eyesMidPointTmp = new PointF();
			detectedFaces[indx].getMidPoint(eyesMidPointTmp);
			midPoint[indx] = eyesMidPointTmp;
			eyesDistance[indx] = detectedFaces[indx].eyesDistance();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// prepare for onDraw
		widthNormalize = getWidth() * 1.0f / imageWidth;
		heightNormalize = getHeight() * 1.0f / imageHeight;
		
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bitmapImg, null, new Rect(0, 0, getWidth(), getHeight()), null);
		canvas.drawText("Number of detected faces = " + numOfFacesDetected, 100, 80, paintEye);
		canvas.drawText("Info on face 1 only. Eye dist = " + eyesDistance[0], 100, 100, paintEye);
		canvas.drawText("Middle Point = " + midPoint[0].x + ", " + midPoint[0].y, 100, 120, paintEye);
		canvas.drawText("Confidence Factor = " + detectedFaces[0].confidence(), 100, 140, paintEye);
		
		for (int indx = 0; indx < numOfFacesDetected; indx++) {
			float normalX = midPoint[indx].x * widthNormalize;
			float normalY = midPoint[indx].y * heightNormalize;
			
			paintEye.setStrokeWidth(CIRCLE_STROKE_WIDTH);
			RectF rect = new RectF(
						normalX - (eyesDistance[indx] * 2.5f), 
						normalY - (eyesDistance[indx] / 2), 
						normalX + (eyesDistance[indx] * 2.5f), 
						normalY + (eyesDistance[indx] / 3)
			);
			
			canvas.drawOval(rect, paintEye);
		}
	}
}