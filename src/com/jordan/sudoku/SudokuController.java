package com.jordan.sudoku;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class SudokuController {
	private SudokuModel puzzle;
	private SudokuDimensions dimensions;

	public SudokuController(SudokuDimensions dimensions, SudokuModel puzzle) {
		this.puzzle = puzzle;
		this.dimensions = dimensions;
	}

	public boolean isKeyManaged(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			return moveSelection(0, -1);
		case KeyEvent.KEYCODE_DPAD_DOWN:
			return moveSelection(0, +1);
		case KeyEvent.KEYCODE_DPAD_LEFT:
			return moveSelection(-1, 0);
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			return moveSelection(+1, 0);
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
			return setSelectedTile(0);
		case KeyEvent.KEYCODE_1:
			return setSelectedTile(1);
		case KeyEvent.KEYCODE_2:
			return setSelectedTile(2);
		case KeyEvent.KEYCODE_3:
			return setSelectedTile(3);
		case KeyEvent.KEYCODE_4:
			return setSelectedTile(4);
		case KeyEvent.KEYCODE_5:
			return setSelectedTile(5);
		case KeyEvent.KEYCODE_6:
			return setSelectedTile(6);
		case KeyEvent.KEYCODE_7:
			return setSelectedTile(7);
		case KeyEvent.KEYCODE_8:
			return setSelectedTile(8);
		case KeyEvent.KEYCODE_9:
			return setSelectedTile(9);
		default:
			return false;
		}
	}

	public boolean setSelectedTile(int tile) {
		return puzzle.setTileIfValid(tile);
	}

	private boolean moveSelection(int diffX, int diffY) {
		return puzzle.moveSelection(diffX, diffY);
	}

	public boolean isTouchManaged(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return false;

		Log.d(Game.TAG, "onTouchEvent: x touch  " + event.getX() + ", y "
				+ event.getY());

		int xTouched = (int) (event.getX() / dimensions.tileSide);
		int yTouched = (int) (event.getY() / dimensions.tileSide);

		puzzle.selectTile(xTouched, yTouched);
		return true;
	}

}
