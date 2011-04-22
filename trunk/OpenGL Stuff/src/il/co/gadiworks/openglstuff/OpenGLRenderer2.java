package il.co.gadiworks.openglstuff;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer2 implements Renderer
{
//	private Shape shape;
	private Cube cube;
	private Pyramid pyramid;
	
	private Context context;
	
	public float ry, rx, angleCube, anglePyramid;
	
	public OpenGLRenderer2(Context context) {
		this.context = context;
		
//		this.shape = new Shape();
		this.cube = new Cube();
		this.pyramid = new Pyramid();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// Set the background to yellow (rgba).
		gl.glClearColor(0.941f, 0.937f, 0.533f, 0.5f);
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
		
		// Setup texture, each time the surface is created
		cube.loadTexture(gl, context);    // Load images into Texture
//		gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture
	}

	public void onDrawFrame(GL10 gl)
	{
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		
//		gl.glTranslatef(0, 0, -12);
		
//		gl.glRotatef(rx, 1, 0, 0);
//		gl.glRotatef(ry, 0, 1, 0);

		
		this.pyramid.moveTo(gl, 0, 2.5f, -12);
		this.pyramid.rotate(gl, anglePyramid, 1, 1, 0);
		this.pyramid.draw(gl);
		
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		
		this.cube.moveTo(gl, 0, -1, -12);
		this.cube.rotate(gl, angleCube, 1, 1, 0);
		this.cube.draw(gl);
		
//		this.shape.draw(gl);

		angleCube++;
		anglePyramid--;
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
