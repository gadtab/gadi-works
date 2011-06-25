package il.co.gadiworks.droidinvaders;

import il.co.gadiworks.games.framework.DynamicGameObject3D;

public class Shot extends DynamicGameObject3D {
	static final float SHOT_VELOCITY = 10f;
	static final float SHOT_RADIUS = 0.1f;

	public Shot(float x, float y, float z, float velocityZ) {
		super(x, y, z, SHOT_RADIUS);
		VELOCITY.z = velocityZ;
	}
	
	public void update (float deltaTime) {
		POSITION.z = VELOCITY.z * deltaTime;
		BOUNDS.CENTER.set(POSITION);
	}
}