package il.co.gadiworks.jumper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.impl.GLGame;

public class SuperJumper extends GLGame {
	boolean firstTimeCreate = true;

	@Override
	public Screen getStartScreen() {
		return new MainMenuScreen(this);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		
		if (firstTimeCreate) {
			Settings.load(getFileIO());
			Assets.load(this);
			this.firstTimeCreate = false;
		}
		else {
			Assets.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		
		if (Settings.soundEnabled) {
			Assets.music.pause();
		}
	}
}