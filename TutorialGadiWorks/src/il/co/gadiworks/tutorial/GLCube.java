package il.co.gadiworks.tutorial;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLCube {
	private float[] vertices = {
			 1,   1,  -1, // p0 topFrontRight
			 1,  -1, -1, //p1 bottomFrontRight
			 -1, -1, -1, //p2 bottomFrontLeft
			 -1,   1, -1, // p3 topFrontLeft
			 1,   1,    1, // p4 topBackRight
			 1,  -1,   1, //p5 bottomBackRight
			 -1, -1,  1, //p6 bottomBackLeft
			 -1,   1,  1  // p7 topBackLeft
		};
		
		private FloatBuffer vertBuff;
		
		private short[] pIndex = {
				3, 4, 0,   0, 4, 1,   3, 0, 1,
				3, 7, 4,   7, 6, 4,   7, 3, 6,
				3, 1, 2,   1, 6, 2,   6, 3, 2,
				1, 4, 5,   5, 6, 1,   6, 5, 4
		};
		
		private ShortBuffer pBuff;
		
		public GLCube() {
			// 4 bytes per float
			ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
			bBuff.order(ByteOrder.nativeOrder());
			vertBuff = bBuff.asFloatBuffer();
			vertBuff.put(vertices);
			vertBuff.position(0);
			
			// 2 bytes per short
			ByteBuffer pbBuff = ByteBuffer.allocateDirect(pIndex.length * 2);
			pbBuff.order(ByteOrder.nativeOrder());
			pBuff = pbBuff.asShortBuffer();
			pBuff.put(pIndex);
			pBuff.position(0);
		}
		
		public void draw(GL10 gl) {
			gl.glFrontFace(GL10.GL_CW);
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glCullFace(GL10.GL_BACK);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuff);
			gl.glDrawElements(GL10.GL_TRIANGLES, pIndex.length, GL10.GL_UNSIGNED_SHORT, pBuff);
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisable(GL10.GL_CULL_FACE);
		}
}