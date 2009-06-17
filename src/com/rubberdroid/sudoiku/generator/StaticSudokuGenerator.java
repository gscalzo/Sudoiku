package com.rubberdroid.sudoiku.generator;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import com.rubberdroid.sudoiku.Sudoiku;
import com.rubberdroid.sudoiku.model.Tile;
import com.rubberdroid.sudoku.util.GeneratorSupport;

public class StaticSudokuGenerator implements SudokuGenerator {
	private final String easyPuzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";
	private final String mediumPuzzle = "650000070000506000014000005"
			+ "007009000002314700000700800" + "500000630000201000030000097";
	private final String hardPuzzle = "009000000080605020501078000"
			+ "000000700706040102004000000" + "000720903090301080000000600";


	public Tile[] create(int diff) {
		String puz;
		switch (diff) {
		case Sudoiku.DIFFICULTY_HARD:
			puz = hardPuzzle;
			break;
		case Sudoiku.DIFFICULTY_MEDIUM:
			puz = mediumPuzzle;
			break;
		case Sudoiku.DIFFICULTY_EASY:
		default:
			puz = easyPuzzle;
			break;
		}
		return GeneratorSupport.fromPuzzleString(puz);
	}

}
