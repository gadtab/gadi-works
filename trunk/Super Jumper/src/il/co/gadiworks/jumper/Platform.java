package il.co.gadiworks.jumper;

import il.co.gadiworks.games.framework.DynamicGameObject;

public class Platform extends DynamicGameObject {
	public static final float PLATFORM_WIDTH = 2f;
	public static final float PLATFORM_HEIGHT = 0.5f;
	public static final int PLATFORM_TYPE_STATIC = 0;
	public static final int PLATFORM_TYPE_MOVING = 1;
	public static final int PLATFORM_STATE_NORMAL = 0;
	public static final int PLATFORM_STATE_PULVERIZING = 1;
	public static final float PLATFORM_PULVERIZE_TIME = 0.2f * 4;
	public static final float PLATFORM_VELOCITY = 2f;
	
	int type;
	int state;
	float stateTime;

	public Platform(int type, float x, float y) {
		super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		this.type = type;
		this.state = PLATFORM_STATE_NORMAL;
		this.stateTime = 0;
		if (type == PLATFORM_TYPE_MOVING) {
			VELOCITY.x = PLATFORM_VELOCITY;
		}
	}
	
	public void update(float deltaTime) {
		if (this.type == PLATFORM_TYPE_MOVING) {
			POSITION.add(VELOCITY.x * deltaTime, 0);
			BOUNDS.LOWER_LEFT.set(POSITION).sub(PLATFORM_WIDTH / 2, PLATFORM_HEIGHT / 2);
			
			if (POSITION.x < PLATFORM_WIDTH / 2) {
				VELOCITY.x = -VELOCITY.x;
				POSITION.x = PLATFORM_WIDTH / 2;
			}
			if (POSITION.x > World.WORLD_WIDTH - PLATFORM_WIDTH / 2) {
				VELOCITY.x = -VELOCITY.x;
				POSITION.x = World.WORLD_WIDTH - PLATFORM_WIDTH / 2;
			}
		}
		this.stateTime += deltaTime;
	}
	
	public void pulverize() {
		this.state = PLATFORM_STATE_PULVERIZING;
		this.stateTime = 0;
		VELOCITY.x = 0;
	}
}
