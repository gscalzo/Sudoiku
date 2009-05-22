package com.jordan.sudoku;

import android.util.Log;

public class SudokuModel {

	private int puzzle[] = new int[9 * 9];

	private final int used[][][] = new int[9][9][];
	private int selectedTileX;
	private int selectedTileY;

	private SudokuGenerator sudokuGenerator = new StaticSudokuGenerator();

	public SudokuModel(int diff) {
		this.puzzle = sudokuGenerator.create(diff);
		calculateUsedTiles();
	}

	private void setTile(int x, int y, int value) {
		puzzle[y * 9 + x] = value;
	}

	private int getTileInt(int x, int y) {
		return puzzle[y * 9 + x];
	}
	
	public Tile getTile(int x, int y) {
		int valueAt=puzzle[y * 9 + x];
		Tile tile = new Tile(x,y,valueAt);
		
		return tile;
	}


	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}

	public boolean setTileIfValid(int value) {
		Log.d(Game.TAG, String.valueOf(value));
		int tiles[] = getUsedTiles(selectedTileX, selectedTileY);
		if (value != 0) {
			for (int tile : tiles) {
				if (tile == value)
					return false;
			}
		}
		setTile(selectedTileX, selectedTileY, value);
		calculateUsedTiles();
		// TODO
		// implementare update!!
		return true;
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
			int t = getTileInt(x, i);
			if (t != 0)
				c[t - 1] = t;
		}
		// vertical
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTileInt(i, y);
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
				int t = getTileInt(i, j);
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

	public void selectTile(int x, int y) {
		selectedTileX = Math.min(Math.max(x, 0), 8);
		selectedTileY = Math.min(Math.max(y, 0), 8);
		// TODO
		// implementare update!!
	}

	public boolean moveSelection(int diffX, int diffY) {
		selectTile(selectedTileX + diffX, selectedTileY + diffY);

		// TODO
		// implementare update!!
		return true;
	}

	public Tile selectedTile() {
		return new Tile(selectedTileX, selectedTileY);
	}

	public void setGenerator(MySudokuGenerator sudokuGenerator) {
		this.sudokuGenerator = sudokuGenerator;
	}

}
