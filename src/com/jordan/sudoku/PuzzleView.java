package com.jordan.sudoku;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class PuzzleView extends View implements Observer{
	private BoardView board;

	private SudokuModel puzzle;
	private SudokuController controller;

	public PuzzleView(Context context, SudokuModel puzzle) {
		super(context);

		this.puzzle = puzzle;
		puzzle.addObserver(this);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		BoardLayout dimensions = new BoardLayout(getWidth(),getHeight());
		controller = new SudokuController(dimensions, puzzle);
		board = new BoardView(dimensions, puzzle, getResources());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(Game.TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		if (controller.isKeyManaged(keyCode, event)) 
			return true;

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (controller.isTouchManaged(event)) 
			return true;

		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		board.draw(canvas);
	}

	public void update(Observable observable, Object data) {
		invalidate();		
	}

}
