package com.rubberdroid.sudoku.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import com.rubberdroid.sudoiku.model.Tile;

public class GeneratorSupport {
	public static Tile[] fromPuzzleString(String puzzleAsString) {
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

}
