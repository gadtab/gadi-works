package il.co.gadiworks.games.gamedev2d;

import il.co.gadiworks.games.framework.math.Vector2;

public class DynamicGameObject extends GameObject {
	public final Vector2 VELOCITY;
	public final Vector2 ACCEL;

	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		this.VELOCITY = new Vector2();
		this.ACCEL = new Vector2();
	}
}