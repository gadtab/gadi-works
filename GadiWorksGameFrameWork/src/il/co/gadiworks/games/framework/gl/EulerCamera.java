package il.co.gadiworks.games.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.Matrix;

import il.co.gadiworks.games.framework.math.Vector3;

public class EulerCamera {
	final Vector3 POSITION = new Vector3();
	float yaw;
	float pitch;
	float fieldOfView;
	float aspectRatio;
	float near;
	float far;
	
	public EulerCamera(float fieldOfView, float aspectRatio, float near, float far) {
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.near = near;
		this.far = far;
	}
	
	public Vector3 getPosition() {
		return this.POSITION;
	}
	
	public float getYaw() {
		return this.yaw;
	}
	
	public float getPitch() {
		return this.pitch;
	}
	
	public void setAngles(float yaw, float pitch) {
		if (pitch < -90) {
			pitch = -90;
		}
		if (pitch > 90) {
			pitch = 90;
		}
		
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public void rotate(float yawInc, float pitchInc) {
		this.yaw += yawInc;
		this.pitch += pitchInc;
		
		if (this.pitch < -90) {
			this.pitch = -90;
		}
		if (this.pitch > 90) {
			this.pitch = 90;
		}
	}
	
	public void setMatrices(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, this.fieldOfView, this.aspectRatio, this.near, this.far);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-this.pitch, 1, 0, 0);
		gl.glRotatef(-this.yaw, 0, 1, 0);
		gl.glTranslatef(-this.POSITION.x, -this.POSITION.y, -this.POSITION.z);
	}
	
	final float[] MATRIX = new float[16];
	final float[] IN_VEC = {0, 0, -1, 1};
	final float[] OUT_VEC = new float[4];
	final Vector3 DIRECTION = new Vector3();
	
	public Vector3 getDirection() {
		Matrix.setIdentityM(this.MATRIX, 0);
		Matrix.rotateM(this.MATRIX, 0, this.yaw, 0, 1, 0);
		Matrix.rotateM(this.MATRIX, 0, this.pitch, 1, 0, 0);
		Matrix.multiplyMV(this.OUT_VEC, 0, this.MATRIX, 0, this.IN_VEC, 0);
		this.DIRECTION.set(this.OUT_VEC[0], this.OUT_VEC[1], this.OUT_VEC[2]);
		
		return this.DIRECTION;
	}
}