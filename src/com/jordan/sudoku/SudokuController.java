package com.jordan.sudoku;

import com.jordan.sudoku.util.Pair;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class SudokuController {
	private SudokuModel puzzle;
	private BoardLayout board;

	public SudokuController(BoardLayout dimensions, SudokuModel puzzle) {
		this.puzzle = puzzle;
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
			setSelectedTile(0);
			return true;
		case KeyEvent.KEYCODE_1:
			setSelectedTile(1);
			return true;
		case KeyEvent.KEYCODE_2:
			setSelectedTile(2);
			return true;
		case KeyEvent.KEYCODE_3:
			setSelectedTile(3);
			return true;
		case KeyEvent.KEYCODE_4:
			setSelectedTile(4);
			return true;
		case KeyEvent.KEYCODE_5:
			setSelectedTile(5);
			return true;
		case KeyEvent.KEYCODE_6:
			setSelectedTile(6);
			return true;
		case KeyEvent.KEYCODE_7:
			setSelectedTile(7);
			return true;
		case KeyEvent.KEYCODE_8:
			setSelectedTile(8);
			return true;
		case KeyEvent.KEYCODE_9:
			setSelectedTile(9);
			return true;
		default:
			return false;
		}
	}

	public boolean setSelectedTile(int tile) {
		Tile selected = puzzle.selectedTile();
		return puzzle.setValueToTileIfValid(selected.x(), selected.y(), tile);
	}

	private void moveSelection(int diffX, int diffY) {
		puzzle.moveSelection(diffX, diffY);
	}

	public boolean isTouchManaged(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return false;

		int x = (int) event.getX();
		int y = (int) event.getY();

		Log.d(Game.TAG, "onTouchEvent: x touch  " + x + ", y " + y);

		checkBoardTouch(x, y);
		checkKeyBoardTouch(x, y);
		return true;
	}

	private void checkKeyBoardTouch(int x, int y) {
		if (!board.isInKeyboard(x, y))
			return;

		try {
			setSelectedTile(board.touchedNumber(x));
		} catch (SudokuException e) {
			Log.e(Game.TAG, e.getMessage());
		}
	}

	private void checkBoardTouch(int x, int y) {
		if (!board.isInGrid(x, y))
			return;

		Pair touchedTile = null;
		try {
			touchedTile = board.touchedTile(x, y);
		} catch (SudokuException e) {
			Log.e(Game.TAG, e.getMessage());
			return;
		}
		puzzle.selectTile(touchedTile.a(), touchedTile.b());
	}
}
