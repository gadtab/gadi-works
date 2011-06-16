package il.co.gadiworks.jumper;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Input.TouchEvent;
import il.co.gadiworks.games.framework.gl.Camera2D;
import il.co.gadiworks.games.framework.gl.SpriteBatcher;
import il.co.gadiworks.games.framework.gl.Texture;
import il.co.gadiworks.games.framework.gl.TextureRegion;
import il.co.gadiworks.games.framework.impl.GLScreen;
import il.co.gadiworks.games.framework.math.OverlapTester;
import il.co.gadiworks.games.framework.math.Rectangle;
import il.co.gadiworks.games.framework.math.Vector2;

public class HelpScreen5 extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle nextBounds;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;

	public HelpScreen5(Game game) {
		super(game);
		
		this.guiCam = new Camera2D(GL_GRAPHICS, 320, 480);
		this.nextBounds = new Rectangle(320 - 64, 0, 64, 64);
		this.touchPoint = new Vector2();
		this.batcher = new SpriteBatcher(GL_GRAPHICS, 1);
	}
	
	@Override
	public void resume() {
		this.helpImage = new Texture(GL_GAME, "help5.png");
		this.helpRegion = new TextureRegion(this.helpImage, 0, 0, 320, 480);
	}
	
	@Override
	public void pause() {
		this.helpImage.dispose();
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
				if (OverlapTester.pointInRectangle(this.nextBounds, this.touchPoint)) {
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
		
		this.batcher.beginBatch(this.helpImage);
		this.batcher.drawSprite(160, 240, 320, 480, this.helpRegion);
		this.batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		this.batcher.beginBatch(Assets.items);
		this.batcher.drawSprite(320 - 32, 32, -64, 64, Assets.arrow);
		this.batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void dispose() {
	}
}