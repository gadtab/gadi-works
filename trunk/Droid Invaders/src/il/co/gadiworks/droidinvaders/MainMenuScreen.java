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

public class MainMenuScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Rectangle playBounds;
	Rectangle settingBounds;

	public MainMenuScreen(Game game) {
		super(game);
		
		this.guiCam = new Camera2D(GL_GRAPHICS, 480, 320);
		this.batcher = new SpriteBatcher(GL_GRAPHICS, 10);
		this.touchPoint = new Vector2();
		this.playBounds = new Rectangle(240 - 112, 100, 224, 32);
		this.settingBounds = new Rectangle(240 - 112, 100 - 32, 224, 32);
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
			
			if (OverlapTester.pointInRectangle(this.playBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				GAME.setScreen(new GameScreen(GAME));
				return;
			}
			if (OverlapTester.pointInRectangle(this.settingBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				GAME.setScreen(new SettingScreen(GAME));
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
		
		this.batcher.drawSprite(240, 240, 384, 128, Assets.logoRegion);
		this.batcher.drawSprite(240, 100, 224, 64, Assets.menuRegion);
		
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