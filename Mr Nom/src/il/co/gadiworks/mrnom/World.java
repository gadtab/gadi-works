package il.co.gadiworks.mrnom;

import java.util.Random;

public class World {
	static final int WORLD_WIDTH = 10;
	static final int WORLD_HEIGHT = 13;
	static final int SCORE_INCREMENT = 10;
	
	static final float TICK_INITIAL = 0.5f;
	static final float TICK_DECREMENT = 0.05f;
	
	public Snake snake;
	public Stain stain;
	public boolean gameOver = false;
	public int score = 0;
	
	boolean[][] fields = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	Random random = new Random();
	float tickTime = 0;
	static float tick = TICK_INITIAL;
	
	public World() {
		this.snake = new Snake();
		placeStain();
	}
	
	private void placeStain() {
		for (int x = 0; x < WORLD_WIDTH; x++) {
			for (int y = 0; y < WORLD_HEIGHT; y++) {
				this.fields[x][y] = false;
			}
		}
		
		int len = this.snake.parts.size();
		for (int i = 0; i < len; i++) {
			SnakePart part = this.snake.parts.get(i);
			this.fields[part.x][part.y] = true;
		}
		
		int stainX = this.random.nextInt(WORLD_WIDTH);
		int stainY = this.random.nextInt(WORLD_HEIGHT);
		
		while (true) {
			if (this.fields[stainX][stainY] == false) {
				break;
			}
			
			stainX++;
			
			if (stainX >= WORLD_WIDTH) {
				stainX = 0;
				stainY++;
				if (stainY >= WORLD_HEIGHT) {
					stainY = 0;
				}
			}
		}
		
		this.stain = new Stain(stainX, stainY, random.nextInt(3));
	}
	
	public void update(float deltaTime) {
		if (this.gameOver) {
			return;
		}
		
		this.tickTime += deltaTime;
		
		while (this.tickTime > tick) {
			this.tickTime -= tick;
			this.snake.advance();
			
			if (this.snake.checkBitten()) {
				this.gameOver = true;
				return;
			}
			
			SnakePart head = this.snake.parts.get(0);
			
			if (head.x == this.stain.x && head.y == this.stain.y) {
				this.score += SCORE_INCREMENT;
				this.snake.eat();
				
				if (this.snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
					this.gameOver = true;
					return;
				}
				else {
					placeStain();
				}
				
				if (this.score % 100 == 0 && tick - TICK_DECREMENT > 0) {
					tick -= TICK_DECREMENT;
				}
			}
		}
	}
}
