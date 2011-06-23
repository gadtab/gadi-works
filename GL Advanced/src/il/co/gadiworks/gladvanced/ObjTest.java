package il.co.gadiworks.gladvanced;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.Camera2D;
import il.co.gadiworks.games.framework.gl.EulerCamera;
import il.co.gadiworks.games.framework.gl.ObjLoader;
import il.co.gadiworks.games.framework.gl.PointLight;
import il.co.gadiworks.games.framework.gl.SpriteBatcher;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.TextureRegion;
import il.co.gadiworks.games.framework.gl.Vertices3;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLScreen;
import il.co.gadiworks.games.framework.math.Vector2;
import il.co.gadiworks.games.framework.math.Vector3;

public class ObjTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new ObjScreen(this);
	}
	
	class ObjScreen extends GLScreen {
		Texture crateTexture;
		Vertices3 cube;
		PointLight light;
		EulerCamera camera;
		Texture buttonTexture;
		SpriteBatcher batcher;
		Camera2D guiCamera;
		TextureRegion buttonRegion;
		Vector2 touchPos;
		float lastX = -1;
		float lastY = -1;

		public ObjScreen(Game game) {
			super(game);
			
			this.crateTexture = new Texture(GL_GAME, "crate.png", true);
			this.cube = ObjLoader.load(GL_GAME, "cube.obj");
			this.light = new PointLight();
			this.light.setPosition(3, 3, -3);
			this.camera = new EulerCamera(67, GL_GRAPHICS.getWidth() / (float)GL_GRAPHICS.getHeight(), 1, 100);
			this.camera.getPosition().set(0, 1, 3);
			
			this.buttonTexture = new Texture(GL_GAME, "button.png");
			this.batcher = new SpriteBatcher(GL_GRAPHICS, 1);
			this.guiCamera = new Camera2D(GL_GRAPHICS, 480, 320);
			this.buttonRegion = new TextureRegion(this.buttonTexture, 0, 0, 64, 64);
			
			this.touchPos = new Vector2();
		}
		
		@Override
		public void resume() {
			this.crateTexture.reload();
		}
		
		@Override
		public void update(float deltaTime) {
			GAME.getInput().getTouchEvents();
			float x = GAME.getInput().getTouchX(0);
			float y = GAME.getInput().getTouchY(0);
			this.guiCamera.touchToWorld(this.touchPos.set(x, y));
			
			if (GAME.getInput().isTouchDown(0)) {
				if (this.touchPos.x < 64 && this.touchPos.y < 64) {
					Vector3 direction = this.camera.getDirection();
					this.camera.getPosition().add(direction).mul(deltaTime);
				}
				else {
					if (this.lastX == -1) {
						this.lastX = x;
						this.lastY = y;
					}
					else {
						this.camera.rotate((x - this.lastX) / 10, (y - this.lastY) / 10);
						this.lastX = x;
						this.lastY = y;
					}
				}
			}
			else {
				this.lastX = -1;
				this.lastY = -1;
			}
		}
		
		@Override
		public void present(float deltaTime) {
			GL10 gl = GL_GRAPHICS.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glViewport(0, 0, GL_GRAPHICS.getWidth(), GL_GRAPHICS.getHeight());
			
			this.camera.setMatrices(gl);
			
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnable(GL10.GL_LIGHTING);
			
			this.crateTexture.bind();
			this.cube.bind();
			this.light.enable(gl, GL10.GL_LIGHT0);
			
			for (int z = 0; z >= -8; z -= 2) {
				for (int x = -4; x <= 4; x += 2) {
					gl.glPushMatrix();
					gl.glTranslatef(x, 0, z);
					this.cube.draw(GL10.GL_TRIANGLES, 0, 6 * 2 * 3);
					gl.glPopMatrix();
				}
			}
			
			this.cube.unbind();
			
			gl.glDisable(GL10.GL_LIGHTING);
			gl.glDisable(GL10.GL_DEPTH_TEST);
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			this.guiCamera.setViewportAndMatrices();
			this.batcher.beginBatch(this.buttonTexture);
			this.batcher.drawSprite(32, 32, 64, 64, this.buttonRegion);
			this.batcher.endBatch();
			
			gl.glDisable(GL10.GL_BLEND);
			gl.glDisable(GL10.GL_TEXTURE_2D);
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