package il.co.gadiworks.droidinvaders;

import il.co.gadiworks.games.framework.GameObject3D;

public class Shield extends GameObject3D {
	static final float SHIELD_RADIUS = 0.5f;

	public Shield(float x, float y, float z) {
		super(x, y, z, SHIELD_RADIUS);
	}
}