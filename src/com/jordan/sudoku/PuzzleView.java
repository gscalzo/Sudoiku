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
	private final Game game;
	private Board board;

	private Puzzle puzzle;
	private SudokuController controller;

	public PuzzleView(Context context, Puzzle puzzle) {
		super(context);

		this.game = (Game) context;
		this.puzzle = puzzle;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		SudokuDimensions dimensions = new SudokuDimensions(getWidth());

		controller = new SudokuController(dimensions, puzzle);

		board = new Board(dimensions, puzzle, getResources());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(Game.TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		if (controller.onKeyDown(keyCode, event)) {
			invalidate();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (controller.onTouchEvent(event)) {
			invalidate();
			return true;
		}

		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		board.draw(canvas);
	}

}
