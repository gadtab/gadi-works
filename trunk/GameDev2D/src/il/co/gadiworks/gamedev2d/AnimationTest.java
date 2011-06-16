package il.co.gadiworks.gamedev2d;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.Animation;
import il.co.gadiworks.games.framework.gl.Camera2D;
import il.co.gadiworks.games.framework.gl.SpriteBatcher;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.TextureRegion;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;
import il.co.gadiworks.games.framework.DynamicGameObject;

public class AnimationTest extends GLGame {
	static final float WORLD_WIDTH = 4.8f;
	static final float WORLD_HEIGHT = 3.2f;
	
	static class Caveman extends DynamicGameObject {
		public float walkingTime = 0;
		
		public Caveman(float x, float y, float width, float height) {
			super(x, y, width, height);
			
			this.POSITION.set((float) Math.random() * WORLD_WIDTH, (float) Math.random() * WORLD_HEIGHT);
			this.VELOCITY.set(Math.random() > 0.5f ? -0.5f : 0.5f, 0);
			
			this.walkingTime = (float) Math.random() * 10;
		}
		
		public void update(float deltaTime) {
			this.POSITION.add(this.VELOCITY.x * deltaTime, this.VELOCITY.y * deltaTime);
			
			if (this.POSITION.x < 0) {
				this.POSITION.x  = WORLD_WIDTH;
			}
			if (this.POSITION.x > WORLD_WIDTH) {
				this.POSITION.x = 0;
			}
			
			this.walkingTime += deltaTime;
		}
	}

	@Override
	public Screen getStartScreen() {
		return new AnimationScreen(this);
	}
	
	class AnimationScreen extends Screen {
		static final int NUM_CAVEMEN = 10;
		
		GLGraphics glGraphics;
		Caveman[] cavemen;
		SpriteBatcher batcher;
		Camera2D camera;
		Texture texture;
		Animation walkAnim;

		public AnimationScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame) game).getGLGraphics();
			
			this.cavemen = new Caveman[NUM_CAVEMEN];
			for (int i = 0; i < NUM_CAVEMEN; i++) {
				this.cavemen[i] = new Caveman((float) Math.random(), (float) Math.random(), 1, 1);
			}
			
			this.batcher = new SpriteBatcher(this.glGraphics, NUM_CAVEMEN);
			this.camera = new Camera2D(this.glGraphics, AnimationTest.WORLD_WIDTH, AnimationTest.WORLD_HEIGHT);
		}
		
		@Override
		public void resume() {
			this.texture = new Texture(((GLGame) GAME), "walkanim.png");
			this.walkAnim = new Animation(0.2f, 
										  new TextureRegion(this.texture, 0, 0, 64, 64),
										  new TextureRegion(this.texture, 64, 0, 64, 64),
										  new TextureRegion(this.texture, 128, 0, 64, 64),
									      new TextureRegion(this.texture, 192, 0, 64, 64));
		}
		
		@Override
		public void update(float deltaTime) {
			int len = this.cavemen.length;
			for (int i = 0; i < len; i++) {
				this.cavemen[i].update(deltaTime);
			}
		}
		
		@Override
		public void present(float arg0) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			this.camera.setViewportAndMatrices();
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			this.batcher.beginBatch(this.texture);
			
			int len = this.cavemen.length;
			for (int i = 0; i < len; i++) {
				Caveman caveman = this.cavemen[i];
				TextureRegion keyFrame = this.walkAnim.getKeyFrame(caveman.walkingTime, Animation.ANIMATION_LOOPING);
				this.batcher.drawSprite(caveman.POSITION.x, caveman.POSITION.y, caveman.VELOCITY.x < 0 ? 1 : -1, 1, keyFrame);
			}
			
			this.batcher.endBatch();
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