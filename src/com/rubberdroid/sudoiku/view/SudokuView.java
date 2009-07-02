package com.rubberdroid.sudoiku.view;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.rubberdroid.sudoiku.SoundManager;
import com.rubberdroid.sudoiku.Sudoiku;
import com.rubberdroid.sudoiku.SudoikuPreferences;
import com.rubberdroid.sudoiku.SudokuController;
import com.rubberdroid.sudoiku.model.SudokuModel;

public class SudokuView extends View implements Observer {
	private BoardView board;

	private SudokuModel sudokuModel;
	private SudokuController controller;
	private SudoikuPreferences preferences;
	private SoundManager soundManager;

	public SudokuView(Context context, SudokuModel sudokuModel,
			SudoikuPreferences preferences, SoundManager soundManager) {
		super(context);

		this.sudokuModel = sudokuModel;
		this.preferences = preferences;
		this.soundManager = soundManager;

		sudokuModel.addObserver(this);

		setFocusable(true);
		setFocusableInTouchMode(true);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		BoardLayout dimensions = new BoardLayout(getWidth(), getHeight());
		controller = new SudokuController(dimensions, sudokuModel);
		board = new BoardView(dimensions, sudokuModel, getResources(),
				preferences, soundManager);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		soundManager.playTouch();
		Log
				.d(Sudoiku.TAG, "onKeyDown: keycode=" + keyCode + ", event="
						+ event);
		if (controller.isKeyManaged(keyCode, event))
			return true;

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			soundManager.playTouch();
		if (controller.isTouchManaged(event))
			return true;

		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		board.draw(canvas);

	}

	public void update(Observable observable, Object data) {
		if (data != null && data instanceof ToastMsg) {
			Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT)
					.show();
			soundManager.playCrash();
		}

		invalidate();
	}

}
