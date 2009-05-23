package com.jordan.sudoku;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestSudokuDimension {

	@Test
	public void aPointShouldBeInsideBoardIfInGrid() {
		SudokuDimensions dim = new SudokuDimensions(90,100);
		assertTrue(dim.isInBoard(10, 20));
		assertTrue(dim.isInBoard(0, 20));
		assertTrue(dim.isInBoard(89, 20));
		assertTrue(dim.isInBoard(10, 89));

	}

	@Test
	public void aPointShouldBeOutsideBoardIfOffGrid() {
		SudokuDimensions dim = new SudokuDimensions(90,100);
		assertFalse(dim.isInBoard(10, 90));
		assertFalse(dim.isInBoard(-10, 20));
		assertFalse(dim.isInBoard(90, 20));
	}

}
