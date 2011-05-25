package il.co.gadiworks.mrnom;

import java.util.ArrayList;
import java.util.List;

public class Snake {
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	
	public List<SnakePart> parts = new ArrayList<SnakePart>();
	public int direction;
	
	public Snake() {
		this.direction = UP;
		this.parts.add(new SnakePart(5, 6));
		this.parts.add(new SnakePart(5, 7));
		this.parts.add(new SnakePart(5, 8));
	}
	
	public void turnLeft() {
		this.direction += 1;
		if (this.direction > RIGHT) {
			this.direction = UP;
		}
	}
	
	public void turnRight() {
		this.direction -= 1;
		if (this.direction < UP) {
			this.direction = RIGHT;
		}
	}
	
	public void eat() {
		SnakePart end = this.parts.get(this.parts.size() - 1);
		this.parts.add(new SnakePart(end.x, end.y));
	}
	
	public void advance() {
		SnakePart head = this.parts.get(0);
		
		int len = this.parts.size() - 1;
		for (int i = len; i > 0; i--) {
			SnakePart before = this.parts.get(i - 1);
			SnakePart part = this.parts.get(i);
			
			part.x = before.x;
			part.y = before.y;
		}
		
		if (this.direction == UP) {
			head.y -= 1;
		}
		else if (this.direction == LEFT) {
			head.x -= 1;
		}
		else if (this.direction == DOWN) {
			head.y += 1;
		}
		else if (this.direction == RIGHT) {
			head.x += 1;
		}
		
		if (head.x < 0) {
			head.x = 9;
		}
		else if (head.x > 9) {
			head.x  = 0;
		}
		
		if (head.y < 0) {
			head.y = 12;
		}
		else if (head.y > 12) {
			head.y = 0;
		}
	}
	
	public boolean checkBitten() {
		int len = this.parts.size();
		
		SnakePart head = this.parts.get(0);
		for (int i = 1; i < len; i++) {
			SnakePart part = this.parts.get(i);
			
			if (part.x == head.x && part.y == head.y) {
				return true;
			}
		}
		
		return false;
	}
}
