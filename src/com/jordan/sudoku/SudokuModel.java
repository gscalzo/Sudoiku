package com.jordan.sudoku;

import java.util.Observable;

import android.util.Log;

import com.jordan.sudoku.util.Pair;

public class SudokuModel extends Observable {

	private Tile puzzle[] = new Tile[9 * 9];

	private final int used[][][] = new int[9][9][];
	private int selectedTileX;
	private int selectedTileY;

	private SudokuGenerator sudokuGenerator = new StaticSudokuGenerator();

	public SudokuModel(int diff) {
		init(diff);
	}

	private void init(int diff) {
		this.puzzle = sudokuGenerator.create(diff);
		calculateUsedTiles();
	}

	public SudokuModel(int diff, SudokuGenerator sudokuGenerator) {
		this.sudokuGenerator = sudokuGenerator;
		init(diff);
	}

	private void setTile(int x, int y, int value) {
		puzzle[y * 9 + x].setValue(value);
	}

	public Tile getTile(int x, int y) {
		return puzzle[y * 9 + x];
	}

	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}

	public boolean setValueToTileIfValid(int x, int y, int value) {
		if (getTile(x, y).isGiven() || isUsed(x, y, value))
			return false;

		setTile(x, y, value);
		calculateUsedTiles();
		setChanged();
		notifyObservers();
		return true;
	}

	public boolean isUsed(int x, int y, int value) {
		int tiles[] = getUsedTiles(x, y);
		if (value != 0) {
			for (int tile : tiles) {
				if (tile == value)
					return true;
			}
		}
		return false;
	}

	private void calculateUsedTiles() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = calculateUsedTiles(x, y);
			}
		}
	}

	private int[] calculateUsedTiles(int x, int y) {
		int c[] = new int[9];
		// horizontal
		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(x, i).value();
			if (t != 0)
				c[t - 1] = t;
		}
		// vertical
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTile(i, y).value();
			if (t != 0)
				c[t - 1] = t;
		}
		// same cell block
		int startx = (x / 3) * 3;
		int starty = (y / 3) * 3;
		for (int i = startx; i < startx + 3; i++) {
			for (int j = starty; j < starty + 3; j++) {
				if (i == x && j == y)
					continue;
				int t = getTile(i, j).value();
				if (t != 0)
					c[t - 1] = t;
			}
		}
		// compress
		int nused = 0;
		for (int t : c) {
			if (t != 0)
				nused++;
		}
		int c1[] = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0)
				c1[nused++] = t;
		}
		return c1;
	}

	static private String toPuzzleString(int[] puz) {
		   StringBuilder buf = new StringBuilder();
		   for (int element : puz) {
		      buf.append(element);
		   }
		   return buf.toString();
		}

	
	public void selectTile(int x, int y) {
		Pair previousPosition = new Pair(selectedTileX, selectedTileY);
		setNewPosition(x, y);
		Log.d(Game.TAG, "used[" + x + "][" + y + "] = "
				+ toPuzzleString(used[x][y]));
		notifyIfMoved(previousPosition);
	}

	private void setNewPosition(int x, int y) {
		selectedTileX = Math.min(Math.max(x, 0), 8);
		selectedTileY = Math.min(Math.max(y, 0), 8);
	}

	private void notifyIfMoved(Pair previousPosition) {
		if (selectedTileX != previousPosition.a()
				|| selectedTileY != previousPosition.b())
			setChanged();

		notifyObservers();
	}

	public void moveSelection(int diffX, int diffY) {
		selectTile(selectedTileX + diffX, selectedTileY + diffY);
	}

	public Tile selectedTile() {
		return getTile(selectedTileX, selectedTileY);
	}

}
