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
import il.co.gadiworks.jumper.World.WorldListener;

public class GameScreen extends GLScreen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	
	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	int lastScore;
	String scoreString;

	public GameScreen(Game game) {
		super(game);
		
		this.state = GAME_READY;
		this.guiCam = new Camera2D(GL_GRAPHICS, 320, 480);
		this.touchPoint = new Vector2();
		this.batcher = new SpriteBatcher(GL_GRAPHICS, 1000);
		this.worldListener = new WorldListener() {
			
			@Override
			public void jump() {
				Assets.playSound(Assets.jumpSound);
			}
			
			@Override
			public void hit() {
				Assets.playSound(Assets.hitSound);
			}
			
			@Override
			public void highJump() {
				Assets.playSound(Assets.highJumpSound);
			}
			
			@Override
			public void coin() {
				Assets.playSound(Assets.coinSound);
			}
		};
		this.world = new World(this.worldListener);
		this.renderer = new WorldRenderer(GL_GRAPHICS, this.batcher, this.world);
		this.pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		this.resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		this.quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
		this.lastScore = 0;
		this.scoreString = "score: 0";
	}
	
	@Override
	public void update(float deltaTime) {
		if (deltaTime > 0.1f) {
			deltaTime = 0.1f;
		}
		
		switch (this.state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateGameOver() {
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP) {
				continue;
			}
			GAME.setScreen(new MainMenuScreen(GAME));
		}
	}

	private void updateLevelEnd() {
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP) {
				continue;
			}
			this.world = new World(this.worldListener);
			this.renderer = new WorldRenderer(GL_GRAPHICS, this.batcher, this.world);
			this.world.score = this.lastScore;
			this.state = GAME_READY;
		}
	}

	private void updatePaused() {
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP) {
				continue;
			}
			
			this.touchPoint.set(event.x, event.y);
			this.guiCam.touchToWorld(this.touchPoint);
			
			if (OverlapTester.pointInRectangle(this.resumeBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				this.state = GAME_RUNNING;
				return;
			}
			
			if (OverlapTester.pointInRectangle(this.quitBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				GAME.setScreen(new MainMenuScreen(GAME));
				return;
			}
		}
	}

	private void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP) {
				continue;
			}
			
			this.touchPoint.set(event.x, event.y);
			this.guiCam.touchToWorld(this.touchPoint);
			
			if (OverlapTester.pointInRectangle(this.pauseBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				this.state = GAME_PAUSED;
				return;
			}
		}
		
		this.world.update(deltaTime, GAME.getInput().getAccelX());
		if (this.world.score != this.lastScore) {
			this.lastScore = this.world.score;
			this.scoreString = "" + this.lastScore;
		}
		if (this.world.state == World.WORLD_STATE_NEXT_LEVEL) {
			this.state = GAME_LEVEL_END;
		}
		if (this.world.state == World.WORLD_STATE_GAME_OVER) {
			this.state = GAME_OVER;
			if (this.lastScore > Settings.HIGH_SCORES[4]) {
				this.scoreString = "new highscore: " + this.lastScore;
			} else {
				this.scoreString = "score: " + this.lastScore;
			}
			
			Settings.addScore(this.lastScore);
			Settings.save(GAME.getFileIO());
		}
	}

	private void updateReady() {
		if (GAME.getInput().getKeyEvents().size() > 0) {
			this.state = GAME_RUNNING;
		}
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = GL_GRAPHICS.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		this.renderer.render();
		
		this.guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.batcher.beginBatch(Assets.items);
		switch(this.state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentGameOver() {
		this.batcher.drawSprite(160, 240, 160, 96, Assets.gameOver);
		float scoreWidth = Assets.font.GLYPH_WIDTH * this.scoreString.length();
		Assets.font.drawText(this.batcher, this.scoreString, 160 - scoreWidth / 2, 480 - 20);
	}

	private void presentLevelEnd() {
		String topText = "the princess is ...";
		String bottomText = "in another castle!";
		float topWidth = Assets.font.GLYPH_WIDTH * topText.length();
		float bottomWidth = Assets.font.GLYPH_WIDTH * bottomText.length();
		Assets.font.drawText(this.batcher, topText, 160 - topWidth / 2, 480 - 40);
		Assets.font.drawText(this.batcher, bottomText, 160 - bottomWidth / 2, 40);
	}

	private void presentPaused() {
		this.batcher.drawSprite(160, 240, 192, 96, Assets.pauseMenu);
		Assets.font.drawText(this.batcher, this.scoreString, 16, 480 - 20);
	}

	private void presentRunning() {
		this.batcher.drawSprite(320 - 32, 480 - 32, 64, 64, Assets.pause);
		Assets.font.drawText(this.batcher, this.scoreString, 16, 480 - 20);
	}

	private void presentReady() {
		this.batcher.drawSprite(160, 240, 192, 32, Assets.ready);
	}
	
	@Override
	public void pause() {
		if (this.state == GAME_RUNNING) {
			this.state = GAME_PAUSED;
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resume() {
	}
}
