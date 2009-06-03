package com.jordan.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rubberdroid.sudoiku.Sudoiku;
import com.rubberdroid.sudoiku.model.SudokuModel;
import com.rubberdroid.sudoiku.model.Tile;

public class TestTile {
	private SudokuModel model;

	@Before
	public void createTestContext() {
		model = new SudokuModel(Sudoiku.DIFFICULTY_EASY,
				new MySudokuGenerator());
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

	@Test
	public void aTileShouldBeEqualsToAnotherIfPositionAndValueAreEqual() {
		assertFalse(new Tile(1, 2, 3).equals(new Tile(1, 2, 4)));
		assertTrue(new Tile(1, 2, 3).equals(new Tile(1, 2, 3)));
	}

	@Test
	public void aTileShouldBeEqualsToAnotherIfPositionValueAndGivenessAreEqual() {
		Tile givenOne = new Tile(1, 2, 3);
		Tile notGivenOne = new Tile(1, 2, 0);
		notGivenOne.setValue(3);
		assertFalse(givenOne.equals(notGivenOne));
		Tile notGivenTwo = new Tile(1, 2, 0);
		notGivenTwo.setValue(3);
		assertTrue(notGivenTwo.equals(notGivenOne));
	}

	@Test
	public void aTileShouldBeEqualsToAnotherIfPositionValueGivenessAndNotesAreEqual() {
		Tile notGivenOne = new Tile(1, 2, 0);
		Tile notGivenTwo = new Tile(1, 2, 0);
		notGivenTwo.toggleNoteAt(1);
		notGivenTwo.toggleNoteAt(3);
		assertFalse(notGivenTwo.equals(notGivenOne));
	}

	@Test
	public void aTileShouldBeSerializable() throws IOException,
			ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Tile originalTile = createATile();
		Serializer.serialize(originalTile, baos);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Tile deserializedTile = (Tile) Serializer.deserialize(bais);
		assertTrue(deserializedTile.equals(originalTile));
	}

	private Tile createATile() {
		Tile aTile = new Tile(1, 2, 0);
		aTile.setValue(3);
		aTile.toggleNoteAt(1);
		aTile.toggleNoteAt(3);
		return aTile;
	}

}
