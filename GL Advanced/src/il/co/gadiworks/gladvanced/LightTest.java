package il.co.gadiworks.gladvanced;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.AmbientLight;
import il.co.gadiworks.games.framework.gl.DirectionalLight;
import il.co.gadiworks.games.framework.gl.Material;
import il.co.gadiworks.games.framework.gl.PointLight;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.Vertices3;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLScreen;

public class LightTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new LightScreen(this);
	}
	
	class LightScreen extends GLScreen {
		float angle;
		Vertices3 cube;
		Texture texture;
		AmbientLight ambientLight;
		PointLight pointLight;
		DirectionalLight directionalLight;
		Material material;

		public LightScreen(Game game) {
			super(game);
			
			this.cube = createCube();
			this.texture = new Texture(GL_GAME, "crate.png");
			this.ambientLight = new AmbientLight();
			this.ambientLight.setColor(0, 0.2f, 0, 1);
			this.pointLight = new PointLight();
			this.pointLight.setDiffuse(1, 0, 0, 1);
			this.pointLight.setPosition(3, 3, 0);
			this.directionalLight = new DirectionalLight();
			this.directionalLight.setDiffuse(0, 0, 1, 1);
			this.directionalLight.setDirection(1, 0, 0);
			this.material = new Material();
		}
		
		@Override
		public void resume() {
			this.texture.reload();
		}
		
		private Vertices3 createCube() {
			float[] vertices = {
					-0.5f, -0.5f,  0.5f, 0, 1, 0, 0, 1,
					 0.5f, -0.5f,  0.5f, 1, 1, 0, 0, 1,
					 0.5f,  0.5f,  0.5f, 1, 0, 0, 0, 1,
					-0.5f,  0.5f,  0.5f, 0, 0, 0, 0, 1,
					
					 0.5f, -0.5f,  0.5f, 0, 1, 1, 0, 0,
					 0.5f, -0.5f, -0.5f, 1, 1, 1, 0, 0,
					 0.5f,  0.5f, -0.5f, 1, 0, 1, 0, 0,
					 0.5f,  0.5f,  0.5f, 0, 0, 1, 0, 0,
					 
					 0.5f, -0.5f, -0.5f, 0, 1, 0, 0, -1,
					-0.5f, -0.5f, -0.5f, 1, 1, 0, 0, -1,
					-0.5f,  0.5f, -0.5f, 1, 0, 0, 0, -1,
					 0.5f,  0.5f, -0.5f, 0, 0, 0, 0, -1,
					 
					-0.5f, -0.5f, -0.5f, 0, 1, -1, 0, 0,
					-0.5f, -0.5f,  0.5f, 1, 1, -1, 0, 0,
					-0.5f,  0.5f,  0.5f, 1, 0, -1, 0, 0,
					-0.5f,  0.5f, -0.5f, 0, 0, -1, 0, 0,
					
					-0.5f,  0.5f,  0.5f, 0, 1, 0, 1, 0,
					 0.5f,  0.5f,  0.5f, 1, 1, 0, 1, 0,
					 0.5f,  0.5f, -0.5f, 1, 0, 0, 1, 0,
					-0.5f,  0.5f, -0.5f, 0, 0, 0, 1, 0,
					
					-0.5f, -0.5f,  0.5f, 0, 1, 0, -1, 0,
					 0.5f, -0.5f,  0.5f, 1, 1, 0, -1, 0,
					 0.5f, -0.5f, -0.5f, 1, 0, 0, -1, 0,
					-0.5f, -0.5f, -0.5f, 0, 0, 0, -1, 0
			};
			
			short[] indices = {
					0, 1, 2, 2, 3, 0,
					4, 5, 6, 6, 7, 4,
					8, 9, 10, 10, 11, 8,
					12, 13, 14, 14, 15, 12,
					16, 17, 18, 18, 19, 16,
					20, 21, 22, 22, 23, 20,
					24, 25, 26, 26, 27, 24
			};
			
			Vertices3 cube = new Vertices3(GL_GRAPHICS, vertices.length / 8, indices.length, false, true, true);
			
			cube.setVertices(vertices, 0, vertices.length);
			cube.setIndices(indices, 0, indices.length);
			
			return cube;
		}
		
		@Override
		public void update(float deltaTime) {
			this.angle += deltaTime * 20;
		}
		
		@Override
		public void present(float deltaTime) {
			GL10 gl = GL_GRAPHICS.getGL();
			gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glViewport(0, 0, GL_GRAPHICS.getWidth(), GL_GRAPHICS.getHeight());
			
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			GLU.gluPerspective(gl, 67, GL_GRAPHICS.getWidth() / (float)GL_GRAPHICS.getHeight(), 0.1f, 10f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			GLU.gluLookAt(gl, 0, 1, 3, 0, 0, 0, 0, 1, 0);
			
			gl.glEnable(GL10.GL_LIGHTING);
			
			this.ambientLight.enable(gl);
			this.pointLight.enable(gl, GL10.GL_LIGHT0);
			this.directionalLight.enable(gl, GL10.GL_LIGHT1);
			this.material.enable(gl);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			this.texture.bind();
			
			gl.glRotatef(this.angle, 0, 1, 0);
			this.cube.bind();
			this.cube.draw(GL10.GL_TRIANGLES, 0, 6 * 2 * 3);
			this.cube.unbind();
			
			this.pointLight.disable(gl);
			this.directionalLight.disable(gl);
			
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