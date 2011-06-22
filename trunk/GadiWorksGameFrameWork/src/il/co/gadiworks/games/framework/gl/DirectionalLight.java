package il.co.gadiworks.games.framework.gl;

import javax.microedition.khronos.opengles.GL10;

public class DirectionalLight {
	float[] ambient = {0.2f, 0.2f, 0.2f, 1.0f};
	float[] diffuse = {1.0f, 1.0f, 1.0f, 1.0f};
	float[] specular = {0.0f, 0.0f, 0.0f, 1.0f};
	float[] direction = {0, 0, -1, 0};
	int lastLightId = 0;
	
	public void setAmbient(float r, float g, float b, float a) {
		this.ambient[0] = r;
		this.ambient[1] = g;
		this.ambient[2] = b;
		this.ambient[3] = a;
	}
	
	public void setDiffuse(float r, float g, float b, float a) {
		this.diffuse[0] = r;
		this.diffuse[1] = g;
		this.diffuse[2] = b;
		this.diffuse[3] = a;
	}
	
	public void setSpecular(float r, float g, float b, float a) {
		this.specular[0] = r;
		this.specular[1] = g;
		this.specular[2] = b;
		this.specular[3] = a;
	}
	
	public void setDirection(float x, float y, float z) {
		this.direction[0] = -x;
		this.direction[1] = -y;
		this.direction[2] = -z;
	}
	
	public void enable(GL10 gl, int lightId) {
		gl.glEnable(lightId);
		
		gl.glLightfv(lightId, GL10.GL_AMBIENT, this.ambient, 0);
		gl.glLightfv(lightId, GL10.GL_DIFFUSE, this.diffuse, 0);
		gl.glLightfv(lightId, GL10.GL_SPECULAR, this.specular, 0);
		gl.glLightfv(lightId, GL10.GL_POSITION, this.direction, 0);
		
		this.lastLightId = lightId;
	}
	
	public void disable(GL10 gl) {
		gl.glDisable(this.lastLightId);
	}
}
