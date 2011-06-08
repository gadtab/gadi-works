package il.co.gadiworks.glbasics;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.FPSCounter;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.Vertices;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class BobTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new BobScreen(this);
	}
	
	class BobScreen extends Screen {
		static final int NUM_BOBS = 100;
		GLGraphics glGraphics;
		Texture bobTexture;
		Vertices bobModel;
		Bob[] bobs;
		FPSCounter fpsCounter;
		
		public BobScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame)game).getGLGraphics();
			
			this.bobTexture = new Texture((GLGame)game, "bobrgb888-32x32.png");
			
			this.bobModel = new Vertices(glGraphics, 4, 12, false, true);
			
			this.bobModel.setVertices(new float[] {
					-16, -16, 0, 1,
					 16, -16, 1, 1,
					 16,  16, 1, 0,
					-16,  16, 0, 0
			}, 0, 16);
			this.bobModel.setIndices(new short[] {
					0, 1, 2,
					2, 3, 0
			}, 0, 6);
			
			this.bobs = new Bob[NUM_BOBS];
			
			for (int i = 0; i < NUM_BOBS; i++) {
				this.bobs[i] = new Bob();
			}
			
			fpsCounter = new FPSCounter();
		}
		
		@Override
		public void update(float deltaTime) {
			GAME.getInput().getTouchEvents();
			GAME.getInput().getKeyEvents();
			
			for (int i = 0; i < NUM_BOBS; i++) {
				this.bobs[i].update(deltaTime);
			}
		}
		
		@Override
		public void resume() {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glViewport(0, 0, this.glGraphics.getWidth(), this.glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			
			this.bobTexture.reload();
			gl.glEnable(GL10.GL_TEXTURE_2D);
			this.bobTexture.bind();
			
		}
		
		@Override
		public void present(float deltaTime) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			this.bobModel.bind();
			for (int i = 0; i < NUM_BOBS; i++) {
				gl.glLoadIdentity();
				gl.glTranslatef(this.bobs[i].x, this.bobs[i].y, 0);
//				gl.glRotatef(45, 0, 0, 1);
//				gl.glScalef(2, 0.5f, 0);
				this.bobModel.draw(GL10.GL_TRIANGLES, 0, 6);
			}
			this.bobModel.unbind();
			
			fpsCounter.logFrame();
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