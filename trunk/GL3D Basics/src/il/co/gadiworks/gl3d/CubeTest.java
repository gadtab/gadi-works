package il.co.gadiworks.gl3d;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.Vertices3;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLScreen;

public class CubeTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new CubeScreen(this);
	}
	
	class CubeScreen extends GLScreen {
		Vertices3 cube;
		Texture texture;
		float angle = 0;

		public CubeScreen(Game game) {
			super(game);
			
			this.cube = createCube();
			this.texture = new Texture(GL_GAME, "crate.png");
		}
		
		private Vertices3 createCube() {
			float[] vertices = {
					-0.5f, -0.5f,  0.5f, 0, 1,
					 0.5f, -0.5f,  0.5f, 1, 1,
					 0.5f,  0.5f,  0.5f, 1, 0,
					-0.5f,  0.5f,  0.5f, 0, 0,
					
					 0.5f, -0.5f,  0.5f, 0, 1,
					 0.5f, -0.5f, -0.5f, 1, 1,
					 0.5f,  0.5f, -0.5f, 1, 0,
					 0.5f,  0.5f,  0.5f, 0, 0,
					 
					 0.5f, -0.5f, -0.5f, 0, 1,
					-0.5f, -0.5f, -0.5f, 1, 1,
					-0.5f,  0.5f, -0.5f, 1, 0,
					 0.5f,  0.5f, -0.5f, 0, 0,
					 
					-0.5f, -0.5f, -0.5f, 0, 1,
					-0.5f, -0.5f,  0.5f, 1, 1,
					-0.5f,  0.5f,  0.5f, 1, 0,
					-0.5f,  0.5f, -0.5f, 0, 0,
					
					-0.5f,  0.5f,  0.5f, 0, 1,
					 0.5f,  0.5f,  0.5f, 1, 1,
					 0.5f,  0.5f, -0.5f, 1, 0,
					-0.5f,  0.5f, -0.5f, 0, 0,
					
					-0.5f, -0.5f,  0.5f, 0,  1,
					 0.5f, -0.5f,  0.5f, 1,  1,
					 0.5f, -0.5f, -0.5f, 1, 0,
					-0.5f, -0.5f, -0.5f, 0, 0
			};
			
			short[] indices = {
					0, 1, 3, 1, 2, 3,
					4, 5, 7, 5, 6, 7,
					8, 9, 11, 9, 10, 11,
					12, 13, 15, 13, 14, 15,
					16, 17, 19, 17, 18, 19,
					20, 21, 23, 21, 22, 23
			};
			
			Vertices3 cube = new Vertices3(GL_GRAPHICS, 24, 36, false, true);
			
			cube.setVertices(vertices, 0, vertices.length);
			cube.setIndices(indices, 0, indices.length);
			
			return cube;
		}
		
		@Override
		public void resume() {
			this.texture.reload();
		}
		
		@Override
		public void update(float deltaTime) {
			this.angle += 45 * deltaTime;
		}

		@Override
		public void present(float deltaTime) {
			GL10 gl = GL_GRAPHICS.getGL();
			gl.glViewport(0, 0, GL_GRAPHICS.getWidth(), GL_GRAPHICS.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			GLU.gluPerspective(gl, 67, GL_GRAPHICS.getWidth() / (float)GL_GRAPHICS.getHeight(), 0.1f, 10f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			this.texture.bind();
			this.cube.bind();
			
			gl.glTranslatef(0, 0, -3);
			gl.glRotatef(this.angle, 0, 1, 0);
			
			this.cube.draw(GL10.GL_TRIANGLES, 0, 36);
			this.cube.unbind();
			
			gl.glDisable(GL10.GL_TEXTURE_2D);
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
	}
}