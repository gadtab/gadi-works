package il.co.gadiworks.games.framework.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.impl.GLGraphics;

public class Vertices {
	final GLGraphics glGraphics;
	final boolean hasColor;
	final boolean hasTexCoords;
	final int vertexSize;
	final FloatBuffer vertices;
	final ShortBuffer indices;
	
	public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndices, 
			boolean hasColor, boolean hasTexCoords) {
		this.glGraphics = glGraphics;
		this.hasColor = hasColor;
		this.hasTexCoords = hasTexCoords;
		this.vertexSize = (2 + (this.hasColor ? 4 : 0) + (this.hasTexCoords ? 2 : 0)) * 4;
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * this.vertexSize);
		buffer.order(ByteOrder.nativeOrder());
		this.vertices = buffer.asFloatBuffer();
		
		if (maxIndices > 0) {
			buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE / 8);
			buffer.order(ByteOrder.nativeOrder());
			this.indices = buffer.asShortBuffer();
		}
		else {
			this.indices = null;
		}
	}
	
	public void setVertices(float[] vertices, int offset, int length) {
		this.vertices.clear();
		this.vertices.put(vertices, offset, length);
		this.vertices.flip();
	}
	
	public void setIndices(short[] indices, int offset, int length) {
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}
	
	public void bind() {
		GL10 gl = this.glGraphics.getGL();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		this.vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, this.vertexSize, this.vertices);
		
		if (this.hasColor) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			this.vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, this.vertexSize, this.vertices);
		}
		
		if (this.hasTexCoords) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			this.vertices.position(this.hasColor ? 6 : 2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, this.vertexSize, this.vertices);
		}
	}
	
	public void draw(int primitiveType, int offset, int numVertices) {
		GL10 gl = this.glGraphics.getGL();
		
		if (this.indices != null) {
			this.indices.position(offset);
			gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
		}
		else {
			gl.glDrawArrays(primitiveType, offset, numVertices);
		}
	}
	
	public void unbind() {
		GL10 gl = this.glGraphics.getGL();
		
		if (this.hasTexCoords) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		
		if (this.hasColor) {
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
	}
}
