package com.jordan.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Game extends Activity {
	public static final String TAG = "Sudoku";

	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;

	public enum Difficulty {
		easy, medium, hard
	};

	private PuzzleView puzzleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);

		Puzzle puzzle =  new Puzzle(diff);


		puzzleView = new PuzzleView(this, puzzle);

		setContentView(puzzleView);
		puzzleView.requestFocus();
	}





}
