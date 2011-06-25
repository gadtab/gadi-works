package il.co.gadiworks.droidinvaders;

import il.co.gadiworks.games.framework.math.OverlapTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
	public interface WorldListener {
		public void explostion();
		
		public void shot();
	}
	
	final static float WORLD_MIN_X = -14;
	final static float WORLD_MAX_X =  14;
	final static float WORLD_MIN_Z = -15;
	
	WorldListener listener;
	int waves = 1;
	int score = 0;
	float speedMultiplier = 1;
	
	final List<Shot> SHOTS = new ArrayList<Shot>();
	final List<Invader> INVADERS = new ArrayList<Invader>();
	final List<Shield> SHIELDS = new ArrayList<Shield>();
	final Ship SHIP;
	
	long lastShotTime;
	Random random;
	
	public World() {
		this.SHIP = new Ship(0, 0, 0);
		generateInvaders();
		generateShields();
		this.lastShotTime = System.nanoTime();
		this.random = new Random();
	}

	private void generateInvaders() {
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 8; column++) {
				Invader invader = new Invader(-WORLD_MAX_X / 2 + column * 2f, 0, WORLD_MIN_Z + row * 2f);
				this.INVADERS.add(invader);
			}
		}
	}
	
	private void generateShields() {
		for (int shield = 0; shield < 3; shield++) {
			this.SHIELDS.add(new Shield(-10 + shield * 10 - 1, 0, -3));
			this.SHIELDS.add(new Shield(-10 + shield * 10 + 0, 0, -3));
			this.SHIELDS.add(new Shield(-10 + shield * 10 + 1, 0, -3));
			this.SHIELDS.add(new Shield(-10 + shield * 10 - 1, 0, -2));
			this.SHIELDS.add(new Shield(-10 + shield * 10 + 1, 0, -2));
		}
	}
	
	public void setWorldListener(WorldListener worldListener) {
		this.listener = worldListener;
	}
	
	public void update (float deltaTime, float accelX) {
		this.SHIP.update(deltaTime, accelX);
		updateInvaders(deltaTime);
		updateShots(deltaTime);
		
		checkShotCollisions();
		checkInvaderCollisions();
		
		if (this.INVADERS.size() == 0) {
			generateInvaders();
			this.waves++;
			this.speedMultiplier += 0.5f;
		}
	}

	private void updateInvaders(float deltaTime) {
		int len = this.INVADERS.size();
		for (int i = 0; i < len; i++) {
			Invader invader = this.INVADERS.get(i);
			invader.update(deltaTime, this.speedMultiplier);
			
			if (invader.state == Invader.INVADER_ALIVE) {
				if (this.random.nextFloat() < 0.001f) {
					Shot shot = new Shot(invader.POSITION.x, invader.POSITION.y, invader.POSITION.z, Shot.SHOT_VELOCITY);
					this.SHOTS.add(shot);
					this.listener.shot();
				}
			}
			
			if (invader.state == Invader.INVADER_DEAD && invader.stateTime > Invader.INVADER_EXPLOSION_TIME) {
				this.INVADERS.remove(i);
				i--;
				len--;
			}
		}
	}
	
	private void updateShots(float deltaTime) {
		int len = this.SHOTS.size();
		for (int i = 0; i < len; i++) {
			Shot shot = this.SHOTS.get(i);
			shot.update(deltaTime);
			if (shot.POSITION.z < WORLD_MIN_Z || shot.POSITION.z > 0) {
				this.SHOTS.remove(i);
				i--;
				len--;
			}
		}
	}
	
	private void checkInvaderCollisions() {
		if (this.SHIP.state == Ship.SHIP_EXPLODING) {
			return;
		}
		
		int len = this.INVADERS.size();
		for (int i = 0; i < len; i++) {
			Invader invader = this.INVADERS.get(i);
			if (OverlapTester.overlapSpheres(this.SHIP.BOUNDS, invader.BOUNDS)) {
				this.SHIP.lives = 1;
				this.SHIP.kill();
				return;
			}
		}
	}
	
	private void checkShotCollisions() {
		int len = this.SHOTS.size();
		for (int i = 0; i < len; i++) {
			Shot shot = this.SHOTS.get(i);
			boolean shotRemoved = false;
			
			int len2 = this.SHIELDS.size();
			for (int j = 0; j < len2; j++) {
				Shield shield = this.SHIELDS.get(j);
				if (OverlapTester.overlapSpheres(shield.BOUNDS, shot.BOUNDS)) {
					this.SHIELDS.remove(j);
					this.SHOTS.remove(i);
					i--;
					len--;
					shotRemoved = true;
					break;
				}
			}
			
			if (shotRemoved) {
				continue;
			}
			
			if (shot.VELOCITY.z < 0) {
				len2 = this.INVADERS.size();
				for (int j = 0; j < len2; j++) {
					Invader invader = this.INVADERS.get(j);
					if (OverlapTester.overlapSpheres(invader.BOUNDS, shot.BOUNDS) && invader.state == Invader.INVADER_ALIVE) {
						invader.kill();
						this.listener.explostion();
						this.score += 10;
						this.SHOTS.remove(i);
						i--;
						len--;
						break;
					}
				}
			}
			else {
				if (OverlapTester.overlapSpheres(shot.BOUNDS, this.SHIP.BOUNDS) && this.SHIP.state == Ship.SHIP_ALIVE) {
					this.SHIP.kill();
					this.listener.explostion();
					this.SHOTS.remove(i);
					i--;
					len--;
				}
			}
		}
	}
	
	public boolean isGameOver() {
		return this.SHIP.lives == 0;
	}
	
	public void shoot() {
		if (this.SHIP.state == Ship.SHIP_EXPLODING) {
			return;
		}
		
		int friendlyShots = 0;
		int len = this.SHOTS.size();
		for (int i = 0; i < len; i++) {
			if (this.SHOTS.get(i).VELOCITY.z < 0) {
				friendlyShots++;
			}
		}
		
		if (System.nanoTime() - this.lastShotTime > 1000000000 || friendlyShots == 0) {
			this.SHOTS.add(new Shot(this.SHIP.POSITION.x, this.SHIP.POSITION.y, this.SHIP.POSITION.z, -Shot.SHOT_VELOCITY));
			this.lastShotTime = System.nanoTime();
			this.listener.shot();
		}
	}
}
