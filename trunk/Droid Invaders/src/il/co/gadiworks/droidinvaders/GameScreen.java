package il.co.gadiworks.droidinvaders;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.droidinvaders.World.WorldListener;
import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Input.TouchEvent;
import il.co.gadiworks.games.framework.gl.Camera2D;
import il.co.gadiworks.games.framework.gl.FPSCounter;
import il.co.gadiworks.games.framework.gl.SpriteBatcher;
import il.co.gadiworks.games.framework.impl.GLScreen;
import il.co.gadiworks.games.framework.math.OverlapTester;
import il.co.gadiworks.games.framework.math.Rectangle;
import il.co.gadiworks.games.framework.math.Vector2;

public class GameScreen extends GLScreen {
	static final int GAME_RUNNINIG = 0;
	static final int GAME_PAUSED = 1;
	static final int GAME_OVER = 2;
	
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
	Rectangle leftBounds;
	Rectangle rightBounds;
	Rectangle shotBounds;
	int lastScore;
	int lastLives;
	int lastWaves;
	String scoreString;
	FPSCounter fpsCounter;

	public GameScreen(Game game) {
		super(game);
		
		this.state = GAME_RUNNINIG;
		this.guiCam = new Camera2D(GL_GRAPHICS, 480, 320);
		this.touchPoint = new Vector2();
		this.batcher = new SpriteBatcher(GL_GRAPHICS, 100);
		this.world = new World();
		this.worldListener = new WorldListener() {
			
			@Override
			public void shot() {
				Assets.playSound(Assets.shotSound);
			}
			
			@Override
			public void explostion() {
				Assets.playSound(Assets.explosionSound);
			}
		};
		this.world.setWorldListener(worldListener);
		this.renderer = new WorldRenderer(GL_GRAPHICS);
		this.pauseBounds = new Rectangle(480 - 64, 320 - 64, 64, 64);
		this.resumeBounds = new Rectangle(240 - 80, 160, 160, 32);
		this.quitBounds = new Rectangle(240 - 80, 160 - 32, 160, 32);
		this.shotBounds = new Rectangle(480 - 64, 0, 64, 64);
		this.leftBounds = new Rectangle(0, 0, 64, 64);
		this.rightBounds = new Rectangle(64, 0, 64, 64);
		this.lastScore = 0;
		this.lastLives = this.world.SHIP.lives;
		this.lastWaves = this.world.waves;
		this.scoreString = "LIVES: " + this.lastLives + "; WAVES: " + this.lastWaves + "; SCORE: " + this.lastScore;
		this.fpsCounter = new FPSCounter();
	}
	
	@Override
	public void update(float deltaTime) {
		switch(this.state) {
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_RUNNINIG:
			updateRunning(deltaTime);
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updatePaused() {
		List<TouchEvent> events = GAME.getInput().getTouchEvents();
		
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP) {
				continue;
			}
			
			this.guiCam.touchToWorld(this.touchPoint.set(event.x, event.y));
			if (OverlapTester.pointInRectangle(this.resumeBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				this.state = GAME_RUNNINIG;
			}
			if (OverlapTester.pointInRectangle(this.quitBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				GAME.setScreen(new MainMenuScreen(GAME));
			}
		}
	}

	private void updateRunning(float deltaTime) {
		List<TouchEvent> events = GAME.getInput().getTouchEvents();
		
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP) {
				continue;
			}
			
			this.guiCam.touchToWorld(this.touchPoint.set(event.x, event.y));
			if (OverlapTester.pointInRectangle(this.pauseBounds, this.touchPoint)) {
				Assets.playSound(Assets.clickSound);
				this.state = GAME_PAUSED;
			}
			if (OverlapTester.pointInRectangle(this.shotBounds, this.touchPoint)) {
				this.world.shot();
			}
		}
		
		this.world.update(deltaTime, calculateInputAcceleration());
		if (this.world.SHIP.lives != this.lastLives || this.world.score != this.lastScore || this.world.waves != this.lastWaves) {
			this.lastLives = this.world.SHIP.lives;
			this.lastScore = this.world.score;
			this.lastWaves = this.world.waves;
			this.scoreString = "LIVES: " + this.lastLives + "; WAVES: " + this.lastWaves + "; SCORE: " + this.lastScore;
		}
		if (this.world.isGameOver()) {
			this.state = GAME_OVER;
		}
	}

	private float calculateInputAcceleration() {
		float accelX = 0;
		if (Settings.touchEnabled) {
			for (int i = 0; i < 2; i++) {
				if (GAME.getInput().isTouchDown(i)) {
					this.guiCam.touchToWorld(this.touchPoint.set(GAME.getInput().getTouchX(i), GAME.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(this.leftBounds, this.touchPoint)) {
						accelX = -Ship.SHIP_VELOCITY / 5;
					}
					if (OverlapTester.pointInRectangle(this.rightBounds, this.touchPoint)) {
						accelX = Ship.SHIP_VELOCITY / 5;
					}
				}
			}
		}
		else {
			accelX = GAME.getInput().getAccelY();
		}
		
		return accelX;
	}

	private void updateGameOver() {
		List<TouchEvent> events = GAME.getInput().getTouchEvents();
		
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				Assets.playSound(Assets.clickSound);
				GAME.setScreen(new MainMenuScreen(GAME));
			}
		}
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = GL_GRAPHICS.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		this.guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		this.batcher.beginBatch(Assets.background);
		this.batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		this.renderer.render(this.world, deltaTime);
		
		switch (state) {
		case GAME_RUNNINIG:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		
		this.fpsCounter.logFrame();
	}
	
	private void presentPaused() {
		GL10 gl = GL_GRAPHICS.getGL();
		
		this.guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		this.batcher.beginBatch(Assets.items);
		Assets.font.drawText(this.batcher, this.scoreString, 10, 320 - 10);
		this.batcher.drawSprite(240, 160, 160, 64, Assets.pauseRegion);
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentRunning() {
		GL10 gl = GL_GRAPHICS.getGL();
		
		this.guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		this.batcher.beginBatch(Assets.items);
		this.batcher.drawSprite(480 - 32, 320 - 32, 64, 64, Assets.pauseButtonRegion);
		Assets.font.drawText(this.batcher, this.scoreString, 10, 320 - 20);
		if (Settings.touchEnabled) {
			this.batcher.drawSprite(32, 32, 64, 64, Assets.leftRegion);
			this.batcher.drawSprite(96, 32, 64, 64, Assets.rightRegion);
		}
		this.batcher.drawSprite(480 - 40, 32, 64, 64, Assets.fireRegion);
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentGameOver() {
		GL10 gl = GL_GRAPHICS.getGL();
		
		this.guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		this.batcher.beginBatch(Assets.items);
		this.batcher.drawSprite(240, 160, 128, 64, Assets.gameOverRegion);
		Assets.font.drawText(this.batcher, this.scoreString, 10, 320 - 20);
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}
	
	@Override
	public void pause() {
		this.state = GAME_PAUSED;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resume() {
	}
}