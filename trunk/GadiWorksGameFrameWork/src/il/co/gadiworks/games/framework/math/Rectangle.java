package il.co.gadiworks.games.framework.math;

public class Rectangle {
	public final Vector2 LOWER_LEFT;
	public float width, height;
	
	public Rectangle(float x, float y, float width, float height) {
		this.LOWER_LEFT = new Vector2(x, y);
		this.width = width;
		this.height = height;
	}
}
