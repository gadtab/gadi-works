package il.co.gadiworks.games.framework.impl;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;

public abstract class GLScreen extends Screen {
	protected final GLGraphics GL_GRAPHICS;
	protected final GLGame GL_GAME;

	public GLScreen(Game game) {
		super(game);
		
		this.GL_GAME = (GLGame) game;
		this.GL_GRAPHICS = ((GLGame) game).getGLGraphics();
	}
}
