package il.co.gadiworks.jumper;

import il.co.gadiworks.games.framework.DynamicGameObject;

public class Squirrel extends DynamicGameObject {
	public static final float SQUIRREL_WIDTH = 1f;
	public static final float SQUIRREL_HEIGHT = 0.6f;
	public static final float SQUIRREL_VELOCITY = 3f;
	
	float stateTime = 0;

	public Squirrel(float x, float y) {
		super(x, y, SQUIRREL_WIDTH, SQUIRREL_HEIGHT);
		VELOCITY.set(SQUIRREL_VELOCITY, 0);
	}
	
	public void update(float deltaTime) {
		POSITION.add(VELOCITY.x * deltaTime, VELOCITY.y * deltaTime);
		BOUNDS.LOWER_LEFT.set(POSITION).sub(SQUIRREL_WIDTH / 2, SQUIRREL_HEIGHT / 2);
		
		if (POSITION.x < SQUIRREL_WIDTH / 2) {
			POSITION.x = SQUIRREL_WIDTH / 2;
			VELOCITY.x = SQUIRREL_VELOCITY;
		}
		if (POSITION.x > World.WORLD_WIDTH - SQUIRREL_WIDTH / 2) {
			POSITION.x = World.WORLD_WIDTH - SQUIRREL_WIDTH / 2;
			VELOCITY.x = -SQUIRREL_VELOCITY;
		}
		
		this.stateTime += deltaTime;
	}
}
