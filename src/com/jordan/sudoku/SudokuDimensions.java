package com.jordan.sudoku;

public class SudokuDimensions {
	int boardSide;
	float tileSide;

	public SudokuDimensions(int boardWidth) {
		this.boardSide = boardWidth;
		this.tileSide = boardSide/9f;
	}

}
