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
		model.setValueToTile(2, 0, 2);
		Tile tile = model.getTile(2, 0);
		assertEquals(2, tile.value());
	}

	@Test
	public void aGivenTileShouldNotBePossibleModify() {
		model.setValueToTile(1, 0, 7);
		Tile tile = model.getTile(1, 0);
		assertEquals(6, tile.value());
	}

	@Test
	public void itShouldBePossibleAskATilesNote() {
		Tile tile = model.getTile(1, 0);
		assertFalse(tile.noteActiveAt(3));
	}

	@Test
	public void itShouldBePossibleAddANoteToATile() {
		Tile tile = model.getTile(1, 0);
		assertFalse(tile.noteActiveAt(3));
		tile.toggleNoteAt(3);
		assertTrue(tile.noteActiveAt(3));
	}

	@Test
	public void itShouldBePossibleAddANoteToTileBoundairies() {
		Tile tile = model.getTile(1, 0);
		assertFalse(tile.noteActiveAt(1));
		tile.toggleNoteAt(1);
		assertTrue(tile.noteActiveAt(1));
		assertFalse(tile.noteActiveAt(9));
		tile.toggleNoteAt(9);
		assertTrue(tile.noteActiveAt(9));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void itShouldNotBePossibleAddANoteOffLowBoundairy() {
		model.getTile(1, 0).toggleNoteAt(0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void itShouldNotBePossibleAddANoteOffUpBoundairy() {
		model.getTile(1, 0).toggleNoteAt(10);
	}

	@Test
	public void valueZeroShouldResetAllNotes() {
		Tile tile = model.getTile(1, 0);
		for (int i = 1; i <= 9; ++i)
			tile.toggleNoteAt(i);
		for (int i = 1; i <= 9; ++i)
			assertTrue(tile.noteActiveAt(i));
		tile.resetNotes();

		for (int i = 1; i <= 9; ++i)
			assertFalse(tile.noteActiveAt(i));
	}

}
