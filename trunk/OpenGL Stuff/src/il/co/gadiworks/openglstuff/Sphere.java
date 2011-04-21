package il.co.gadiworks.openglstuff;

import java.nio.FloatBuffer;

import android.graphics.Bitmap;
import android.util.Log;

public class Sphere {
	private static final String TAG = "Sphere";
	
	public Sphere(int slices, int stacks, float radius, float H, float K, float Z, Bitmap image, Bitmap first, Bitmap second) {
		FloatBuffer[] slicesBuffers = new FloatBuffer[slices];
	    FloatBuffer[] normalsBuffers = new FloatBuffer[slices];
	    FloatBuffer[] texCoordsBuffers = new FloatBuffer[slices];
	    
	    float[] total_vertexBuff;
	    float[]  total_normalsBuff;
	    float[]  total_textCoordsBuff;
	    int vertex_counter = 0;
	    int normals_counter = 0;
	    int texCoords_counter = 0;
	    int position_dst;
	    float tmp[];
	    
	    for (int i = 0; i < slices; i++) {
	    	float[] vertexCoords = new float [2 * 3 * (stacks + 1)];
	    	float[] normalCoords = new float[  2 * 3 *(stacks + 1)];
            float[] textureCoords = new float[ 4 * (stacks + 1) ];
            double alpha0 = i * (2 * Math.PI) / slices;
            double alpha1 = (i + 1) * (2 * Math.PI) / slices;
            float cosAlpha0 = (float) Math.cos(alpha0);
            float sinAlpha0 = (float) Math.sin(alpha0);
            float cosAlpha1 = (float) Math.cos(alpha1);
            float sinAlpha1 = (float) Math.sin(alpha1);
            
            for (int j = 0; j <= stacks; j++) {
            	double beta = j * Math.PI / stacks - Math.PI / 2;
                float cosBeta = (float) Math.cos(beta);
                float sinBeta = (float) Math.sin(beta);
                setXYZ(vertexCoords, 6 * j,    radius * cosBeta * cosAlpha1,   radius * sinBeta,   radius * cosBeta * sinAlpha1   );
                setXYZ(vertexCoords, 6 * j + 3,radius * cosBeta * cosAlpha0,radius * sinBeta,radius * cosBeta * sinAlpha0);
                vertex_counter +=  2;

                Log.d(TAG, "j:"+j);

                setXYZ(normalCoords, 6 * j,cosBeta * cosAlpha1,sinBeta,cosBeta * sinAlpha1);
                setXYZ(normalCoords, 6 * j + 3,cosBeta * cosAlpha0,sinBeta,cosBeta * sinAlpha0);
                normals_counter += 2;
                setXY(textureCoords, 4 * j,((float) (i + 1)) / slices,((float) j) / stacks);
                setXY(textureCoords, 4 * j + 2,((float) i) / slices,((float) j) / stacks);
                texCoords_counter += 2;
            }
            
            slicesBuffers[i] = FloatBuffer.wrap(vertexCoords);
            normalsBuffers[i] = FloatBuffer.wrap(normalCoords);
            texCoordsBuffers[i] = FloatBuffer.wrap(textureCoords);
	    }
	}

	private void setXY(float[] coords, int i, float f, float g) {
		// TODO Auto-generated method stub
		
	}

	private void setXYZ(float[] cdoords, int i, float f, float sinBeta,
			float g) {
		// TODO Auto-generated method stub
		
	}
}
