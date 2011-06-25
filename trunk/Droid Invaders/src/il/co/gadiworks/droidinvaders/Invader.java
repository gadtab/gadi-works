package il.co.gadiworks.droidinvaders;

import il.co.gadiworks.games.framework.GameObject3D;

public class Invader extends GameObject3D {
	static final int INVADER_ALIVE = 0;
	static final int INVADER_DEAD = 1;
	static final float INVADER_EXPLOSION_TIME = 1.6f;
	static final float INVADER_RADIUS = 0.75f;
	static final float INVADER_VELOCITY = 1;
	static final int MOVE_LEFT = 0;
	static final int MOVE_DOWN = 1;
	static final int MOVE_RIGHT = 2;
	
	int state = INVADER_ALIVE;
	float stateTime = 0;
	int move = MOVE_LEFT;
	boolean wasLastStateLeft = true;
	float moveDistance = World.WORLD_MAX_X / 2;

	public Invader(float x, float y, float z) {
		super(x, y, z, INVADER_RADIUS);
	}
	
	public void update(float deltaTime, float speedMultiplier) {
		if (this.state == INVADER_ALIVE) {
			this.moveDistance += deltaTime * INVADER_VELOCITY * speedMultiplier;
			if (this.move == MOVE_LEFT) {
				POSITION.x -= deltaTime * INVADER_VELOCITY * speedMultiplier;
				if (this.moveDistance > World.WORLD_MAX_X) {
					this.move = MOVE_DOWN;
					this.moveDistance = 0;
					this.wasLastStateLeft = true;
				}
			}
			if (this.move == MOVE_RIGHT) {
				POSITION.x += deltaTime * INVADER_VELOCITY * speedMultiplier;
				if (this.moveDistance > World.WORLD_MAX_X) {
					this.move = MOVE_DOWN;
					this.moveDistance = 0;
					this.wasLastStateLeft = false;
				}
			}
			if (this.move == MOVE_DOWN) {
				POSITION.z += deltaTime * INVADER_VELOCITY * speedMultiplier;
				if (this.moveDistance > 1) {
					if (this.wasLastStateLeft) {
						this.move = MOVE_RIGHT;
					} else {
						this.move = MOVE_LEFT;
					}
					this.moveDistance = 0;
				}
			}
			
			BOUNDS.CENTER.set(POSITION);
		}
		
		this.stateTime += deltaTime;
	}
	
	public void kill() {
		this.state = INVADER_DEAD;
		this.stateTime = 0;
	}
}