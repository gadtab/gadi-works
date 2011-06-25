package il.co.gadiworks.droidinvaders;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Input.TouchEvent;
import il.co.gadiworks.games.framework.gl.Camera2D;
import il.co.gadiworks.games.framework.gl.SpriteBatcher;
import il.co.gadiworks.games.framework.impl.GLScreen;
import il.co.gadiworks.games.framework.math.OverlapTester;
import il.co.gadiworks.games.framework.math.Rectangle;
import il.co.gadiworks.games.framework.math.Vector2;

public class SettingScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Rectangle touchBounds;
	Rectangle accelBounds;
	Rectangle soundBounds;
	Rectangle backBounds;

	public SettingScreen(Game game) {
		super(game);
		
		this.guiCam = new Camera2D(GL_GRAPHICS, 480, 320);
		this.batcher = new SpriteBatcher(GL_GRAPHICS, 10);
		this.touchPoint = new Vector2();
		this.touchBounds = new Rectangle(120 - 32, 160 - 32, 64, 64);
		this.accelBounds = new Rectangle(240 - 32, 160 - 32, 64, 64);
		this.soundBounds = new Rectangle(360 - 32, 160 - 32, 64, 64);
		this.backBounds = new Rectangle(32, 32, 64, 64);
	}
	
	@Override
	public void update(float deltaTime) {
List<TouchEvent> events = GAME.getInput().getTouchEvents();
		
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP) {
				continue;
			}
			
			this.guiCam.touchToWorld(this.touchPoint.set(event.x, event.y));
			
			if (OverlapTester.pointInRectangle(this.touchBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.touchEnabled = true;
				Settings.save(GAME.getFileIO());
			}
			if (OverlapTester.pointInRectangle(this.accelBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.touchEnabled = false;
				Settings.save(GAME.getFileIO());
			}
			if (OverlapTester.pointInRectangle(this.soundBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.touchEnabled = !Settings.touchEnabled;
				if (Settings.soundEnabled) {
					Assets.music.play();
				}
				else {
					Assets.music.pause();
				}
				Settings.save(GAME.getFileIO());
			}
			if (OverlapTester.pointInRectangle(this.backBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				GAME.setScreen(new MainMenuScreen(GAME));
			}
		}
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = GL_GRAPHICS.getGL();
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		this.guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		this.batcher.beginBatch(Assets.background);
		this.batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
		this.batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		this.batcher.beginBatch(Assets.items);
		
		this.batcher.drawSprite(240, 280, 224, 32, Assets.settingsRegion);
		this.batcher.drawSprite(120, 160, 64, 64, 
								Settings.touchEnabled ? Assets.touchEnabledRegion : Assets.touchRegion);
		this.batcher.drawSprite(240, 160, 64, 64, 
				Settings.touchEnabled ? Assets.accelRegion : Assets.accelEnabledRegion);
		this.batcher.drawSprite(360, 160, 64, 64, 
				Settings.soundEnabled ? Assets.soundEnabledRegion : Assets.leftRegion);
		
		this.batcher.drawSprite(32, 32, 64, 64, Assets.settingsRegion);
		
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}