package com.jordan.sudoku;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

public class TestModelNotification {

	private SudokuModel model;

	@Before
	public void createTestContext() {
		model = new SudokuModel(Game.DIFFICULTY_EASY, new MySudokuGenerator());
	}

	class MockObserver implements Observer {
		boolean observed;

		public void update(Observable observable, Object data) {
			observed = true;
		}

	}

	@Test
	public void modelShouldNotifyWhenSelectionIsMoved() {
		MockObserver canary = new MockObserver();
		model.addObserver(canary);

		model.moveSelection(1, 1);
		assertTrue(canary.observed);
	}

	@Test
	public void modelShouldNotNotifyWhenSelectionIsntReallyMoved() {
		MockObserver canary = new MockObserver();
		model.addObserver(canary);

		model.moveSelection(0, 0);
		assertFalse(canary.observed);
	}

	@Test
	public void modelShouldNotifyWhenANewNumberIsInserted() {
		MockObserver canary = new MockObserver();
		model.addObserver(canary);

		assertTrue(model.setValueToTileIfValid(2, 0, 2));
		assertTrue(canary.observed);
	}
}
