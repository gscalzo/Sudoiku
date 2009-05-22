package com.jordan.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestTile {
	private SudokuModel model;

	@Before
	public void createTestContext() {
		model = new SudokuModel(Game.DIFFICULTY_EASY, new MySudokuGenerator());
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

	@Test
	public void aGivenTileShouldBeMarkedAsGiven() {
		Tile tile = model.getTile(0, 0);
		assertTrue(tile.isGiven());
	}

	@Test
	public void aNotGivenTileShouldNotBeMarkedAsGiven() {
		Tile tile = model.getTile(2, 0);
		assertEquals(0, tile.value());
		assertFalse(tile.isGiven());
	}

	@Test
	public void aNotGivenTileShouldBeModifiedIfPossibile() {
		assertTrue(model.setValueToTileIfValid(2, 0, 2));
		Tile tile = model.getTile(2, 0);
		assertEquals(2, tile.value());
	}

	@Test
	public void aGivenTileShouldNotBePossibleModify() {
		assertFalse(model.setValueToTileIfValid(1, 0, 7));
		Tile tile = model.getTile(1, 0);
		assertEquals(6, tile.value());
	}
}
