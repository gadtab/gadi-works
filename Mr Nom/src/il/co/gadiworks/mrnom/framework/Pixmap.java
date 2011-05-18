package il.co.gadiworks.mrnom.framework;

import il.co.gadiworks.mrnom.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
}
