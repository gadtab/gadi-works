package il.co.gadiworks.openglstuff;

//import android.graphics.Bitmap;
//import android.util.Log;

//import il.co.gadiworks.core.Object3dContainer;
//import il.co.gadiworks.core.RendererActivity;
//import il.co.gadiworks.objectPrimitves.Sphere;
//import il.co.gadiworks.utils.OpenGLStuff;
//import il.co.gadiworks.utils.Shared;
//import il.co.gadiworks.utils.Utils;
//import il.co.gadiworks.vos.Light;

import android.app.Activity;
import android.os.Bundle;

public class OpenGLStuffActivity extends /*Renderer*/Activity {
//	Sphere _sphere;
//	int _count;
//	
//	@Override	
//	public void initScene() {
//		Log.d(OpenGLStuff.TAG, "Test: OpenGLStuffActivity.initScene");
//		Light light = new Light();
//		
//		light.ambient.setAll(64, 64, 64, 255);
//		light.position.setAll(3, 3, 3);
//		scene.lights().add(light);
//		
//		this._sphere = new Sphere(0.8f, 15, 10, false, false, false);
//		
//		scene.addChild(_sphere);
//		
//		// Add textures to TextureManager               
////        Bitmap b = Utils.makeBitmapFromResourceId(this, R.drawable.jupiter);
////        Shared.textureManager().addTextureId(b, "jupiter", false);
////        b.recycle();
//        
////        this._sphere.textures().addById("jupiter");
//		
//		this._count = 0;
//	}
//
//	@Override
//	public void updateScene() {
//		Log.d(OpenGLStuff.TAG, "Test: OpenGLStuffActivity.updateScene");
//		// Spin the sphere
//		this._sphere.rotation().y += 1.0f;
//		
//		// Wobble sphere a little just for fun 
//        this._count++;
//        
//        float mag = (float)(Math.sin(this._count * 0.2 * Utils.DEG)) * 15;
//        this._sphere.rotation().z = (float)Math.sin(this._count * .33 * Utils.DEG) * mag;
//        
//        // Move camera around
//        scene.camera().position.z = 4.5f + (float)Math.sin(_sphere.rotation().y * Utils.DEG);
//        scene.camera().target.x = (float)Math.sin((_sphere.rotation().y + 90) * Utils.DEG) * 0.8f;
//	}
	
	
	
	
	
	private RotateGLSurfaceView view;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new RotateGLSurfaceView(this);
        setContentView(view);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }
}