package il.co.gadiworks.games.framework.math;

import android.opengl.Matrix;
import android.util.FloatMath;

public class Vector3 {
	private static final float[] MATRIX = new float[16];
	private static final float[] IN_VEC = new float[4];
	private static final float[] OUT_VEC = new float[4];
	
	public float x, y, z;
	
	public Vector3() {
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}
	
	public Vector3 cpy() {
		return new Vector3(this.x, this.y, this.z);
	}
	
	public Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public Vector3 set(Vector3 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		
		return this;
	}
	
	public Vector3 add (float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		
		return this;
	}
	
	public Vector3 add (Vector3 other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		
		return this;
	}
	
	public Vector3 sub (float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		
		return this;
	}
	
	public Vector3 sub (Vector3 other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		
		return this;
	}
	
	public Vector3 mul(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		
		return this;
	}
	
	public float len() {
		return FloatMath.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	public Vector3 nor() {
		float len = len();
		if (len != 0) {
			this.x /= len;
			this.y /= len;
			this.z /= len;
		}
		
		return this;
	}
	
	public Vector3 rotate(float angle, float axisX, float axisY, float axisZ) {
		IN_VEC[0] = this.x;
		IN_VEC[1] = this.y;
		IN_VEC[2] = this.z;
		IN_VEC[3] = 1;
		
		Matrix.setIdentityM(MATRIX, 0);
		Matrix.rotateM(MATRIX, 0, angle, axisX, axisY, axisZ);
		Matrix.multiplyMV(OUT_VEC, 0, MATRIX, 0, IN_VEC, 0);
		
		this.x = OUT_VEC[0];
		this.y = OUT_VEC[1];
		this.z = OUT_VEC[2];
		
		return this;
	}
	
	public float dist(Vector3 other) {
		float distX = this.x - other.x;
		float distY = this.y - other.y;
		float distZ = this.z - other.z;
		
		return FloatMath.sqrt(distX * distX + distY * distY + distZ * distZ); 
	}
	
	public float dist(float x, float y, float z) {
		float distX = this.x - x;
		float distY = this.y - y;
		float distZ = this.z - z;
		
		return FloatMath.sqrt(distX * distX + distY * distY + distZ * distZ); 
	}
	
	public float distSquared(Vector3 other) {
		float distX = this.x - other.x;
		float distY = this.y - other.y;
		float distZ = this.z - other.z;
		
		return distX * distX + distY * distY + distZ * distZ;
	}
	
	public float distSquared(float x, float y, float z) {
		float distX = this.x - x;
		float distY = this.y - y;
		float distZ = this.z - z;
		
		return distX * distX + distY * distY + distZ * distZ;
	}
}