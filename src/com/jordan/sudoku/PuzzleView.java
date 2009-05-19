package com.jordan.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class PuzzleView extends View {
	private static final String TAG = "Sudoku";
	private final Game game;
	private float tileWidth;
	private float tileHeight;
	private Point selectedTile = new Point();
	private Rect selectedRect = new Rect();
	private Board board;

	private Puzzle puzzle;

	public PuzzleView(Context context, Puzzle puzzle) {
		super(context);
		this.game = (Game) context;
		this.puzzle = puzzle;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		tileWidth = w / 9f;
		tileHeight = tileWidth;// h / 9f;
		board = new Board(getWidth(), getHeight(), tileWidth, tileHeight,
				puzzle, getResources());
		selectRect();
		Log.d(TAG, "onSizeChanged: width " + tileWidth + ", height "
				+ tileHeight);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void selectRect() {
		Log.d(TAG, "tileSelected=" + selectedTile);

		selectedRect.set((int) (selectedTile.x * tileWidth),
				(int) (selectedTile.y * tileHeight), (int) (selectedTile.x
						* tileWidth + tileWidth), (int) (selectedTile.y
						* tileHeight + tileHeight));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			moveSelection(0, -1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			moveSelection(0, +1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			moveSelection(-1, 0);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			moveSelection(+1, 0);
			break;

		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
			setSelectedTile(0);
			break;
		case KeyEvent.KEYCODE_1:
			setSelectedTile(1);
			break;
		case KeyEvent.KEYCODE_2:
			setSelectedTile(2);
			break;
		case KeyEvent.KEYCODE_3:
			setSelectedTile(3);
			break;
		case KeyEvent.KEYCODE_4:
			setSelectedTile(4);
			break;
		case KeyEvent.KEYCODE_5:
			setSelectedTile(5);
			break;
		case KeyEvent.KEYCODE_6:
			setSelectedTile(6);
			break;
		case KeyEvent.KEYCODE_7:
			setSelectedTile(7);
			break;
		case KeyEvent.KEYCODE_8:
			setSelectedTile(8);
			break;
		case KeyEvent.KEYCODE_9:
			setSelectedTile(9);
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			showKeypadOrError(selectedTile.x, selectedTile.y);
			break;

		default:
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		Log.d(TAG, "onTouchEvent: x touch  " + event.getX() + ", y "
				+ event.getY());

		int xTouched = (int) (event.getX() / tileWidth);
		int yTouched = (int) (event.getY() / tileHeight);

		selectTile(xTouched, yTouched);
		// showKeypadOrError(selX, selY);
		Log.d(TAG, "onTouchEvent: x " + selectedTile.x + ", y "
				+ selectedTile.y);
		return true;
	}

	protected void showKeypadOrError(int x, int y) {
		int tiles[] = puzzle.getUsedTiles(x, y);
		if (tiles.length == 9) {
			// Toast toast = Toast.makeText(this,
			// R.string.no_moves_label, Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.CENTER, 0, 0);
			// toast.show();
			// } else {
			// Log.d(TAG, "showKeypad: used=" + toPuzzleString(tiles));
			// Dialog v = new Keypad(this, tiles, puzzleView);
			// v.show();
		}
	}

	public void setSelectedTile(int tile) {
		if (puzzle.setTileIfValid(selectedTile, tile)) {
			invalidate();// may change hints
		} else {
			// Number is not valid for this tile
			Log.d(TAG, "setSelectedTile: invalid: " + tile);
		}
	}

	private void moveSelection(int diffX, int diffY) {
		selectTile(selectedTile.x + diffX, selectedTile.y + diffY);
	}

	private void selectTile(int xTile, int yTile) {
		invalidate(selectedRect);
		selectedTile.x = Math.min(Math.max(xTile, 0), 8);
		selectedTile.y = Math.min(Math.max(yTile, 0), 8);
		selectRect();
		invalidate(selectedRect);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		board.draw(canvas);

		// Draw the selection...
		Log.d(TAG, "selRect=" + selectedRect);
		Paint selected = new Paint();
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selectedRect, selected);
		// Draw the hints...
		// Draw the selection...
	}

}
