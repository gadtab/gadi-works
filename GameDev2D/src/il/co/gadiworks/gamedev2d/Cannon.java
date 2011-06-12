package il.co.gadiworks.gamedev2d;

import il.co.gadiworks.games.gamedev2d.GameObject;

public class Cannon extends GameObject {
	public float angle;

	public Cannon(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		this.angle = 0;
	}
}