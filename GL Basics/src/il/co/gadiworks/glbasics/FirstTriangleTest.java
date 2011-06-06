package il.co.gadiworks.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class FirstTriangleTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new FirstTriangleScreen(this);
	}
	
	class FirstTriangleScreen extends Screen {
		GLGraphics glGraphics;
		FloatBuffer vertices;

		public FirstTriangleScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame)game).getGLGraphics();
			
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
			byteBuffer.order(ByteOrder.nativeOrder());
			this.vertices = byteBuffer.asFloatBuffer();
			this.vertices.put(new float[] {
					0.0f,     0.0f,
					319.0f,   0.0f,
					160.0f, 479.0f
			});
			this.vertices.flip();
		}
		
		@Override
		public void present(float deltaTime) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glViewport(0, 0, this.glGraphics.getWidth(), this.glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glColor4f(1, 0, 0, 1);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, this.vertices);
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		}
		
		@Override
		public void update(float deltaTime) {
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