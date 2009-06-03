package com.jordan.sudoku;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import com.rubberdroid.sudoiku.Sudoiku;
import com.rubberdroid.sudoiku.model.SudokuModel;

public class TestModelNotification {

	private SudokuModel model;
	private MockObserver canary;

	@Before
	public void createTestContext() {
		model = new SudokuModel(Sudoiku.DIFFICULTY_EASY, new MySudokuGenerator());
		canary = new MockObserver();
		model.addObserver(canary);
	}

	class MockObserver implements Observer {
		boolean observed;

		public void update(Observable observable, Object data) {
			observed = true;
		}

	}

	@Test
	public void modelShouldNotifyWhenSelectionIsMoved() {
		model.moveSelection(1, 1);
		assertTrue(canary.observed);
	}

	@Test
	public void modelShouldNotNotifyWhenSelectionIsntReallyMoved() {
		model.moveSelection(0, 0);
		assertFalse(canary.observed);
	}

	@Test
	public void modelShouldNotifyWhenANewNumberIsInserted() {
		model.setValueToTile(2, 0, 2);
		assertTrue(canary.observed);
	}

	@Test
	public void modelShouldNotifyWhenGoesInNotesMode() {
		model.setInNotesMode();
		assertTrue(canary.observed);
	}

	@Test
	public void modelShouldNotifyWhenGoesInNumbersMode() {
		model.setInNumbersMode();
		assertTrue(canary.observed);
	}

	@Test
	public void modelShouldNotifyWhenNotesAreReset() {
		model.resetNotes();
		assertTrue(canary.observed);
	}
}
