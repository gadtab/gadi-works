package il.co.gadiworks.games.framework.math;

public class Circle {
	public final Vector2 CENTER = new Vector2();
	public float radius;
	
	public Circle(float x, float y, float radius) {
		this.CENTER.set(x, y);
		this.radius = radius;
	}
}
