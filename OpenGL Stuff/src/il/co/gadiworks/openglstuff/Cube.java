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
	// The initial vertex definition
	private float vertices[] = {
		-1, -1, 0,
		 1, -1, 0,
		-1,  1, 0,
		 1,  1, 0
	};
	
	// The initial texture coordinates (u, v)
	private float[] texCoords = {
			0, 1,	// A. left-bottom
			1, 1,	// B. right-bottom
			0, 0,	// C. left-top
			1, 0	// D. right-top
	};
	
	// The texture pointer
	private int[] textureIDs = new int[3];
	
	// The initial normals for the lighting calculations 
	private float normals[] = {
			// Normals
			0.0f,  0.0f,  1.0f, 						
			0.0f,  0.0f, -1.0f, 
			0.0f,  1.0f,  0.0f, 
			0.0f, -1.0f,  0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f,
			
			0.0f, 0.0f, 1.0f, 
			0.0f, 0.0f, -1.0f, 
			0.0f, 1.0f, 0.0f, 
			0.0f, -1.0f, 0.0f
	};
	
	// The buffer holding the normals 
	private FloatBuffer normalBuffer;
		
	// Our vertex buffer.
	private FloatBuffer vertexBuffer, texBuffer;

	/**
	 * The Cube constructor.
	 * 
	 * Initiate the buffers.
	 */
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
	    
	    ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length * 4);
	    nbb.order(ByteOrder.nativeOrder());
		normalBuffer = nbb.asFloatBuffer();
		normalBuffer.put(normals);
		normalBuffer.position(0);
	}
	
	/**
	 * The object own drawing function.
	 * Called from the renderer to redraw this instance
	 * with possible changes in values.
	 * 
	 * @param gl - The GL Context
	 * @param filter - Which texture filter to be used
	 */
	public void draw(GL10 gl, int filter) {
		// Bind the texture according to the set texture filter
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[filter]);
		
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
	    
	    // Enable the normal state
	    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	    
	    // Define normal buffer
	    gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
		
		// back
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -1);
		gl.glRotatef(180, 0, 1, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// front
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// left
		gl.glPushMatrix();
		gl.glTranslatef(-1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();	
		
		// bottom
		gl.glPushMatrix();
		gl.glTranslatef(0, -1, 0);
		gl.glRotatef(90, 1, 0, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();			
		
		// top
		gl.glPushMatrix();
		gl.glTranslatef(0, 1, 0);
		gl.glRotatef(-90, 1, 0, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();	
		
		// right
		gl.glPushMatrix();
		gl.glTranslatef(1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// Disable the buffers.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	
	/**
	 * Load the textures
	 * 
	 * @param gl - The GL Context
	 * @param context - The Activity context
	 */
	public void loadTexture(GL10 gl, Context context) {

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		
	   // Construct an input stream to texture image "res\drawable\gadiworks.png"
	   InputStream istream = context.getResources().openRawResource(R.drawable.gadiworks);
	   Bitmap bitmap = null;
	   try {
	      // Read and decode input as bitmap
	      bitmap = BitmapFactory.decodeStream(istream);
	   } finally {
	      try {
	         istream.close();
	         istream = null;
	      } catch(IOException e) { }
	   }
	   
	   // Generate 3 texture-ID array
	   gl.glGenTextures(3, textureIDs, 0);

	   // Create Nearest Filtered Texture and bind it to texture 0
	   gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	   // Build Texture from loaded bitmap for the currently-bind texture ID
	   GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	   
	   // Create Linear Filtered Texture and bind it to texture 1
	   gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
	   // Build Texture from loaded bitmap for the currently-bind texture ID
	   GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	   
	   // Create mipmapped textures and bind it to texture 2
	   gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[2]);
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	   gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
	   // Build Texture from loaded bitmap for the currently-bind texture ID
	   GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	   
	   buildMipmap(gl, bitmap);
	   
	   // Clean up
	   bitmap.recycle();
	}
	
	/**
	 * A MipMap generation implementation.
	 * Scale the original bitmap down, always by factor two,
	 * and set it as new mipmap level.
	 * 
	 * @param gl - The GL Context
	 * @param bitmap - The bitmap to mipmap
	 */
	private void buildMipmap(GL10 gl, Bitmap bitmap) {
		int level  = 0;
		int height = bitmap.getHeight();
		int width  = bitmap.getWidth();
		
		Bitmap bitmap2 = null;
		
		while(height >= 1 || width >= 1) {
			//First of all, generate the texture from our bitmap and set it to the according level
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
			
			//
			if(height == 1 || width == 1) {
				break;
			}

			//Increase the mipmap level
			level++;

			height /= 2;
			width /= 2;
			
			bitmap2 = Bitmap.createScaledBitmap(bitmap, width, height, true);
			
			//Clean up
			bitmap.recycle();
			bitmap = bitmap2;
		}
	}
	
	public void rotate(GL10 gl, float angle, float x, float y, float z) {
		gl.glRotatef(angle, x, y, z);
	}
	
	public void moveTo(GL10 gl, float x, float y, float z) {
		gl.glTranslatef(x, y, z);
	}
}
