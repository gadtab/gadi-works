package il.co.gadiworks.games.framework.impl;

import il.co.gadiworks.games.framework.Audio;
import il.co.gadiworks.games.framework.FileIO;
import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Graphics;
import il.co.gadiworks.games.framework.Input;
import il.co.gadiworks.games.framework.Screen;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class AndroidGame extends Activity implements Game {
	AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boolean isLandscape = getResources().getConfiguration().orientation ==
			Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, 
				frameBufferHeight, Config.RGB_565);
		
		float scaleX = (float) frameBufferWidth / 
			getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float) frameBufferHeight /
			getWindowManager().getDefaultDisplay().getHeight();
		
		this.renderView = new AndroidFastRenderView(this, frameBuffer);
		this.graphics = new AndroidGraphics(getAssets(), frameBuffer);
		this.fileIO = new AndroidFileIO(getAssets());
		this.audio = new AndroidAudio(this);
		this.input = new AndroidInput(this, this.renderView, scaleX, scaleY);
		this.screen = getStartScreen();
		
		setContentView(this.renderView);
		
		PowerManager powerManager = (PowerManager) 
			getSystemService(Context.POWER_SERVICE);
		this.wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.wakeLock.acquire();
		this.screen.resume();
		this.renderView.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.wakeLock.release();
		this.renderView.pause();
		this.screen.pause();
		
		if (isFinishing()) {
			this.screen.dispose();
		}
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
		return this.graphics;
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