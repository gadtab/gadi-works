package il.co.gadiworks.glbasics;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class GLGameTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new TestScreen(this);
	}
	
	class TestScreen extends Screen{
		GLGraphics glGraphics;
		Random rand = new Random();
		
		public TestScreen(Game game) {
			super (game);
			this.glGraphics = ((GLGame) game).getGLGraphics();
		}
		
		@Override
		public void present(float deltaTime) {
			GL10 gl = this.glGraphics.getGL();
			gl.glClearColor(this.rand.nextFloat(), this.rand.nextFloat(), this.rand.nextFloat(), 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		}

		@Override
		public void update(float deltaTime) {
		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {
		}

		@Override
		public void dispose() {
		}
	}
}
