package com.rubberdroid.sudoiku.generator;

import com.rubberdroid.sudoiku.model.Tile;

public interface SudokuGenerator {
	public Tile[] create(int diff);
}
