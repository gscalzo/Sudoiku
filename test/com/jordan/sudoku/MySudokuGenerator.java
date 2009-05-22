package com.jordan.sudoku;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

class MySudokuGenerator implements SudokuGenerator {
	private final String puzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";

	private Tile[] fromPuzzleString(String puzzleAsString) {
		Tile[] puzzle = new Tile[puzzleAsString.length()];
		CharacterIterator charIterator = new StringCharacterIterator(
				puzzleAsString);
		for (int y = 0; y < 9; ++y) {
			for (int x = 0; x < 9; ++x) {
				puzzle[y * 9 + x] = new Tile(x, y, charIterator.current() - '0');
				charIterator.next();
			}
		}
		return puzzle;
	}

	public Tile[] create(int diff) {
		return fromPuzzleString(puzzle);
	}
}