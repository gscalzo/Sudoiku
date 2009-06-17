package com.rubberdroid.sudoiku.model;

import java.io.Serializable;
import java.util.Observable;

import com.rubberdroid.sudoiku.R;
import com.rubberdroid.sudoiku.generator.StaticSudokuGenerator;
import com.rubberdroid.sudoiku.generator.SudokuGenerator;
import com.rubberdroid.sudoiku.view.ToastMsg;
import com.rubberdroid.sudoku.util.Pair;

public class SudokuModel extends Observable implements Serializable {
	private static final long serialVersionUID = 89590486528465331L;

	private Tile puzzle[] = new Tile[9 * 9];

	private final int used[][][] = new int[9][9][];
	private int selectedTileX;
	private int selectedTileY;

	private transient SudokuGenerator sudokuGenerator = new StaticSudokuGenerator();

	private boolean notesMode;

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

	public int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}

	public void setValueToTile(int x, int y, int value) {
		if (isNumbersMode())
			setNumberToTile(x, y, value);
		else
			setNoteToTile(x, y, value);
	}

	public void resetNotes() {
		selectedTile().resetNotes();
		setChanged();
		notifyObservers();
	}

	private void setNumberToTile(int x, int y, int value) {
		setChanged();
		if (getTile(x, y).isGiven() || isUsed(x, y, value)) {
			notifyObservers(new ToastMsg("Invalid entry"));
			return;
		}
		setTile(x, y, value);
		calculateUsedTiles();
		notifyObservers();
	}

	private void setNoteToTile(int x, int y, int value) {
		if (value == 0) {
			resetNotes();
		} else {
			Tile tile = getTile(x, y);
			tile.toggleNoteAt(value);
		}
		setChanged();
		notifyObservers();
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

	// static private String toPuzzleString(int[] puz) {
	// StringBuilder buf = new StringBuilder();
	// for (int element : puz) {
	// buf.append(element);
	// }
	// return buf.toString();
	// }

	public void selectTile(int x, int y) {
		Pair previousPosition = new Pair(selectedTileX, selectedTileY);
		setNewPosition(x, y);
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

	public boolean isNotesMode() {
		return notesMode;
	}

	public void setInNotesMode() {
		setNotesMode(true);
	}

	private void setNotesMode(boolean value) {
		notesMode = value;
		setChanged();
		notifyObservers();
	}

	public void setInNumbersMode() {
		setNotesMode(false);
	}

	public boolean isNumbersMode() {
		return !notesMode;
	}

	public void solve() {
		setChanged();
		notifyObservers(new ToastMsg("Solve!"));

		// char[] puzzle = new char[81];
		// for (int i = 0; i < 81; ++i) {
		// puzzle[i] = (char) ((char) (this.puzzle[i].value()) + '0');
		// }
		// S.A = puzzle;
		// S.R();
		// for (int i = 0; i < 81; ++i) {
		// this.puzzle[i].setValue(puzzle[i] - '0');
		// }
		// setChanged();
		// notifyObservers();
	}

	public boolean isSelected(int i) {
		return (selectedTileY * 9 + selectedTileX) == i;
	}

	public boolean isFinshed() {
		for (int x = 0; x < 9; ++x)
			for (int y = 0; y < 9; ++y)
				if (getTile(x, y).value()==0)
					return false;

		return true;
	}

}
