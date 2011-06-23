package il.co.gadiworks.games.framework;

import il.co.gadiworks.games.framework.math.Sphere;
import il.co.gadiworks.games.framework.math.Vector3;

public class GameObject3D {
	public final Vector3 POSITION;
	public final Sphere  BOUNDS; 
	
	public GameObject3D(float x, float y, float z, float radius) {
		this.POSITION = new Vector3(x, y, z);
		this.BOUNDS = new Sphere(x, y, z, radius);
	}
}
