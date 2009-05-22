package com.jordan.sudoku;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StaticSudokuGenerator implements SudokuGenerator {
	private final String easyPuzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";
	private final String mediumPuzzle = "650000070000506000014000005"
			+ "007009000002314700000700800" + "500000630000201000030000097";
	private final String hardPuzzle = "009000000080605020501078000"
			+ "000000700706040102004000000" + "000720903090301080000000600";

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
		String puz;
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

}
