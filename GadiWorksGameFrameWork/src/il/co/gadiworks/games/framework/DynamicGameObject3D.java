package il.co.gadiworks.games.framework;

import il.co.gadiworks.games.framework.math.Vector3;

public class DynamicGameObject3D extends GameObject3D {
	public final Vector3 VELOCITY;
	public final Vector3 ACCEL;

	public DynamicGameObject3D(float x, float y, float z, float radius) {
		super(x, y, z, radius);
		
		this.VELOCITY = new Vector3();
		this.ACCEL = new Vector3();
	}
}