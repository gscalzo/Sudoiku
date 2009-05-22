package com.jordan.sudoku;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestTile {
	private SudokuModel model;

	@Before
	public void createTestContext() {
		model = new SudokuModel(Game.DIFFICULTY_EASY);
		model.setGenerator(new MySudokuGenerator());
	}

	@Test
	public void aTileShouldHaveAValue() {
		Tile tile = model.getTile(0, 0);
		assertEquals(3, tile.value());
	}
	
	@Test
	public void aTileShouldReturnValueAsString() {
		Tile tile = model.getTile(0, 0);
		assertEquals("3", tile.valueAsString());
	}
	
	@Test
	public void aTileShouldReturnEmptyStringIfZero() {
		Tile tile = model.getTile(2, 0);
		assertEquals("", tile.valueAsString());
	}
}
