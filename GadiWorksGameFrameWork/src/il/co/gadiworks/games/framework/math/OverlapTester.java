package il.co.gadiworks.games.framework.math;

public class OverlapTester {
	public static boolean overlapCircles(Circle c1, Circle c2) {
		float distance = c1.CENTER.distSquared(c2.CENTER);
		float radiusSum = c1.radius + c2.radius;
		
		return distance <= radiusSum * radiusSum;
	}
	
	public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
		if (r1.LOWER_LEFT.x < r2.LOWER_LEFT.x + r2.width &&
			r1.LOWER_LEFT.x + r1.width > r2.LOWER_LEFT.x &&
			r1.LOWER_LEFT.y < r2.LOWER_LEFT.y + r2.height &&
			r1.LOWER_LEFT.y + r1.height > r2.LOWER_LEFT.y) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean overlapCircleRectangle(Circle c, Rectangle r) {
		float closestX = c.CENTER.x;
		float closestY = c.CENTER.y;
		
		if (c.CENTER.x < r.LOWER_LEFT.x) {
			closestX = r.LOWER_LEFT.x;
		}
		else if (c.CENTER.x > r.LOWER_LEFT.x + r.width) {
			closestX = r.LOWER_LEFT.x + r.width;
		}
		
		if (c.CENTER.y < r.LOWER_LEFT.y) {
			closestY = r.LOWER_LEFT.y;
		}
		else if (c.CENTER.y > r.LOWER_LEFT.y + r.height) {
			closestY = r.LOWER_LEFT.y + r.height;
		}
		
		return c.CENTER.distSquared(closestX, closestY) < c.radius * c.radius;
	}
	
	public static boolean pointInCircle(Circle c, Vector2 p) {
		return c.CENTER.distSquared(p) < c.radius * c.radius;
	}
	
	public static boolean pointInCircle(Circle c, float x, float y) {
		return c.CENTER.distSquared(x, y) < c.radius * c.radius;
	}
	
	public static boolean pointInRectangle(Rectangle r, Vector2 p) {
		return r.LOWER_LEFT.x <= p.x && r.LOWER_LEFT.x + r.width >= p.x &&
			   r.LOWER_LEFT.y <= p.y && r.LOWER_LEFT.y + r.height >= p.y;
	}
	
	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.LOWER_LEFT.x <= x && r.LOWER_LEFT.x + r.width >= x &&
			   r.LOWER_LEFT.y <= y && r.LOWER_LEFT.y + r.height >= y;
	}
}
