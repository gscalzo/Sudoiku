package com.jordan.sudoku;

import com.rubberdroid.sudoiku.generator.SudokuGenerator;
import com.rubberdroid.sudoiku.model.Tile;
import com.rubberdroid.sudoku.util.GeneratorSupport;

class MySudokuGenerator implements SudokuGenerator {
	private final String puzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";


	public Tile[] create(int diff) {
		return GeneratorSupport.fromPuzzleString(puzzle);
	}
}