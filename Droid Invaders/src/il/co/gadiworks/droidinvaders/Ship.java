package il.co.gadiworks.droidinvaders;

import il.co.gadiworks.games.framework.DynamicGameObject3D;

public class Ship extends DynamicGameObject3D {
	static final float SHIP_VELOCITY = 20f;
	static final int SHIP_ALIVE = 0;
	static final int SHIP_EXPLODING = 1;
	static final float SHIP_EXPLOSION_TIME = 1.6f;
	static final float SHIP_RADIUS = 0.5f;
	
	int lives;
	int state;
	float stateTime = 0;

	public Ship(float x, float y, float z) {
		super(x, y, z, SHIP_RADIUS);
		this.lives = 3;
		this.state = SHIP_ALIVE;
	}
	
	public void update (float deltaTime, float accelY) {
		if (this.state == SHIP_ALIVE) {
			VELOCITY.set(accelY / 10 * SHIP_VELOCITY, 0, 0);
			POSITION.add(VELOCITY.x * deltaTime, 0, 0);
			if (POSITION.x < World.WORLD_MIN_X) {
				POSITION.x = World.WORLD_MIN_X;
			}
			if (POSITION.x > World.WORLD_MAX_X) {
				POSITION.x = World.WORLD_MAX_X;
			}
			BOUNDS.CENTER.set(POSITION);
		}
		else {
			if (this.stateTime >= SHIP_EXPLOSION_TIME) {
				this.lives--;
				this.stateTime = 0;
				this.state = SHIP_ALIVE;
			}
		}
		this.stateTime += deltaTime;
	}
	
	public void kill() {
		this.state = SHIP_EXPLODING;
		this.stateTime = 0;
		this.VELOCITY.x = 0;
	}
}