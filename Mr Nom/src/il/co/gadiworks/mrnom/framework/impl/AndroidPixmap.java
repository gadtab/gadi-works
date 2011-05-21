package il.co.gadiworks.mrnom.framework.impl;

import android.graphics.Bitmap;
import il.co.gadiworks.mrnom.framework.Pixmap;
import il.co.gadiworks.mrnom.framework.Graphics.PixmapFormat;

public class AndroidPixmap implements Pixmap {
	Bitmap bitmap;
	PixmapFormat format;
	
	public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public int getWidth() {
		return this.bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return this.bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		return this.format;
	}

	@Override
	public void dispose() {
		this.bitmap.recycle();
	}
}