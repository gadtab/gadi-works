package il.co.gadiworks.gl3d;

import il.co.gadiworks.games.framework.gl.Vertices3;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class HierarchicalObject {
	public float x, y, z;
	public float scale = 1;
	public float rotationY, rotationParent;
	public boolean hasParent;
	public final List<HierarchicalObject> CHILDREN = new ArrayList<HierarchicalObject>();
	public final Vertices3 MESH;
	
	public HierarchicalObject(Vertices3 mesh, boolean hasParent) {
		this.MESH = mesh;
		this.hasParent = hasParent;
	}
	
	public void update(float deltaTime) {
		this.rotationY += 45 * deltaTime;
		this.rotationParent += 20 * deltaTime;
		int len = this.CHILDREN.size();
		for (int i = 0; i < len; i++) {
			this.CHILDREN.get(i).update(deltaTime);
		}
	}
	
	public void render(GL10 gl) {
		gl.glPushMatrix();
		if (this.hasParent) {
			gl.glRotatef(this.rotationParent, 0, 1, 0);
		}
		gl.glTranslatef(this.x, this.y, this.z);
		gl.glPushMatrix();
		gl.glRotatef(this.rotationY, 0, 1, 0);
		gl.glScalef(this.scale, this.scale, this.scale);
		this.MESH.draw(GL10.GL_TRIANGLES, 0, 36);
		gl.glPopMatrix();
		int len = this.CHILDREN.size();
		for (int i = 0; i < len; i++) {
			this.CHILDREN.get(i).render(gl);
		}
		gl.glPopMatrix();
	}
}
