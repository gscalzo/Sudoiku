package com.jordan.sudoku;

public class SudokuDimensions {
	int boardSide;
int screenHeight;
	float tileSide;

	public SudokuDimensions(int boardWidth, int screenHeight) {
		this.boardSide = boardWidth;
		this.screenHeight=screenHeight;
		this.tileSide = boardSide / 9f;
	}

	public boolean isInBoard(int x, int y) {
		return x >= 0 && x < boardSide && y > 00 && y < boardSide;
	}

}
