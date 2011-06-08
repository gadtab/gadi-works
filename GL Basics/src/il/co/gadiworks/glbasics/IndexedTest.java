package il.co.gadiworks.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class IndexedTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new IndexedScreen(this);
	}
	
	class IndexedScreen extends Screen {
		final int VERTEX_SIZE = (2 + 2) * 4;
		GLGraphics glGraphics;
		FloatBuffer vertices;
		ShortBuffer indices;
		Texture texture;

		public IndexedScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame)game).getGLGraphics();
			
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * this.VERTEX_SIZE);
			byteBuffer.order(ByteOrder.nativeOrder());
			this.vertices = byteBuffer.asFloatBuffer();
			this.vertices.put(new float[] {
					100.0f, 100.0f, 0.0f, 1.0f,
					228.0f, 100.0f, 1.0f, 1.0f,
					228.0f, 228.0f, 1.0f, 0.0f,
					100.0f, 228.0f, 0.0f, 0.0f
			});
			this.vertices.flip();
			
			byteBuffer = ByteBuffer.allocateDirect(6 * 2);
			byteBuffer.order(ByteOrder.nativeOrder());
			this.indices = byteBuffer.asShortBuffer();
			this.indices.put(new short[] {
					0, 1, 2,
					2, 3, 0
			});
			this.indices.flip();
			
			this.texture = new Texture((GLGame)game, "bobrgb888.png");
		}
		
		@Override
		public void present(float arg0) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glViewport(0, 0, this.glGraphics.getWidth(), this.glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			this.texture.bind();
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
			this.vertices.position(0);
			gl.glVertexPointer(2, GL10.GL_FLOAT, this.VERTEX_SIZE, this.vertices);
			this.vertices.position(2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, this.VERTEX_SIZE, this.vertices);
			
			gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indices);
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