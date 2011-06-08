package il.co.gadiworks.glbasics;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.Vertices;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class BlendingTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new BlendingScreen(this);
	}

	class BlendingScreen extends Screen {
		GLGraphics glGraphics;
		Vertices vertices;
		Texture textureRgb;
		Texture textureRgba;

		public BlendingScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame)game).getGLGraphics();
			
			this.textureRgb = new Texture((GLGame)game, "bobrgb888.png");
			this.textureRgba = new Texture((GLGame)game, "bobargb8888.png");
			
			this.vertices = new Vertices(glGraphics, 8, 12, true, true);
			
			float[] rects = new float[] {
					100, 100, 1, 1, 1, 0.5f, 0, 1,
					228, 100, 1, 1, 1, 0.5f, 1, 1,
					228, 228, 1, 1, 1, 0.5f, 1, 0,
					100, 228, 1, 1, 1, 0.5f, 0, 0,
					
					100, 300, 1, 1, 1, 1, 0, 1,
					228, 300, 1, 1, 1, 1, 1, 1,
					228, 428, 1, 1, 1, 1, 1, 0,
					100, 428, 1, 1, 1, 1, 0, 0
			};
			
			this.vertices.setVertices(rects, 0, rects.length);
			this.vertices.setIndices(new short[] {
					0, 1, 2,
					2, 3, 0,
					
					4, 5, 6,
					6, 7, 4
			}, 0, 12);
		}
		
		@Override
		public void present(float arg0) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glViewport(0, 0, this.glGraphics.getWidth(), this.glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			this.textureRgb.bind();
			this.vertices.draw(GL10.GL_TRIANGLES, 0, 6);
			
			this.textureRgba.bind();
			this.vertices.draw(GL10.GL_TRIANGLES, 6, 6);
		}
		
		@Override
		public void update(float arg0) {
			GAME.getInput().getTouchEvents();
			GAME.getInput().getKeyEvents();
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
	}
}