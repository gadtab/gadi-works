package il.co.gadiworks.jumper;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.gl.Animation;
import il.co.gadiworks.games.framework.gl.Camera2D;
import il.co.gadiworks.games.framework.gl.SpriteBatcher;
import il.co.gadiworks.games.framework.gl.TextureRegion;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;
	
	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}
	
	public void render() {
		if (this.world.BOB.POSITION.y > this.cam.POSITION.y) {
			this.cam.POSITION.y = this.world.BOB.POSITION.y;
		}
		
		this.cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}

	private void renderObjects() {
		GL10 gl = this.glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		this.batcher.beginBatch(Assets.items);
		renderBob();
		renderPlatforms();
		renderItems();
		renderSquirrels();
		renderCastle();
		this.batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	private void renderCastle() {
		Castle castle = this.world.castle;
		this.batcher.drawSprite(castle.POSITION.x, castle.POSITION.y, 2, 2, Assets.castle);
	}

	private void renderSquirrels() {
		int len = this.world.SQUIRRELS.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = this.world.SQUIRRELS.get(i);
			TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime, Animation.ANIMATION_LOOPING);
			float side = squirrel.VELOCITY.x < 0 ? -1 : 1;
			this.batcher.drawSprite(squirrel.POSITION.x, squirrel.POSITION.y, side * 1, 1, keyFrame);
		}
	}

	private void renderItems() {
		int len = this.world.SPRINGS.size();
		for (int i = 0; i < len; i++) {
			Spring spring = this.world.SPRINGS.get(i);
			this.batcher.drawSprite(spring.POSITION.x, spring.POSITION.y, 1, 1, Assets.spring);
		}
		
		len = this.world.COINS.size();
		for (int i = 0; i < len; i++) {
			Coin coin = this.world.COINS.get(i);
			TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
			this.batcher.drawSprite(coin.POSITION.x, coin.POSITION.y, 1, 1, keyFrame);
		}
	}

	private void renderPlatforms() {
		int len = this.world.PLATFORMS.size();
		for (int i = 0; i < len; i++) {
			Platform platform = this.world.PLATFORMS.get(i);
			TextureRegion keyFrame = Assets.platform;
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
				keyFrame = Assets.breakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_LOOPING);
			}
			this.batcher.drawSprite(platform.POSITION.x, platform.POSITION.y, 2, 0.5f, keyFrame);
		}
	}

	private void renderBob() {
		TextureRegion keyFrame;
		switch(this.world.BOB.state) {
		case Bob.BOB_STATE_FALL:
			keyFrame = Assets.bobFall.getKeyFrame(this.world.BOB.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Bob.BOB_STATE_JUMP:
			keyFrame = Assets.bobJump.getKeyFrame(this.world.BOB.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Bob.BOB_STATE_HIT:
		default:
			keyFrame = Assets.bobHit;
		}
		
		float side = this.world.BOB.VELOCITY.x < 0 ? -1 : 1;
		this.batcher.drawSprite(this.world.BOB.POSITION.x, this.world.BOB.POSITION.y, side * 1, 1, keyFrame);
	}

	private void renderBackground() {
		this.batcher.beginBatch(Assets.background);
		this.batcher.drawSprite(this.cam.POSITION.x, this.cam.POSITION.y, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundRegion);
		this.batcher.endBatch();
	}
}
