package il.co.gadiworks.openglstuff;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid {
	private float vertices[] = {
			-1, 0, 1,
			 1, 0, 1,
		     0, 2, 0,
		};
	
	private float baseVertices[] = {
			 1, 0,  1,
			-1, 0,  1,
			 1, 0, -1,
			-1, 0, -1
		};
	
	// Our vertex buffer.
	private FloatBuffer vertexBuffer, baseVertexBuffer;

	public Pyramid() {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		ByteBuffer vbb2 = ByteBuffer.allocateDirect(baseVertices.length * 4);
		vbb2.order(ByteOrder.nativeOrder());
		baseVertexBuffer = vbb2.asFloatBuffer();
		baseVertexBuffer.put(baseVertices);
		baseVertexBuffer.position(0);
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
		
		// front
		gl.glPushMatrix();
		gl.glColor4f(0, 1, 0, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
		gl.glPopMatrix();
		
		// back
		gl.glPushMatrix();
		gl.glRotatef(180, 0, 1, 0);
		gl.glColor4f(0, 0.5f, 0, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
		gl.glPopMatrix();
		
		// left
		gl.glPushMatrix();
		gl.glRotatef(90, 0, 1, 0);
		gl.glColor4f(1, 0, 0, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
		gl.glPopMatrix();
		
		// right
		gl.glPushMatrix();
		gl.glRotatef(-90, 0, 1, 0);
		gl.glColor4f(0.5f, 0, 0, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, baseVertexBuffer);
		gl.glColor4f(0f, 0, 1, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
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
