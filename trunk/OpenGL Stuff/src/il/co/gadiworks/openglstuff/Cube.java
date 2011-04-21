package il.co.gadiworks.openglstuff;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
//import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Cube {
//	public float move1;
	
	private float vertices[] = {
		-1, -1, 0,
		 1, -1, 0,
		-1,  1, 0,
		 1,  1, 0
	};
	
	private float[] texCoords = {
			0, 1,	// A. left-bottom
			1, 1,	// B. right-bottom
			0, 0,	// C. left-top
			1, 0	// D. right-top
	};
	
	private int[] textureIDs = new int[1]; 
		
//	private short[] indices = {0, 1, 2, 2, 1, 3};
		
	// Our vertex buffer.
	private FloatBuffer vertexBuffer, texBuffer;

	// Our index buffer.
//	private ShortBuffer indexBuffer;
		
	public Cube() {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		// Setup texture-coords-array buffer, in float. An float has 4 bytes (NEW)
	    ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
	    tbb.order(ByteOrder.nativeOrder());
	    texBuffer = tbb.asFloatBuffer();
	    texBuffer.put(texCoords);
	    texBuffer.position(0);
		
		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
//		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
//		ibb.order(ByteOrder.nativeOrder());
//		indexBuffer = ibb.asShortBuffer();
//		indexBuffer.put(indices);
//		indexBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		// Counter-clockwize winding.
		gl.glFrontFace(GL10.GL_CCW);
		
		// Enable face culling
		gl.glEnable(GL10.GL_CULL_FACE);
		
		// What face to remove with face culling.
		gl.glCullFace(GL10.GL_BACK);
		
		// Enable the vertices buffer for writing and to be used during rendering
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		
		// Enable texture-coords-array
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Define texture-coords buffer
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
		
		// back
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -1);
		gl.glRotatef(180, 0, 1, 0);
//		gl.glColor4f(1, 1, 0, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// front
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 1);
//		gl.glColor4f(0.5f, 0.5f, 0, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// left
		gl.glPushMatrix();
		gl.glTranslatef(-1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
//		gl.glColor4f(0, 1, 1, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();	
		
		// bottom
		gl.glPushMatrix();
		gl.glTranslatef(0, -1, 0);
		gl.glRotatef(90, 1, 0, 0);
//		gl.glColor4f(1, 0, 1, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();			
		
		// top
		gl.glPushMatrix();
		gl.glTranslatef(0, 1, 0);
		gl.glRotatef(-90, 1, 0, 0);
//		gl.glColor4f(0.5f, 0, 0.5f, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();	
		
		// right
		gl.glPushMatrix();
		gl.glTranslatef(1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
//		gl.glColor4f(0, 0.5f, 0.5f, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	
	// Load an image into GL texture
	public void loadTexture(GL10 gl, Context context) {
	   gl.glGenTextures(1, textureIDs, 0); // Generate texture-ID array

	   gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);   // Bind to texture ID
	   // Set up texture filters
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	  
	   // Construct an input stream to texture image "res\drawable\gadiworks.png"
	   InputStream istream = context.getResources().openRawResource(R.drawable.gadiworks);
	   Bitmap bitmap = null;
	   try {
	      // Read and decode input as bitmap
	      bitmap = BitmapFactory.decodeStream(istream);
	   } finally {
	      try {
	         istream.close();
	      } catch(IOException e) { }
	   }
	   // Build Texture from loaded bitmap for the currently-bind texture ID
	   GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	   bitmap.recycle();
	}
}
