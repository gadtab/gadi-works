package il.co.gadiworks.openglstuff;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid {
	private float[] vertices = {
		  	 0, 2,  0,		// 0
		  	 1, 0, -1,		// 1
		  	-1, 0, -1,		// 2
		  	 1, 0,  1,		// 3
			-1, 0,  1		// 4
		};
	
	private byte[] indices =  {
			0, 1, 2,
			0, 4, 3, 
			0, 3, 1,
			0, 2, 4,
			2, 1, 4,
			4, 1, 3
		};
	
	private float[] colors = {
			 0,    1, 0,    1,
			 1,    0, 0.5f, 1,
			 0.5f, 0, 0.5f, 1,
			 1,    0, 1,    1,
			 0.5f, 0, 1,    1
		};
	
	// Our vertex buffer.
	private FloatBuffer vertexBuffer, colorBuffer;
	private ByteBuffer  indexBuffer;

	public Pyramid() {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.order(ByteOrder.nativeOrder());
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		// Counter-clockwize winding.
		gl.glFrontFace(GL10.GL_CCW);
		
		// Enable face culling
		gl.glEnable(GL10.GL_CULL_FACE);
		
		// What face to remove woth face culling.
		gl.glCullFace(GL10.GL_BACK);
		
		// Enable the vertices buffer for writing and to be used during rendering
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		
		// Enable the colors buffer to be used during rendering.
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// Point out the where the color buffer is.
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
		
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// Disable the colors buffer.
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	
	public void rotate(GL10 gl, float angle, float x, float y, float z) {
		gl.glRotatef(angle, x, y, z);
	}
	
	public void moveTo(GL10 gl, float x, float y, float z) {
		gl.glTranslatef(x, y, z);
	}
}
