package com.jordan.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jordan.sudoku.util.Pair;

public class TestSudokuModel {

	private SudokuModel model;

	@Before
	public void createTestContext() {
		model = new SudokuModel(Game.DIFFICULTY_EASY, new MySudokuGenerator());
	}

	@Test
	public void selectedTitleShouldBeAlwaysInsideTheBoard() {
		model.selectTile(-1, 3);
		Tile selectedTile = model.selectedTile();
		assertEquals(0, selectedTile.x());
		assertEquals(3, selectedTile.y());
	}

	@Test
	public void selectedTileShouldMoveCorrectely() {
		Map<Pair, Tile> values = new HashMap<Pair, Tile>();

		values.put(new Pair(0, -1), new Tile(3, 2));
		values.put(new Pair(0, 1), new Tile(3, 4));
		values.put(new Pair(1, 0), new Tile(4, 3));
		values.put(new Pair(-1, 0), new Tile(2, 3));
		values.put(new Pair(-5, 0), new Tile(0, 3));
		values.put(new Pair(15, 0), new Tile(8, 3));
		values.put(new Pair(0, -5), new Tile(3, 0));
		values.put(new Pair(0, 15), new Tile(3, 8));

		for (Pair move : values.keySet()) {
			model.selectTile(3, 3);
			model.moveSelection(move.a(), move.b());
			Tile selectedTile = model.selectedTile();
			Tile expectedSelected = values.get(move);
			assertEquals(expectedSelected, selectedTile);
		}
	}

	@Test
	public void asDefaultModelShouldBeInNumbersMode() {
		assertFalse(model.isNotesMode());
	}

	@Test
	public void whenNotesModeSelectedModelShouldBeInNotesMode() {
		model.setInNotesMode();
		assertTrue(model.isNotesMode());
	}

	@Test
	public void whenNotesModeThanNumbersModeIsSelectedModelShouldNotBeBeInNotesMode() {
		model.setInNotesMode();
		assertTrue(model.isNotesMode());
		assertFalse(model.isNumbersMode());
		model.setInNumbersMode();
		assertTrue(model.isNumbersMode());
		assertFalse(model.isNotesMode());
	}
}