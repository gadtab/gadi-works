package il.co.gadiworks.games.framework;

import il.co.gadiworks.games.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
}
