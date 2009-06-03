package com.rubberdroid.sudoiku;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.rubberdroid.sudoiku.exception.SudokuException;
import com.rubberdroid.sudoiku.model.SudokuModel;
import com.rubberdroid.sudoiku.model.Tile;
import com.rubberdroid.sudoiku.view.BoardLayout;
import com.rubberdroid.sudoku.util.Pair;

public class SudokuController {
	private SudokuModel sudokuModel;
	private BoardLayout board;

	public SudokuController(BoardLayout dimensions, SudokuModel puzzle) {
		this.sudokuModel = puzzle;
		this.board = dimensions;
	}

	public boolean isKeyManaged(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			moveSelection(0, -1);
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			moveSelection(0, +1);
			return true;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			moveSelection(-1, 0);
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			moveSelection(+1, 0);
			return true;
		case KeyEvent.KEYCODE_0:
			return true;
		case KeyEvent.KEYCODE_SPACE:
			setValueToSelectedTile(0);
			return true;
		case KeyEvent.KEYCODE_1:
			setValueToSelectedTile(1);
			return true;
		case KeyEvent.KEYCODE_2:
			setValueToSelectedTile(2);
			return true;
		case KeyEvent.KEYCODE_3:
			setValueToSelectedTile(3);
			return true;
		case KeyEvent.KEYCODE_4:
			setValueToSelectedTile(4);
			return true;
		case KeyEvent.KEYCODE_5:
			setValueToSelectedTile(5);
			return true;
		case KeyEvent.KEYCODE_6:
			setValueToSelectedTile(6);
			return true;
		case KeyEvent.KEYCODE_7:
			setValueToSelectedTile(7);
			return true;
		case KeyEvent.KEYCODE_8:
			setValueToSelectedTile(8);
			return true;
		case KeyEvent.KEYCODE_9:
			setValueToSelectedTile(9);
			return true;
		default:
			return false;
		}
	}

	private void setValueToSelectedTile(int tile) {
		Tile selected = sudokuModel.selectedTile();
		sudokuModel.setValueToTile(selected.x(), selected.y(), tile);
	}

	private void moveSelection(int diffX, int diffY) {
		sudokuModel.moveSelection(diffX, diffY);
	}

	public boolean isTouchManaged(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return false;

		int x = (int) event.getX();
		int y = (int) event.getY();

		Log.d(Sudoiku.TAG, "onTouchEvent: x touch  " + x + ", y " + y);

		checkGridTouch(x, y);
		checkKeyBoardTouch(x, y);
		checkButtonBoardTouch(x, y);
		return true;
	}

	private void checkButtonBoardTouch(int x, int y) {
		if (!board.isInButtonsBoard(x, y))
			return;

		try {
			int button = board.touchedButton(x);
			if (button == Tile.NOTES_BUTTON) {
				if (sudokuModel.isNotesMode())
					sudokuModel.setInNumbersMode();
				else
					sudokuModel.setInNotesMode();
			}
			if (button == Tile.ERASE_BUTTON) {
				if (sudokuModel.isNotesMode())
					sudokuModel.resetNotes();
				else
					setValueToSelectedTile(0);
			}
			if (button == Tile.SOLVE_BUTTON) {
				sudokuModel.solve();
			}
		} catch (SudokuException e) {
			Log.e(Sudoiku.TAG, e.getMessage());
		}
	}

	private void checkKeyBoardTouch(int x, int y) {
		if (!board.isInKeyboard(x, y))
			return;

		try {
			setValueToSelectedTile(board.touchedNumber(x));
		} catch (SudokuException e) {
			Log.e(Sudoiku.TAG, e.getMessage());
		}
	}

	private void checkGridTouch(int x, int y) {
		if (!board.isInGrid(x, y))
			return;

		Pair touchedTile = null;
		try {
			touchedTile = board.touchedTile(x, y);
		} catch (SudokuException e) {
			Log.e(Sudoiku.TAG, e.getMessage());
			return;
		}
		sudokuModel.selectTile(touchedTile.a(), touchedTile.b());
	}
}
