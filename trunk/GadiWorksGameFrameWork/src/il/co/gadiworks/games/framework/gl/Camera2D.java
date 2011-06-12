package il.co.gadiworks.games.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.impl.GLGraphics;
import il.co.gadiworks.games.framework.math.Vector2;

public class Camera2D {
	public final Vector2 POSITION;
	public float zoom;
	public final float FRUSTUM_WIDTH;
	public final float FRUSTUM_HEIGHT;
	final GLGraphics GL_GRAPHICS;
	
	public Camera2D(GLGraphics glGraphics, float frustumWidth, float frustumHeight) {
		this.GL_GRAPHICS = glGraphics;
		this.FRUSTUM_WIDTH = frustumWidth;
		this.FRUSTUM_HEIGHT = frustumHeight;
		this.POSITION = new Vector2(frustumWidth / 2, frustumHeight / 2);
		this.zoom = 1.0f;
	}
	
	public void setViewportAndMatrices() {
		GL10 gl = this.GL_GRAPHICS.getGL();
		
		gl.glViewport(0, 0, this.GL_GRAPHICS.getWidth(), this.GL_GRAPHICS.getHeight());
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(this.POSITION.x - this.FRUSTUM_WIDTH * this.zoom / 2, 
					this.POSITION.x + this.FRUSTUM_WIDTH * this.zoom / 2, 
					this.POSITION.y - this.FRUSTUM_HEIGHT * this.zoom / 2, 
					this.POSITION.y + this.FRUSTUM_HEIGHT * this.zoom / 2, 
					1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	public void touchToWorld(Vector2 touch) {
		touch.x = (touch.x / (float)this.GL_GRAPHICS.getWidth()) * this.FRUSTUM_WIDTH * this.zoom;
		touch.y = (1 - touch.y / (float)this.GL_GRAPHICS.getHeight()) * this.FRUSTUM_HEIGHT * this.zoom;
		touch.add(this.POSITION).sub(this.FRUSTUM_WIDTH * this.zoom / 2, this.FRUSTUM_HEIGHT * this.zoom / 2);
	}
}
