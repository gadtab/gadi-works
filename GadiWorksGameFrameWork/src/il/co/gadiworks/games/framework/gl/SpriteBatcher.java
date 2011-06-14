package il.co.gadiworks.games.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import il.co.gadiworks.games.framework.impl.GLGraphics;
import il.co.gadiworks.games.framework.math.Vector2;

public class SpriteBatcher {
	final float[] VERTICES_BUFFER;
	final Vertices VERTICES;
	
	int bufferIndex;
	int numSprites;
	
	public SpriteBatcher (GLGraphics glGraphics, int maxSprites) {
		this.VERTICES_BUFFER = new float[maxSprites * 4 * 4];
		this.VERTICES = new Vertices(glGraphics, maxSprites * 4, maxSprites * 6, false, true);
		this.bufferIndex = 0;
		this.numSprites = 0;
		
		short[] indices = new short[maxSprites * 6];
		int len = indices.length;
		short j = 0;
		for (int i = 0; i < len; i += 6, j += 4) {
			indices[i + 0] = (short)(j + 0);
			indices[i + 1] = (short)(j + 1);
			indices[i + 2] = (short)(j + 2);
			indices[i + 3] = (short)(j + 2);
			indices[i + 4] = (short)(j + 3);
			indices[i + 5] = (short)(j + 0);
		}
		this.VERTICES.setIndices(indices, 0, indices.length);
	}
	
	public void beginBatch(Texture texture) {
		texture.bind();
		this.numSprites = 0;
		this.bufferIndex = 0;
	}
	
	public void endBatch() {
		this.VERTICES.setVertices(this.VERTICES_BUFFER, 0, this.bufferIndex);
		this.VERTICES.bind();
		this.VERTICES.draw(GL10.GL_TRIANGLES, 0, this.numSprites * 6);
		this.VERTICES.unbind();
	}
	
	public void drawSprite(float x, float y, float width, float height, TextureRegion region) {
		float halfWidth = width / 2;
		float halfHeight = height / 2;
		float x1 = x - halfWidth;
		float y1 = y - halfHeight;
		float x2 = x + halfWidth;
		float y2 = y + halfHeight;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x1;
		this.VERTICES_BUFFER[this.bufferIndex++] = y1;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U1;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V2;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x2;
		this.VERTICES_BUFFER[this.bufferIndex++] = y1;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U2;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V2;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x2;
		this.VERTICES_BUFFER[this.bufferIndex++] = y2;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U2;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V1;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x1;
		this.VERTICES_BUFFER[this.bufferIndex++] = y2;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U1;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V1;
		
		this.numSprites++;
	}
	
	public void drawSprite(float x, float y, float width, float height, float angle, TextureRegion region) {
		float halfWidth = width / 2;
		float halfHeight = height / 2;
		
		float rad = angle * Vector2.TO_RADIANS;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);
		
		float x1 = -halfWidth * cos - (-halfHeight) * sin;
		float y1 = -halfWidth * sin + (-halfHeight) * cos;
		float x2 = halfWidth * cos - (-halfHeight) * sin;
		float y2 = halfWidth * sin + (-halfHeight) * cos;
		float x3 = halfWidth * cos - halfHeight * sin;
		float y3 = halfWidth * sin + halfHeight * cos;
		float x4 = -halfWidth * cos - halfHeight * sin;
		float y4 = -halfWidth * sin + halfHeight * cos;
		
		x1 += x;
		y1 += y;
		x2 += x;
		y2 += y;
		x3 += x;
		y3 += y;
		x4 += x;
		y4 += y;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x1;
		this.VERTICES_BUFFER[this.bufferIndex++] = y1;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U1;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V2;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x2;
		this.VERTICES_BUFFER[this.bufferIndex++] = y2;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U2;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V2;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x3;
		this.VERTICES_BUFFER[this.bufferIndex++] = y3;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U2;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V1;
		
		this.VERTICES_BUFFER[this.bufferIndex++] = x4;
		this.VERTICES_BUFFER[this.bufferIndex++] = y4;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.U1;
		this.VERTICES_BUFFER[this.bufferIndex++] = region.V1;
		
		this.numSprites++;
	}
}