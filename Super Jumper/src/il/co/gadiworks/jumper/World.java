package il.co.gadiworks.jumper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import il.co.gadiworks.games.framework.math.OverlapTester;
import il.co.gadiworks.games.framework.math.Vector2;

public class World {
	public interface WorldListener {
		public void jump();
		public void highJump();
		public void hit();
		public void coin();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 GRAVITY = new Vector2(0, -12);
	
	public final Bob BOB;
	public final List<Platform> PLATFORMS;
	public final List<Spring> SPRINGS;
	public final List<Squirrel> SQUIRRELS;
	public final List<Coin> COINS;
	public Castle castle;
	public final WorldListener LISTENER;
	public final Random RAND;
	
	public float heightSoFar;
	public int score;
	public int state;
	
	public World(WorldListener listener) {
		this.BOB = new Bob(5, 1);
		this.PLATFORMS = new ArrayList<Platform>();
		this.SPRINGS = new ArrayList<Spring>();
		this.SQUIRRELS = new ArrayList<Squirrel>();
		this.COINS = new ArrayList<Coin>();
		this.LISTENER = listener;
		this.RAND = new Random();
		generateLevel();
		
		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel() {
		float y = Platform.PLATFORM_HEIGHT / 2;
		float maxJumpHeight = Bob.BOB_JUMP_VELOCITY * Bob.BOB_JUMP_VELOCITY / (2 * (-GRAVITY.y));
		
		while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
			int type = this.RAND.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
			float x = this.RAND.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
			
			Platform platform = new Platform(type, x, y);
			this.PLATFORMS.add(platform);
			
			if (this.RAND.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING) {
				Spring spring = new Spring(platform.POSITION.x, platform.POSITION.y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
				this.SPRINGS.add(spring);
			}
			
			if (y > WORLD_HEIGHT / 3 && this.RAND.nextFloat() > 0.8f) {
				Squirrel squirrel = new Squirrel(platform.POSITION.x + this.RAND.nextFloat(), 
												 platform.POSITION.y + Squirrel.SQUIRREL_HEIGHT + this.RAND.nextFloat() * 2);
				this.SQUIRRELS.add(squirrel);
			}
			
			if (this.RAND.nextFloat() > 0.6f) {
				Coin coin = new Coin(platform.POSITION.x + this.RAND.nextFloat(), 
									 platform.POSITION.y + Coin.COIN_HEIGHT + this.RAND.nextFloat() * 3);
				this.COINS.add(coin);
			}
			
			y += (maxJumpHeight - 0.5f);
			y -= this.RAND.nextFloat() * (maxJumpHeight / 3);
		}
		
		this.castle = new Castle(WORLD_WIDTH / 2, y);
	}
	
	public void update(float deltaTime, float accelX) {
		updateBob(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateSquirrels(deltaTime);
		updateCoins(deltaTime);
		
		if (this.BOB.state != Bob.BOB_STATE_HIT) {
			checkCollisions();
		}
		checkGameOver();
	}

	private void checkGameOver() {
		if (this.heightSoFar - 7.5f > this.BOB.POSITION.y) {
			this.state = WORLD_STATE_GAME_OVER;
		}
	}

	private void checkCollisions() {
		checkPlatformCollisions();
		checkSquirrelCollisions();
		checkItemCollisions();
		checkCastleCollisions();
	}

	private void checkCastleCollisions() {
		if (OverlapTester.overlapRectangles(this.castle.BOUNDS, this.BOB.BOUNDS)) {
			this.state = WORLD_STATE_NEXT_LEVEL;
		}
	}

	private void checkItemCollisions() {
		int len = this.COINS.size();
		for (int i = 0; i < len; i++) {
			Coin coin = this.COINS.get(i);
			if (OverlapTester.overlapRectangles(this.BOB.BOUNDS, coin.BOUNDS)) {
				this.COINS.remove(coin);
				len = this.COINS.size();
				this.LISTENER.coin();
				this.score += Coin.COIN_SCORE;
			}
		}
		
		if (this.BOB.VELOCITY.y > 0) {
			return;
		}
		
		len = this.SPRINGS.size();
		for (int i = 0; i < len; i++) {
			Spring spring = this.SPRINGS.get(i);
			if (this.BOB.POSITION.y > spring.POSITION.y) {
				if (OverlapTester.overlapRectangles(this.BOB.BOUNDS, spring.BOUNDS)) {
					this.BOB.hitSpring();
					this.LISTENER.highJump();
				}
			}
		}
	}

	private void checkSquirrelCollisions() {
		int len = this.SQUIRRELS.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = this.SQUIRRELS.get(i);
			if (OverlapTester.overlapRectangles(squirrel.BOUNDS, this.BOB.BOUNDS)) {
				this.BOB.hitSquirrel();
				this.LISTENER.hit();
			}
		}
	}

	private void checkPlatformCollisions() {
		if (this.BOB.VELOCITY.y > 0) {
			return;
		}
		
		int len = this.PLATFORMS.size();
		for (int i = 0; i < len; i++) {
			Platform platform = this.PLATFORMS.get(i);
			if (this.BOB.POSITION.y > platform.POSITION.y) {
				if (OverlapTester.overlapRectangles(this.BOB.BOUNDS, platform.BOUNDS)) {
					this.BOB.hitPlatform();
					this.LISTENER.jump();
					if(this.RAND.nextFloat() > 0.5f) {
						platform.pulverize();
					}
					break;
				}
			}
		}
	}

	private void updateCoins(float deltaTime) {
		int len = this.COINS.size();
		for (int i = 0; i < len; i++) {
			Coin coin = this.COINS.get(i);
			coin.update(deltaTime);
		}
	}

	private void updateSquirrels(float deltaTime) {
		int len = this.SQUIRRELS.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = this.SQUIRRELS.get(i);
			squirrel.update(deltaTime);
		}
	}

	private void updatePlatforms(float deltaTime) {
		int len = this.PLATFORMS.size();
		for (int i = 0; i < len; i++) {
			Platform platform = this.PLATFORMS.get(i);
			platform.update(deltaTime);
			
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING 
					&& platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
				this.PLATFORMS.remove(platform);
				len = this.PLATFORMS.size();
			}
		}
	}

	private void updateBob(float deltaTime, float accelX) {
		if (this.BOB.state != Bob.BOB_STATE_HIT && this.BOB.POSITION.y <= 0.5f){
			this.BOB.hitPlatform();
		}
		if (this.BOB.state != Bob.BOB_STATE_HIT){
			this.BOB.VELOCITY.x = -accelX / 10 * Bob.BOB_MOVE_VELOCITY;
		}
		this.BOB.update(deltaTime);
		this.heightSoFar = Math.max(this.BOB.POSITION.y, this.heightSoFar);
	}
}