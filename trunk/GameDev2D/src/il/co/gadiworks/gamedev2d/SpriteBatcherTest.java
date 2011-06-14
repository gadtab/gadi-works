package il.co.gadiworks.gamedev2d;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.Input.TouchEvent;
import il.co.gadiworks.games.framework.gl.Camera2D;
import il.co.gadiworks.games.framework.gl.SpatialHashGrid;
import il.co.gadiworks.games.framework.gl.SpriteBatcher;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.TextureRegion;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;
import il.co.gadiworks.games.framework.math.OverlapTester;
import il.co.gadiworks.games.framework.math.Vector2;

import il.co.gadiworks.games.gamedev2d.DynamicGameObject;
import il.co.gadiworks.games.gamedev2d.GameObject;

public class SpriteBatcherTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new SpriteBatcherScreen(this);
	}
	
	class SpriteBatcherScreen extends Screen {
		final int NUM_TARGETS = 20;
		final float WORLD_WIDTH = 9.6f;
		final float WORLD_HEIGHT = 4.8f;
		
		GLGraphics glGraphics;
		Cannon cannon;
		DynamicGameObject ball;
		List<GameObject> targets;
		SpatialHashGrid grid;
		Camera2D camera;
		Texture texture;
		
		TextureRegion cannonRegion;
		TextureRegion ballRegion;
		TextureRegion bobRegion;
		SpriteBatcher batcher;
		
		Vector2 touchPos = new Vector2();
		Vector2 gravity = new Vector2(0, -10);

		public SpriteBatcherScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame) game).getGLGraphics();
			
			this.cannon = new Cannon(0, 0, 1, 0.5f);
			this.ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
			this.targets = new ArrayList<GameObject>(this.NUM_TARGETS);
			this.grid = new SpatialHashGrid(this.WORLD_WIDTH, this.WORLD_HEIGHT, 2.5f);
			this.camera = new Camera2D(this.glGraphics, this.WORLD_WIDTH, this.WORLD_HEIGHT);
			
			for (int i = 0; i < this.NUM_TARGETS; i++) {
				GameObject target = new GameObject((float)Math.random() * this.WORLD_WIDTH, (float)Math.random() * this.WORLD_HEIGHT, 0.5f, 0.5f);
				
				this.grid.insertStaticObject(target);
				this.targets.add(target);
			}
			
			this.batcher = new SpriteBatcher(this.glGraphics, 100);
		}
		
		@Override
		public void update(float deltaTime) {
			List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
			GAME.getInput().getKeyEvents();
			
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				
				this.camera.touchToWorld(this.touchPos.set(event.x, event.y));
				
				this.cannon.angle = this.touchPos.sub(this.cannon.POSITION).angle();
				
				if (event.type == TouchEvent.TOUCH_UP) {
					float radians = this.cannon.angle * Vector2.TO_RADIANS;
					float ballSpeed = this.touchPos.len() * 2;
					
					this.ball.POSITION.set(this.cannon.POSITION);
					
					this.ball.VELOCITY.x = FloatMath.cos(radians) * ballSpeed;
					this.ball.VELOCITY.y = FloatMath.sin(radians) * ballSpeed;
					this.ball.BOUNDS.LOWER_LEFT.set(this.ball.POSITION.x - 0.1f, this.ball.POSITION.y - 0.1f);
				}
			}
			
			this.ball.VELOCITY.add(this.gravity.x * deltaTime, this.gravity.y * deltaTime);
			this.ball.POSITION.add(this.ball.VELOCITY.x * deltaTime, this.ball.VELOCITY.y * deltaTime);
			this.ball.BOUNDS.LOWER_LEFT.add(this.ball.VELOCITY.x * deltaTime, this.ball.VELOCITY.y * deltaTime);
			
			List<GameObject> colliders = this.grid.getPotentialColliders(ball);
			len = colliders.size();
			for (int i = 0; i < len; i++) {
				GameObject collider = colliders.get(i);
				if (OverlapTester.overlapRectangles(this.ball.BOUNDS, collider.BOUNDS)) {
					this.grid.removeObject(collider);
					this.targets.remove(collider);
				}
			}
			
			if (this.ball.POSITION.y > 0) {
				// camera will follow the ball
				this.camera.POSITION.set(this.ball.POSITION);
				// camera will zoom out as the y coordinate of the ball position is higher
				this.camera.zoom = 1 + this.ball.POSITION.y / this.WORLD_HEIGHT;
			}
			else {
				this.camera.POSITION.set(this.WORLD_WIDTH / 2, this.WORLD_HEIGHT / 2);
				this.camera.zoom = 1;
			}
		}
		
		@Override
		public void present(float deltaTime) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			this.camera.setViewportAndMatrices();
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			this.batcher.beginBatch(this.texture);
			
			int len = this.targets.size();
			for (int i = 0; i < len; i++) {
				GameObject target = this.targets.get(i);
				this.batcher.drawSprite(target.POSITION.x, target.POSITION.y, 0.5f, 0.5f, this.bobRegion);
			}
			
			this.batcher.drawSprite(this.ball.POSITION.x, this.ball.POSITION.y, 0.2f, 0.2f, this.ballRegion);
			this.batcher.drawSprite(this.cannon.POSITION.x, this.cannon.POSITION.y, 1, 0.5f, this.cannon.angle, this.cannonRegion);
			
			this.batcher.endBatch();
		}
		
		@Override
		public void resume() {
			this.texture = new Texture(((GLGame) GAME), "atlas.png");
			this.cannonRegion = new TextureRegion(this.texture, 0, 0, 64, 32);
			this.ballRegion = new TextureRegion(this.texture, 0, 32, 16, 16);
			this.bobRegion = new TextureRegion(this.texture, 32, 32, 32, 32);
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