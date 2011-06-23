package il.co.gadiworks.games.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import il.co.gadiworks.games.framework.math.Vector3;

public class LookAtCamera {
	final Vector3 POSITION;
	final Vector3 UP;
	final Vector3 LOOK_AT;
	float fieldOfView;
	float aspectRatio;
	float near;
	float far;
	
	public LookAtCamera(float fieldOfView, float aspectRatio, float near, float far) {
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.near = near;
		this.far = far;
		
		this.POSITION = new Vector3();
		this.UP = new Vector3(0, 1, 0);
		this.LOOK_AT = new Vector3(0, 0, -1);
	}
	
	public Vector3 getPosition() {
		return this.POSITION;
	}
	
	public Vector3 getUp() {
		return this.UP;
	}
	
	public Vector3 getLookAt() {
		return this.LOOK_AT;
	}
	
	public void setMatrices(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, this.fieldOfView, this.aspectRatio, this.near, this.far);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, this.POSITION.x, this.POSITION.y, this.POSITION.z, 
						  this.LOOK_AT.x, this.LOOK_AT.y, this.LOOK_AT.z, 
						  this.UP.x, this.UP.y, this.UP.z);
	}
}