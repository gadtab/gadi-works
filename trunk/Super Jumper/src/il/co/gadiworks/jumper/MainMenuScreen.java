package il.co.gadiworks.jumper;

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
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Vector2 touchPoint;
	

	public MainMenuScreen(Game game) {
		super(game);
		
		this.guiCam = new Camera2D(GL_GRAPHICS, 320, 480);
		this.batcher = new SpriteBatcher(GL_GRAPHICS, 100);
		this.soundBounds = new Rectangle(0, 0, 64, 64);
		this.playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
		this.highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
		this.helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);
		this.touchPoint = new Vector2();
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		GAME.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				this.touchPoint.set(event.x, event.y);
				this.guiCam.touchToWorld(this.touchPoint);
				
				if (OverlapTester.pointInRectangle(this.playBounds, this.touchPoint)) {
					Assets.playSound(Assets.clickSound);
					GAME.setScreen(new GameScreen(GAME));
					return;
				}
				if (OverlapTester.pointInRectangle(this.highscoresBounds, this.touchPoint)) {
					Assets.playSound(Assets.clickSound);
					GAME.setScreen(new HighscoresScreen(GAME));
					return;
				}
				if (OverlapTester.pointInRectangle(this.helpBounds, this.touchPoint)) {
					Assets.playSound(Assets.clickSound);
					GAME.setScreen(new HelpScreen(GAME));
					return;
				}
				if (OverlapTester.pointInRectangle(this.soundBounds, this.touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.soundEnabled = !Settings.soundEnabled;
					if (Settings.soundEnabled) {
						Assets.music.play();
					}
					else {
						Assets.music.pause();
					}
				}
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
		this.batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
		this.batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		this.batcher.beginBatch(Assets.items);
		
		this.batcher.drawSprite(160, 480 - 10 - 71, 274, 142, Assets.logo);
		this.batcher.drawSprite(160, 200, 300, 110, Assets.mainMenu);
		this.batcher.drawSprite(32, 32, 64, 64, Settings.soundEnabled ? Assets.soundOn : Assets.soundOff);
		
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}
	
	@Override
	public void pause() {
		Settings.save(GAME.getFileIO());
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resume() {
	}
}