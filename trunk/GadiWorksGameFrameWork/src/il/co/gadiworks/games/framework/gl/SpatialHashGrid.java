package il.co.gadiworks.games.framework.gl;


import il.co.gadiworks.games.framework.GameObject;

import java.util.ArrayList;
import java.util.List;

import android.util.FloatMath;

public class SpatialHashGrid {
	List<GameObject>[] dynamicCells;
	List<GameObject>[] staticCells;
	int cellsPerRow;
	int cellsPerCol;
	float cellSize;
	int[] cellIds = new int[4];
	List<GameObject> foundObjects;
	
	@SuppressWarnings("unchecked")
	public SpatialHashGrid(float worldWidth, float worldHeight, float cellSize) {
		this.cellSize = cellSize;
		this.cellsPerRow = (int) FloatMath.ceil(worldWidth / cellSize);
		this.cellsPerCol = (int) FloatMath.ceil(worldHeight / cellSize);
		
		int numCells = this.cellsPerRow * this.cellsPerCol;
		
		this.dynamicCells = new List[numCells];
		this.staticCells = new List[numCells];
		
		for (int i = 0; i < numCells; i++) {
			this.dynamicCells[i] = new ArrayList<GameObject>(10);
			this.staticCells[i] = new ArrayList<GameObject>(10);
		}
		this.foundObjects = new ArrayList<GameObject>(10);
	}
	
	public void insertStaticObject(GameObject obj) {
		int[] cellIds = getCellIds(obj);
		int i = 0;
		int cellId = -1;
		
		while (i <= 3 && (cellId = cellIds[i++]) != -1) {
			this.staticCells[cellId].add(obj);
		}
	}
	
	public void insertDynamicObject(GameObject obj) {
		int[] cellIds = getCellIds(obj);
		int i = 0;
		int cellId = -1;
		
		while (i <= 3 && (cellId = cellIds[i++]) != -1) {
			this.dynamicCells[cellId].add(obj);
		}
	}
	
	public void removeObject(GameObject obj) {
		int[] cellIds = getCellIds(obj);
		int i = 0;
		int cellId = -1;
		
		while (i <= 3 && (cellId = cellIds[i++]) != -1) {
			this.dynamicCells[cellId].remove(obj);
			this.staticCells[cellId].remove(obj);
		}
	}
	
	public void clearDynamicCells(GameObject obj) {
		int len = this.dynamicCells.length;
		
		for (int i = 0; i < len; i++) {
			this.dynamicCells[i].clear();
		}
	}
	
	public List<GameObject> getPotentialColliders(GameObject obj) {
		this.foundObjects.clear();
		
		int[] cellIds = getCellIds(obj);
		int i = 0;
		int cellId = -1;
		
		while (i <= 3 && (cellId = cellIds[i++]) != -1) {
			int len = this.dynamicCells[cellId].size();
			for (int j = 0; j < len; j++) {
				GameObject collider = this.dynamicCells[cellId].get(j);
				if (!this.foundObjects.contains(collider)) {
					this.foundObjects.add(collider);
				}
			}
			
			len = this.staticCells[cellId].size();
			for (int j = 0; j < len; j++) {
				GameObject collider = this.staticCells[cellId].get(j);
				if (!this.foundObjects.contains(collider)) {
					this.foundObjects.add(collider);
				}
			}
		}
		
		return this.foundObjects;
	}
	
	public int[] getCellIds(GameObject obj) {
		int x1 = (int) FloatMath.floor(obj.BOUNDS.LOWER_LEFT.x / this.cellSize);
		int y1 = (int) FloatMath.floor(obj.BOUNDS.LOWER_LEFT.y / this.cellSize);
		int x2 = (int) FloatMath.floor((obj.BOUNDS.LOWER_LEFT.x + obj.BOUNDS.width) / this.cellSize);
		int y2 = (int) FloatMath.floor((obj.BOUNDS.LOWER_LEFT.y + obj.BOUNDS.height) / this.cellSize);
		
		if (x1 == x2 && y1 == y2) {
			if (x1 >= 0 && x1 < this.cellsPerRow && y1 >= 0 && y1 < this.cellsPerCol) {
				this.cellIds[0] = x1 + y1 * this.cellsPerRow;
			}
			else {
				this.cellIds[0] = -1;
			}
			
			this.cellIds[1] = -1;
			this.cellIds[2] = -1;
			this.cellIds[3] = -1;
		}
		else if (x1 == x2) {
			int i = 0;
			if (x1 >= 0 && x1 < this.cellsPerRow) {
				if (y1 >= 0 && y1 < this.cellsPerCol) {
					this.cellIds[i++] = x1 + y1 * this.cellsPerRow;
				}
				if (y2 >= 0 && y2 < this.cellsPerCol) {
					this.cellIds[i++] = x1 + y2 * this.cellsPerRow;
				}
			}
			while (i <= 3) {
				this.cellIds[i++] = -1;
			}
		}
		else if (y1 == y2) {
			int i = 0;
			if (y1 >= 0 && y1 < this.cellsPerCol) {
				if (x1 >= 0 && x1 < this.cellsPerRow) {
					this.cellIds[i++] = x1 + y1 * this.cellsPerRow;
				}
				if (x2 >= 0 && x2 < this.cellsPerRow) {
					this.cellIds[i++] = x2 + y1 * this.cellsPerRow;
				}
			}
			while (i <= 3) {
				this.cellIds[i++] = -1;
			}
		}
		else {
			int i = 0;
			int y1CellsPerRow = y1 * this.cellsPerRow;
			int y2CellsPerRow = y2 * this.cellsPerRow;
			
			if (x1 >= 0 && x1 < this.cellsPerRow && y1 >= 0 && y1 < this.cellsPerCol) {
				this.cellIds[i++] = x1 + y1CellsPerRow;
			}
			if (x2 >= 0 && x2 < this.cellsPerRow && y1 >= 0 && y1 < this.cellsPerCol) {
				this.cellIds[i++] = x2 + y1CellsPerRow;
			}
			if (x2 >= 0 && x2 < this.cellsPerRow && y2 >= 0 && y2 < this.cellsPerCol) {
				this.cellIds[i++] = x2 + y2CellsPerRow;
			}
			if (x1 >= 0 && x1 < this.cellsPerRow && y2 >= 0 && y2 < this.cellsPerCol) {
				this.cellIds[i++] = x1 + y2CellsPerRow;
			}
			
			while (i <= 3) {
				this.cellIds[i++] = -1;
			}
		}
		
		return this.cellIds;
	}
}