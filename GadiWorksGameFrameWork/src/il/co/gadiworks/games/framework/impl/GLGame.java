package il.co.gadiworks.games.framework.impl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Audio;
import il.co.gadiworks.games.framework.FileIO;
import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Graphics;
import il.co.gadiworks.games.framework.Input;
import il.co.gadiworks.games.framework.Screen;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class GLGame extends Activity implements Game, Renderer {
	enum GLGameState {
		Initialized,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	GLSurfaceView glView;
	GLGraphics glGraphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	GLGameState state = GLGameState.Initialized;
	Object stateChanged = new Object();
	long startTime = System.nanoTime();
	WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		                     WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.glView = new GLSurfaceView(this);
		this.glView.setRenderer(this);
		setContentView(this.glView);
		
		this.glGraphics = new GLGraphics(this.glView);
		this.fileIO = new AndroidFileIO(getAssets());
		this.audio = new AndroidAudio(this);
		this.input = new AndroidInput(this, this.glView, 1, 1);
		
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		this.wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.glView.onResume();
		this.wakeLock.acquire();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		this.glGraphics.setGL(gl);
		
		synchronized (this.stateChanged) {
			if (this.state == GLGameState.Initialized) {
				this.screen = getStartScreen();
			}
			
			this.state = GLGameState.Running;
			this.screen.resume();
			this.startTime = System.nanoTime();
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLGameState state = null;
		
		synchronized (this.stateChanged) {
			state = this.state;
		}
		
		if (state == GLGameState.Running) {
			float deltaTime = (System.nanoTime() - this.startTime) / 1000000000.0f;
			this.startTime = System.nanoTime();
			
			this.screen.update(deltaTime);
			this.screen.present(deltaTime);
		}
		
		if (state == GLGameState.Paused) {
			this.screen.pause();
			
			synchronized (this.stateChanged) {
				this.state = GLGameState.Idle;
				this.stateChanged.notifyAll();
			}
		}
		
		if (state == GLGameState.Finished) {
			this.screen.pause();
			this.screen.dispose();
			synchronized (this.stateChanged) {
				this.state = GLGameState.Idle;
				this.stateChanged.notifyAll();
			}
		}
	}
	
	@Override
	protected void onPause() {
		synchronized (this.stateChanged) {
			if(isFinishing()) {
				this.state = GLGameState.Finished;
			}
			else {
				this.state = GLGameState.Paused;
			}
			
			while (true) {
				try {
					this.stateChanged.wait();
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		this.wakeLock.release();
		this.glView.onPause();
		
		super.onPause();
	}
	
	public GLGraphics getGLGraphics() {
		return this.glGraphics;
	}

	@Override
	public Input getInput() {
		return this.input;
	}

	@Override
	public FileIO getFileIO() {
		return this.fileIO;
	}

	@Override
	public Graphics getGraphics() {
		throw new IllegalStateException("We are using OpenGL!");
	}

	@Override
	public Audio getAudio() {
		return this.audio;
	}

	@Override
	public void setScreen(Screen screen) {
		if (screen == null) {
			throw new IllegalArgumentException("Screen must not be null");
		}
		
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	@Override
	public Screen getCurrentScreen() {
		return this.screen;
	}

	@Override
	public abstract Screen getStartScreen();
}