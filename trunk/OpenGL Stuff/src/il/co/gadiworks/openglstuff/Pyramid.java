package il.co.gadiworks.openglstuff;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid {
	private float vertices[] = {
		-0.5f, 0, 0.5f,
		0.5f, 0, 0.5f,
		0, 0.5f, 0,
		0, 0, -0.5f
	};
	
	private short[] indices = {0, 1, 2, 2, 1, 3, 3, 0, 2, 3, 1, 0};
	
	// Our vertex buffer.
	private FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;
	
	// Our color buffer.
	private FloatBuffer colorBuffer;
	
	// The Colors
//	private float[][] colors = {
//            {0.6f, 0.2f, 0.1f, 1f}, // vertex 0 
//            {0.1f, 1f, 0.3f, 1f}, // vertex 1 
//            {0.5f, 0.3f, 1f, 1f}, // vertex 2 
//            {1f, 0.4f, 0.2f, 1f} // vertex 3 
//	};
	private float[] colors = {
            0.3f, 0.8f, 0.1f, 1f, // vertex 0 
            0.1f, 1f, 0.3f, 1f, // vertex 1 
            0.2f, 0.7f, 0.1f, 1f, // vertex 2 
            0.1f, 0.9f, 0.2f, 1f, // vertex 3 
    };
	
//	private int numFaces = 4;
	
	public Pyramid() {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		// float has 4 bytes, colors (RGBA) * 4 bytes
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
		
		// Enable the color array buffer to be used during rendering.
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY); 
		// Point out the where the color buffer is.
		gl.glColorPointer(4, GL10.GL_FLOAT, 50, colorBuffer); 
		
		// Render all the faces
//	      for (int face = 0; face < numFaces; face++) {
//	         // Set the color for each of the faces
//	         gl.glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3]);
//	         // Draw the primitive from the vertex-array directly
//	         gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*3, 3);
//	      }
		
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// Disable the color buffer.
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
