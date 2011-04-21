package il.co.gadiworks.openglstuff;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer implements Renderer
{
	private Pyramid piramid;
	
	public OpenGLRenderer() {
		this.piramid = new Pyramid();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// Set the background to black (rgba).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Enable smooth shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enable depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	public void onDrawFrame(GL10 gl)
	{
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		
		// Translates 10 units into the screen.
		gl.glTranslatef(0, 0, -4); 
		
		for (int i = 0; i < 361; i++) {
			gl.glRotatef(i, 1, 0, 0);
			this.piramid.draw(gl);
		}
		
		for (int i = 0; i < 361; i++) {
			gl.glRotatef(i, 0, 1, 0);
			this.piramid.draw(gl);
		}
		
		for (int i = 0; i < 361; i++) {
			gl.glRotatef(i, 0, 0, 1);
			this.piramid.draw(gl);
		}
		
//		gl.glRotatef(45, 1, 0, 0);
//		gl.glRotatef(45, 0, 1, 0);
//		gl.glRotatef(45, 0, 0, 1);
		
//		this.piramid.draw(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix.
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix.
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window.
		GLU.gluPerspective(gl, 45.0f, (float)width/(float)height, 0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}
}
