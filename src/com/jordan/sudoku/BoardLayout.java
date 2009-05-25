package com.jordan.sudoku;

import com.jordan.sudoku.util.Pair;

public class BoardLayout {
	int boardSide;
	int screenHeight;
	float tileSide;

	public BoardLayout(int boardWidth, int screenHeight) {
		this.boardSide = boardWidth;
		this.screenHeight = screenHeight;
		this.tileSide = boardSide / 9f;
	}

	public boolean isInGrid(int x, int y) {
		return x >= 0 && x < boardSide && y > 00 && y < boardSide;
	}

	public Pair touchedTile(int x, int y) throws SudokuException {
		if (!isInGrid(x, y))
			throw new SudokuException("No Tile touched!");

		int xTouched = (int) (x / tileSide);
		int yTouched = (int) (y / tileSide);

		return new Pair(xTouched, yTouched);
	}

	public boolean isInKeyboard(int x, int y) {
		return x >= 0 && x < boardSide && y >= boardSide + tileSide
				&& y < boardSide + 2 * tileSide;
	}

	public int touchedNumber(int x) throws SudokuException {
		if (x < 0 || x >= boardSide)
			throw new SudokuException("No Number touched!");
		return (int) (x / tileSide) + 1;
	}

}
