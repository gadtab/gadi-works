package il.co.gadiworks.games.framework;

public abstract class Screen {
	protected final Game GAME;
	
	public Screen (Game game) {
		this.GAME = game;
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void present(float deltaTime);
	
	public abstract void pause();

	public abstract void resume();
	
	public abstract void dispose();
}
