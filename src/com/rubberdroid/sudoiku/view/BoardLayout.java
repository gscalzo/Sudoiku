package com.rubberdroid.sudoiku.view;

import com.rubberdroid.sudoiku.exception.SudokuException;
import com.rubberdroid.sudoiku.model.Tile;
import com.rubberdroid.sudoku.util.Pair;

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

	public boolean isInButtonsBoard(int x, int y) {
		int leftX = (int) (boardSide / 2 - tileSide);
		int rightX = (int) (boardSide / 2 + 2 * tileSide);
		int topY = (int) (boardSide + tileSide);
		int bottomY = (int) (boardSide + 3 * tileSide);
		return x >= leftX && x < rightX && y >= topY && y < bottomY;
	}

	public int touchedButton(int x) throws SudokuException {
		if (x < boardSide / 2 - tileSide || x >= boardSide / 2 + 2 * tileSide)
			throw new SudokuException("No Button touched!");
		if (x >= boardSide / 2 && x < boardSide / 2 + tileSide)
			return Tile.NOTES_BUTTON;
		if (x < boardSide / 2)
			return Tile.ERASE_BUTTON;
		return Tile.SOLVE_BUTTON;
	}
}
