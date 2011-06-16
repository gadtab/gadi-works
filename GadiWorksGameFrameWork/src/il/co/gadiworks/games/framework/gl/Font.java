package il.co.gadiworks.games.framework.gl;

public class Font {
	public final Texture TEXTURE;
	public final int GLYPH_WIDTH;
	public final int GLYPH_HEIGHT;
	public final TextureRegion[] GLYPHS = new TextureRegion[96];
	
	public Font(Texture texture,
				int offsetX, int offsetY,
				int glyphsPerRow, int glyphWidth, int glyphHeight){
		this.TEXTURE = texture;
		this.GLYPH_WIDTH = glyphWidth;
		this.GLYPH_HEIGHT = glyphHeight;
		int x = offsetX;
		int y = offsetY;
		for (int i = 0; i < 96; i++) {
			this.GLYPHS[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
			x += glyphWidth;
			if (x == offsetX + glyphsPerRow * glyphWidth) {
				x = offsetX;
				y += offsetY;
			}
		}
	}
	
	public void drawText(SpriteBatcher batcher, String text, float x, float y) {
		int len = text.length();
		for (int i = 0; i < len; i++) {
			int c = text.charAt(i);
			if (c < 0 || c > this.GLYPHS.length - 1) {
				continue;
			}
			TextureRegion glyph = this.GLYPHS[c];
			batcher.drawSprite(x, y, this.GLYPH_WIDTH, this.GLYPH_HEIGHT, glyph);
			x += this.GLYPH_WIDTH;
		}
	}
}
