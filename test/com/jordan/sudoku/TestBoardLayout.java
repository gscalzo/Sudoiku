package com.jordan.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jordan.sudoku.util.Pair;

public class TestBoardLayout {
	private BoardLayout layout;

	@Before
	public void setUp() {
		layout = new BoardLayout(90, 150);
	}

	@Test
	public void aPointShouldBeInsideBoardIfInGrid() {
		assertTrue(layout.isInGrid(10, 20));
		assertTrue(layout.isInGrid(0, 20));
		assertTrue(layout.isInGrid(89, 20));
		assertTrue(layout.isInGrid(10, 89));

	}

	@Test
	public void aPointShouldBeOutsideBoardIfOffGrid() {
		assertFalse(layout.isInGrid(10, 90));
		assertFalse(layout.isInGrid(-10, 20));
		assertFalse(layout.isInGrid(90, 20));
	}

	@Test
	public void theTouchedTileShouldBeReturned() throws SudokuException {
		Pair touchedTile = layout.touchedTile(10, 20);
		assertEquals(1, touchedTile.a());
		assertEquals(2, touchedTile.b());
	}

	@Test(expected=SudokuException.class)
	public void anExceptionShouldBeThrownIfOffGrid() throws SudokuException {
		layout.touchedTile(10, 95);
	}

	@Test
	public void aPointShouldBeInsideKeyBoardIfInKeyboard() {
		assertTrue(layout.isInKeyboard(80, 100));

	}	

	@Test
	public void aPointShouldBeOutsideKeyBoardIfYIsOffKeyboard() {
		assertFalse(layout.isInKeyboard(80, 90));
	}	

	@Test
	public void aPointShouldBeOutsideKeyBoardIfXIsOffKeyboard() {
		assertFalse(layout.isInKeyboard(100, 100));
	}	

	@Test
	public void theTouchedNumberShouldBeReturned() throws SudokuException {
		assertEquals(2, layout.touchedNumber(11));
		assertEquals(9, layout.touchedNumber(88));
	}

	@Test(expected=SudokuException.class)
	public void anExceptionShouldBeThrownIfOffKeyoard() throws SudokuException {
		layout.touchedNumber(188);
	}

	
}
