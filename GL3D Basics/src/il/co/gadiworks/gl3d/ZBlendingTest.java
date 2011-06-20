package il.co.gadiworks.gl3d;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.Vertices3;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLScreen;

public class ZBlendingTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new ZBlendingScreen(this);
	}
	
	class ZBlendingScreen extends GLScreen {
		Vertices3 vertices;

		public ZBlendingScreen(Game game) {
			super(game);
			
			this.vertices = new Vertices3(GL_GRAPHICS, 6, 0, true, false);
			this.vertices.setVertices(new float[] {
					-0.5f, -0.5f, -3, 1, 0, 0, 0.5f,
					 0.5f, -0.5f, -3, 1, 0, 0, 0.5f,
					 0.0f,  0.5f, -3, 1, 0, 0, 0.5f,
					 
					 0.0f, -0.5f, -5, 0, 1, 0, 1,
					 1.0f, -0.5f, -5, 0, 1, 0, 1,
					 0.5f,  0.5f, -5, 0, 1, 0, 1
			}, 0, 7 * 6);
		}
		
		@Override
		public void present(float deltaTime) {
			GL10 gl = GL_GRAPHICS.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glViewport(0, 0, GL_GRAPHICS.getWidth(), GL_GRAPHICS.getHeight());
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			GLU.gluPerspective(gl, 67, GL_GRAPHICS.getWidth() / (float)GL_GRAPHICS.getHeight(), 0.1f, 10f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			this.vertices.bind();
			this.vertices.draw(GL10.GL_TRIANGLES, 3, 3);
			this.vertices.draw(GL10.GL_TRIANGLES, 0, 3);
			this.vertices.unbind();
			
			gl.glDisable(GL10.GL_BLEND);
			gl.glDisable(GL10.GL_DEPTH_TEST);
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub
			
		}
	}
}