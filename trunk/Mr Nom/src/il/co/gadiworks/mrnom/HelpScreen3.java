package il.co.gadiworks.mrnom;

import java.util.List;

import il.co.gadiworks.mrnom.framework.Game;
import il.co.gadiworks.mrnom.framework.Graphics;
import il.co.gadiworks.mrnom.framework.Input.TouchEvent;
import il.co.gadiworks.mrnom.framework.Screen;

public class HelpScreen3 extends Screen {

	public HelpScreen3(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
		GAME.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                	GAME.setScreen(new MainMenuScreen(GAME));
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
        g.drawPixmap(Assets.help3, 64, 100);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 128, 64, 64);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
