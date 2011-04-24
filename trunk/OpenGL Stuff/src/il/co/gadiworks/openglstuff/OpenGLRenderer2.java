package il.co.gadiworks.openglstuff;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer2 implements Renderer
{
//	private Shape shape;
	private Cube cube;
//	private Pyramid pyramid;
	
	// Which texture filter
	private int filter = 2;
	
	// Is light enabled
//	private boolean light = true;
	
	/* 
	 * The initial light values for ambient and diffuse
	 * as well as the light position
	 */
	private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
	private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
	private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f};
	
	// The buffers for our light values
	private FloatBuffer lightAmbientBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer lightPositionBuffer;
	
	// The Activity Context
	private Context context;
	
	public float ry, rx, angleCube;//, anglePyramid;
	
	/**
	 * Instance the Cube object and set the Activity Context 
	 * handed over. Initiate the light buffers and set this 
	 * class as renderer for this now GLSurfaceView.
	 * 
	 * @param context - The Activity Context
	 */
	public OpenGLRenderer2(Context context) {
		this.context = context;
		
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightAmbientBuffer = byteBuf.asFloatBuffer();
		lightAmbientBuffer.put(lightAmbient);
		lightAmbientBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightDiffuseBuffer = byteBuf.asFloatBuffer();
		lightDiffuseBuffer.put(lightDiffuse);
		lightDiffuseBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPositionBuffer = byteBuf.asFloatBuffer();
		lightPositionBuffer.put(lightPosition);
		lightPositionBuffer.position(0);
		
//		this.shape = new Shape();
		this.cube = new Cube();
//		this.pyramid = new Pyramid();
	}
	
	/**
	 * The Surface is created/init()
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbientBuffer);		//Setup The Ambient Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer);		//Setup The Diffuse Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer);	//Position The Light
		gl.glEnable(GL10.GL_LIGHT0);											//Enable Light 0 
		
		//Disable dithering 
		gl.glDisable(GL10.GL_DITHER);
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
		gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture
		
		gl.glEnable(GL10.GL_LIGHTING); // Enable lightning
	}

	/**
	 * The drawing
	 */
	public void onDrawFrame(GL10 gl)
	{
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// Replace the current matrix with the identity matrix
//		gl.glLoadIdentity();
		
//		gl.glTranslatef(0, 0, -12);
		
//		gl.glRotatef(rx, 1, 0, 0);
//		gl.glRotatef(ry, 0, 1, 0);

		
//		this.pyramid.moveTo(gl, 0, 2.5f, -12);
//		this.pyramid.rotate(gl, anglePyramid, 1, 1, 0);
//		this.pyramid.draw(gl);
		
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		
		this.cube.moveTo(gl, 0, 0, -8);
		this.cube.rotate(gl, angleCube, 1, 1, 0);
		this.cube.draw(gl, filter);
		
//		this.shape.draw(gl);

		angleCube++;
//		anglePyramid--;
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		
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
