package com.jordan.sudoku;

class MySudokuGenerator implements SudokuGenerator {
	private final String puzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";

	private int[] fromPuzzleString(String string) {
		int[] puz = new int[string.length()];
		for (int i = 0; i < puz.length; i++) {
			puz[i] = string.charAt(i) - '0';
		}
		return puz;
	}

	public int[] create(int diff) {
		return fromPuzzleString(puzzle);
	}
}