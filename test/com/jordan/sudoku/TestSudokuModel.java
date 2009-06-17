package com.jordan.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.rubberdroid.sudoiku.Sudoiku;
import com.rubberdroid.sudoiku.generator.SudokuGenerator;
import com.rubberdroid.sudoiku.model.SudokuModel;
import com.rubberdroid.sudoiku.model.Tile;
import com.rubberdroid.sudoku.util.GeneratorSupport;
import com.rubberdroid.sudoku.util.Pair;

public class TestSudokuModel {

	private SudokuModel model;

	@Before
	public void createTestContext() {
		model = new SudokuModel(Sudoiku.DIFFICULTY_EASY,
				new MySudokuGenerator());
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
			assertEquals(expectedSelected.x(), selectedTile.x());
			assertEquals(expectedSelected.y(), selectedTile.y());
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

	@Test
	public void modelShouldSayIfPuzzleIsFinished() {
		SudokuModel model = new SudokuModel(0, new SudokuGenerator() {
			public Tile[] create(int diff) {
				return GeneratorSupport
						.fromPuzzleString("038427651756981324241365798683542917172693845495178263324859176517236489869714532");
			}
		});
		
		assertFalse(model.isFinshed());
		model.setValueToTile(0, 0, 2);
		assertFalse(model.isFinshed());
		model.setValueToTile(0, 0, 9);
		assertTrue(model.isFinshed());
		model.setValueToTile(0, 0, 2);
		assertTrue(model.isFinshed());
		model.setValueToTile(0, 0, 0);
		assertFalse(model.isFinshed());
	}
}