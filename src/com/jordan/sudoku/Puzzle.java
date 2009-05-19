package com.jordan.sudoku;

import android.graphics.Point;
import android.util.Log;

public class Puzzle {
	private final String easyPuzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";
	private final String mediumPuzzle = "650000070000506000014000005"
			+ "007009000002314700000700800" + "500000630000201000030000097";
	private final String hardPuzzle = "009000000080605020501078000"
			+ "000000700706040102004000000" + "000720903090301080000000600";

	private int puzzle[] = new int[9 * 9];

	private final int used[][][] = new int[9][9][];
	private int selectedTileX;
	private int selectedTileY;

	public Puzzle(int diff) {

		this.puzzle = newPuzzle(diff);
		calculateUsedTiles();
	}

	/** Convert an array into a puzzle string */
	// static private String toPuzzleString(int[] puz) {
	// StringBuilder buf = new StringBuilder();
	// for (int element : puz) {
	// buf.append(element);
	// }
	// return buf.toString();
	// }
	public int[] newPuzzle(int diff) {
		String puz;
		// TODO: Continue last game
		switch (diff) {
		case Game.DIFFICULTY_HARD:
			puz = hardPuzzle;
			break;
		case Game.DIFFICULTY_MEDIUM:
			puz = mediumPuzzle;
			break;
		case Game.DIFFICULTY_EASY:
		default:
			puz = easyPuzzle;
			break;
		}
		return fromPuzzleString(puz);
	}

	static protected int[] fromPuzzleString(String string) {
		int[] puz = new int[string.length()];
		for (int i = 0; i < puz.length; i++) {
			puz[i] = string.charAt(i) - '0';
		}
		return puz;
	}

	private void setTile(int x, int y, int value) {
		puzzle[y * 9 + x] = value;
	}

	private int getTile(int x, int y) {
		return puzzle[y * 9 + x];
	}

	public String getTileString(int x, int y) {
		int v = getTile(x, y);
		if (v == 0)
			return "";
		else
			return String.valueOf(v);
	}

	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}

	boolean setTileIfValid(int value) {
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
		return true;
	}

	private void calculateUsedTiles() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = calculateUsedTiles(x, y);
				// Log.d("EEE", "used[" + x + "][" + y + "] = "
				// + toPuzzleString(used[x][y]));
			}
		}
	}

	private int[] calculateUsedTiles(int x, int y) {
		int c[] = new int[9];
		// horizontal
		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(x, i);
			if (t != 0)
				c[t - 1] = t;
		}
		// vertical
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTile(i, y);
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
				int t = getTile(i, j);
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
	}

	public boolean moveSelection(int diffX, int diffY) {
		selectTile(selectedTileX + diffX, selectedTileY + diffY);

		// TODO
		// ritornare true solo se effettivamente cambiato
		return true;
	}

	public Point getTileSelected() {
		return new Point(selectedTileX, selectedTileY);
	}

}
