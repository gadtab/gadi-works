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

public class HighscoreScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle backBounds;
	Vector2 touchPoint;
	String[] highScores;
	float xOffset = 0;

	public HighscoreScreen(Game game) {
		super(game);
		
		this.guiCam = new Camera2D(GL_GRAPHICS, 320, 480);
		this.backBounds = new Rectangle(0, 0, 64, 64);
		this.touchPoint = new Vector2();
		this.batcher = new SpriteBatcher(GL_GRAPHICS, 100);
		this.highScores = new String[5];
		for (int i = 0; i < 5; i++) {
			this.highScores[i] = (i + 1) + ". " + Settings.HIGH_SCORES[i];
			this.xOffset = Math.max(this.highScores[i].length() * Assets.font.GLYPH_WIDTH, this.xOffset);
		}
		
		this.xOffset = 160 - this.xOffset / 2;
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		GAME.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			this.touchPoint.set(event.x, event.y);
			this.guiCam.touchToWorld(this.touchPoint);
			
			if (event.type == TouchEvent.TOUCH_UP) {
				if (OverlapTester.pointInRectangle(this.backBounds, this.touchPoint)) {
					Assets.playSound(Assets.clickSound);
					GAME.setScreen(new MainMenuScreen(GAME));
					return;
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
		this.batcher.drawSprite(160, 360, 300, 33, Assets.highScoreRegion);
		
		float y = 240;
		for (int i = 4; i >= 0; i--) {
			Assets.font.drawText(this.batcher, this.highScores[i], this.xOffset, y);
			y += Assets.font.GLYPH_HEIGHT;
		}
		
		this.batcher.drawSprite(32, 32, 64, 64, Assets.arrow);
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
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