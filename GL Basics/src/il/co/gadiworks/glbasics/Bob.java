package il.co.gadiworks.glbasics;

import java.util.Random;

class Bob {
	static final Random RAND = new Random();
	public float x, y;
	float dirX, dirY;
	
	public Bob() {
		this.x = this.RAND.nextFloat() * 320;
		this.y = this.RAND.nextFloat() * 480;
		
		this.dirX = 50;
		this.dirY = 50;
	}
	
	public void update(float deltaTime) {
		this.x += this.dirX * deltaTime;
		this.y += this.dirY * deltaTime;
		
		if (this.x < 0) {
			this.dirX = -this.dirX;
			this.x = 0;
		}
		
		if (this.x > 320) {
			this.dirX = -this.dirX;
			this.x = 320;
		}
		
		if (this.y < 0) {
			this.dirY = -this.dirY;
			this.y = 0;
		}
		
		if (this.y > 480) {
			this.dirY = -this.dirY;
			this.y = 480;
		}
	}
}
