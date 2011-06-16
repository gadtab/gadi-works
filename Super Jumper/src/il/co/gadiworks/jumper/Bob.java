package il.co.gadiworks.jumper;

import il.co.gadiworks.games.framework.DynamicGameObject;

public class Bob extends DynamicGameObject {
	public static final int BOB_STATE_JUMP = 0;
	public static final int BOB_STATE_FALL = 1;
	public static final int BOB_STATE_HIT = 2;
	public static final float BOB_JUMP_VELOCITY = 11f;
	public static final float BOB_MOVE_VELOCITY = 20f;
	public static final float BOB_WIDTH = 0.8f;
	public static final float BOB_HEIGHT = 0.8f;
	
	int state;
	float stateTime;

	public Bob(int type, float x, float y) {
		super(x, y, BOB_WIDTH, BOB_HEIGHT);
		this.state = BOB_STATE_FALL;
		this.stateTime = 0;
	}
	
	public void update(float deltaTime) {
		VELOCITY.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		POSITION.add(VELOCITY.x * deltaTime, VELOCITY.y * deltaTime);
		BOUNDS.LOWER_LEFT.set(POSITION).sub(BOUNDS.width / 2, BOUNDS.height / 2);
		
		if (VELOCITY.y > 0 && this.state != BOB_STATE_HIT) {
			if (this.state != BOB_STATE_JUMP) {
				this.state = BOB_STATE_JUMP;
				this.stateTime = 0;
			}
		}
		
		if (VELOCITY.y < 0 && this.state != BOB_STATE_HIT) {
			if (this.state != BOB_STATE_FALL) {
				this.state = BOB_STATE_FALL;
				this.stateTime = 0;
			}
		}
		
		if (POSITION.x < 0) {
			POSITION.x = World.WORLD_WIDTH;
		}
		if (POSITION.x > World.WORLD_WIDTH) {
			POSITION.x = 0;
		}
		this.stateTime += deltaTime;
	}
	
	public void hitSquirrel() {
		VELOCITY.set(0, 0);
		this.state = BOB_STATE_HIT;
		this.stateTime = 0;
	}
	
	public void hitPlatform() {
		VELOCITY.y = BOB_JUMP_VELOCITY;
		this.state = BOB_STATE_JUMP;
		this.stateTime = 0;
	}
	
	public void hitSpring() {
		VELOCITY.y = BOB_JUMP_VELOCITY * 1.5f;
		this.state = BOB_STATE_JUMP;
		this.stateTime = 0;
	}
}
