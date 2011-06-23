package il.co.gadiworks.games.framework.math;

public class Sphere {
	public final Vector3 CENTER = new Vector3();
	public float radius;
	
	public Sphere(float x, float y, float z, float radius) {
		this.CENTER.set(x, y, z);
		this.radius = radius;
	}
}
