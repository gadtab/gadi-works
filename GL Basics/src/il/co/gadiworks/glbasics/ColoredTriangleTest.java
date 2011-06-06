package il.co.gadiworks.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class ColoredTriangleTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new ColoredTriangleScreen(this);
	}

	class ColoredTriangleScreen extends Screen {
		final int VERTEX_SIZE = (2 + 4) * 4;
		GLGraphics glGraphics;
		FloatBuffer vertices;

		public ColoredTriangleScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame)game).getGLGraphics();
			
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * this.VERTEX_SIZE);
			byteBuffer.order(ByteOrder.nativeOrder());
			this.vertices = byteBuffer.asFloatBuffer();
			this.vertices.put(new float[] {
					0.0f,     0.0f, 1, 0, 0, 1,
					319.0f,   0.0f, 0, 1, 0, 1,
					160.0f, 479.0f, 0, 0, 1, 1
			});
			this.vertices.flip();
		}
		
		@Override
		public void present(float arg0) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glViewport(0, 0, this.glGraphics.getWidth(), this.glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			
			this.vertices.position(0);
			gl.glVertexPointer(2, GL10.GL_FLOAT, this.VERTEX_SIZE, this.vertices);
			this.vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, this.VERTEX_SIZE, this.vertices);
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
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