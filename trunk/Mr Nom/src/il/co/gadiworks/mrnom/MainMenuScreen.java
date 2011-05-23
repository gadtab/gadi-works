package il.co.gadiworks.mrnom;

import java.util.List;

import il.co.gadiworks.mrnom.framework.Game;
import il.co.gadiworks.mrnom.framework.Graphics;
import il.co.gadiworks.mrnom.framework.Input.TouchEvent;
import il.co.gadiworks.mrnom.framework.Screen;

public class MainMenuScreen extends Screen {

	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = GAME.getGraphics();
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		GAME.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
					Settings.soundEnabled = !Settings.soundEnabled;
					if (Settings.soundEnabled) {
						Assets.click.play(1);
					}
				}
				if (inBounds(event, 64, 220, 192, 42)) {
					GAME.setScreen(new GameScreen(GAME));
					if (Settings.soundEnabled) {
						Assets.click.play(1);
					}
					return;
				}
				if(inBounds(event, 64, 220 + 42, 192, 42) ) {
					GAME.setScreen(new HighscoreScreen(GAME));
				    if(Settings.soundEnabled) {
				        Assets.click.play(1);
				    }
				    return;
				}
				if(inBounds(event, 64, 220 + 84, 192, 42) ) {
					GAME.setScreen(new HelpScreen(GAME));
				    if(Settings.soundEnabled) {
				        Assets.click.play(1);
				    }
				    return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = GAME.getGraphics();
		
		g.drawPixmap(Assets.background, 0, 0);
		g.drawPixmap(Assets.logo, 32, 20);
		g.drawPixmap(Assets.mainMenu, 64, 220);
		
		if (Settings.soundEnabled) {
			g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
		}
		else {
			g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
		}
	}

	@Override
	public void pause() {
		Settings.save(GAME.getFileIO());
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width -1 &&
			event.y > y && event.y < y + height - 1) {
			return true;
		}
		else {
			return false;
		}
	}
}